package Final;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GestionProductos extends JFrame {
    private SistemaGestion sistema;
    private JList<Producto> listaProductos;
    private DefaultListModel<Producto> modelProductos;


    public GestionProductos(SistemaGestion sistema) {
        this.sistema = sistema;
        setTitle("Gestión de Productos");
        setSize(600, 400);
        setLocationRelativeTo(null);

        modelProductos = new DefaultListModel<>();
        actualizarListaProductos();

        listaProductos = new JList<>(modelProductos);
        add(new JScrollPane(listaProductos), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.addActionListener(e -> mostrarFormularioNuevo());
        panelBotones.add(btnNuevo);
        add(panelBotones, BorderLayout.NORTH);

        listaProductos.addListSelectionListener(e -> mostrarDetalles());

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());
        add(btnVolver, BorderLayout.SOUTH);
    }

    private void actualizarListaProductos() {
        modelProductos.clear();
        try {
            ResultSet rs = DatabaseConnection.getAllProducts();
            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("marca"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad_disponible")
                );
                modelProductos.addElement(p);
                sistema.getProductos().add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
        }
    }

    private void mostrarFormularioNuevo() {
        JDialog dialog = new JDialog(this, "Nuevo Producto", true);
        dialog.setSize(300, 300);
        dialog.setLayout(new GridLayout(6, 2));

        JTextField[] campos = new JTextField[5];
        String[] etiquetas = {"Nombre", "Marca", "Categoría", "Precio", "Cantidad Disponible"};

        for (int i = 0; i < 5; i++) {
            dialog.add(new JLabel(etiquetas[i] + ":"));
            campos[i] = new JTextField();
            dialog.add(campos[i]);
        }

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                Producto nuevoProducto = new Producto(
                        -1,
                        campos[0].getText(),
                        campos[1].getText(),
                        campos[2].getText(),
                        Double.parseDouble(campos[3].getText()),
                        Integer.parseInt(campos[4].getText())
                );
                DatabaseConnection.registerProduct(nuevoProducto);
                sistema.getProductos().add(nuevoProducto);
                actualizarListaProductos();
                dialog.dispose();
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error en los datos numéricos o al guardar: " + ex.getMessage());
            }
        });

        dialog.add(btnGuardar);
        dialog.setVisible(true);
    }

    private void mostrarDetalles() {
        Producto p = listaProductos.getSelectedValue();
        if (p != null) {
            JDialog dialog = new JDialog(this, "Editar Producto", true);
            dialog.setSize(300, 300);
            dialog.setLayout(new GridLayout(7, 2));

            JTextField[] campos = new JTextField[5];
            String[] etiquetas = {"Nombre", "Marca", "Categoría", "Precio", "Cantidad Disponible"};

            for (int i = 0; i < 5; i++) {
                dialog.add(new JLabel(etiquetas[i] + ":"));
                campos[i] = new JTextField();
                if (i == 0) campos[i].setText(p.getNombre());
                else if (i == 1) campos[i].setText(p.getMarca());
                else if (i == 2) campos[i].setText(p.getCategoria());
                else if (i == 3) campos[i].setText(String.valueOf(p.getPrecio()));
                else if (i == 4) campos[i].setText(String.valueOf(p.getCantidadDisponible()));
                dialog.add(campos[i]);
            }

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.addActionListener(e -> {
                try {
                    p.setNombre(campos[0].getText());
                    p.setMarca(campos[1].getText());
                    p.setCategoria(campos[2].getText());
                    p.setPrecio(Double.parseDouble(campos[3].getText()));
                    p.setCantidadDisponible(Integer.parseInt(campos[4].getText()));
                    DatabaseConnection.updateProduct(p);
                    actualizarListaProductos();
                    dialog.dispose();
                } catch (NumberFormatException | SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error en los datos numéricos o al guardar: " + ex.getMessage());
                }
            });

            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.addActionListener(event -> {
                try {
                    DatabaseConnection.deleteProduct(p.getId());
                    sistema.getProductos().remove(p);
                    modelProductos.removeElement(p);
                    dialog.dispose();
                    actualizarListaProductos();
                } catch (SQLException error) {
                    JOptionPane.showMessageDialog(dialog, "Error al eliminar producto: " + error.getMessage());
                }
            });

            dialog.add(btnGuardar);
            dialog.add(btnEliminar);
            dialog.setVisible(true);
        }
    }
}