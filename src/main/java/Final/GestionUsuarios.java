package Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
        for (Usuario u : sistema.getUsuarios()) {
            modelUsuarios.addElement(u);
        }

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
            u.setNombre(txtNombre.getText());
            u.setApellido(txtApellido.getText());
            u.setTelefono(txtTelefono.getText());
            u.setCorreo(txtCorreo.getText());
            modelUsuarios.clear();
            for (Usuario usuario : sistema.getUsuarios()) {
                modelUsuarios.addElement(usuario);
            }
            JOptionPane.showMessageDialog(this, "Cambios guardados");
        }
    }

    private void eliminarUsuario() {
        Usuario u = listaUsuarios.getSelectedValue();
        if (u != null) {
            sistema.getUsuarios().remove(u);
            modelUsuarios.removeElement(u);
            txtNombre.setText("");
            txtApellido.setText("");
            txtTelefono.setText("");
            txtCorreo.setText("");
            JOptionPane.showMessageDialog(this, "Usuario eliminado");
        }
    }
}