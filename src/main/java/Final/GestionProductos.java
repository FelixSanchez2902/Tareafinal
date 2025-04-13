package Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GestionProductos extends JFrame {
    private SistemaGestion sistema;
    private JList<Producto> listaProductos;
    private DefaultListModel<Producto> modelProductos;
    private JTextField txtNombre, txtMarca, txtCategoria, txtPrecio, txtCantidad;

    public GestionProductos(SistemaGestion sistema) {
        this.sistema = sistema;
        setTitle("Gestión de Productos");
        setSize(600, 400);
        setLocationRelativeTo(null);

        modelProductos = new DefaultListModel<>();
        for (Producto p : sistema.getProductos()) {
            modelProductos.addElement(p);
        }

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
                        campos[0].getText(),
                        campos[1].getText(),
                        campos[2].getText(),
                        Double.parseDouble(campos[3].getText()),
                        Integer.parseInt(campos[4].getText())
                );
                sistema.getProductos().add(nuevoProducto);
                modelProductos.addElement(nuevoProducto);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Error en los datos numéricos");
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
                    modelProductos.clear();
                    for (Producto producto : sistema.getProductos()) {
                        modelProductos.addElement(producto);
                    }
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error en los datos numéricos");
                }
            });

            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.addActionListener(e -> {
                sistema.getProductos().remove(p);
                modelProductos.removeElement(p);
                dialog.dispose();
            });

            dialog.add(btnGuardar);
            dialog.add(btnEliminar);
            dialog.setVisible(true);
        }
    }
}