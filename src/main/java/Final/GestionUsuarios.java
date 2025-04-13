package Final;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GestionUsuarios extends JFrame {
    private SistemaGestion sistema;
    private JList<Usuario> listaUsuarios;
    private DefaultListModel<Usuario> modelUsuarios;
    private JTextField txtNombre, txtApellido, txtTelefono, txtCorreo;

    public GestionUsuarios(SistemaGestion sistema) {
        this.sistema = sistema;
        setTitle("Gestión de Usuarios");
        setSize(600, 400);
        setLocationRelativeTo(null);

        modelUsuarios = new DefaultListModel<>();
        actualizarListaUsuarios();

        listaUsuarios = new JList<>(modelUsuarios);
        add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);

        JPanel panelDetalles = new JPanel(new GridLayout(4, 2));
        panelDetalles.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelDetalles.add(txtNombre);
        panelDetalles.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelDetalles.add(txtApellido);
        panelDetalles.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelDetalles.add(txtTelefono);
        panelDetalles.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelDetalles.add(txtCorreo);

        add(panelDetalles, BorderLayout.SOUTH);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver");

        btnGuardar.addActionListener(e -> guardarCambios());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnVolver.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.NORTH);

        listaUsuarios.addListSelectionListener(e -> mostrarDetalles());
    }

    private void actualizarListaUsuarios() {
        modelUsuarios.clear();
        try {
            ResultSet rs = DatabaseConnection.getAllUsers();
            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("nombre_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        "" // Contraseña no se muestra
                );
                modelUsuarios.addElement(u);
                sistema.getUsuarios().add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void mostrarDetalles() {
        Usuario u = listaUsuarios.getSelectedValue();
        if (u != null) {
            txtNombre.setText(u.getNombre());
            txtApellido.setText(u.getApellido());
            txtTelefono.setText(u.getTelefono());
            txtCorreo.setText(u.getCorreo());
        }
    }

    private void guardarCambios() {
        Usuario u = listaUsuarios.getSelectedValue();
        if (u != null) {
            try {
                u.setNombre(txtNombre.getText());
                u.setApellido(txtApellido.getText());
                u.setTelefono(txtTelefono.getText());
                u.setCorreo(txtCorreo.getText());
                DatabaseConnection.updateUser(u); // Guardar en la base de datos
                actualizarListaUsuarios(); // Refrescar la lista
                JOptionPane.showMessageDialog(this, "Cambios guardados");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar cambios: " + e.getMessage());
            }
        }
    }

    private void eliminarUsuario() {
        Usuario u = listaUsuarios.getSelectedValue();
        if (u != null) {
            try {
                DatabaseConnection.deleteUser(u.getNombreUsuario()); // Eliminar de la base de datos
                sistema.getUsuarios().remove(u); // Eliminar del ArrayList
                modelUsuarios.removeElement(u);
                txtNombre.setText("");
                txtApellido.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                JOptionPane.showMessageDialog(this, "Usuario eliminado");
                actualizarListaUsuarios(); // Refrescar la lista
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + e.getMessage());
            }
        }
    }
}