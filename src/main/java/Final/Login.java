package Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JPanel {
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private SistemaGestion sistema;
    private JTextField txtNombreUsuarioReg, txtNombreReg, txtApellidoReg, txtTelefonoReg, txtCorreoReg;
    private JPasswordField txtContraseñaReg, txtConfirmarContraseñaReg;
    private JPanel panelRegistro;

    public Login(SistemaGestion sistema) {
        this.sistema = sistema;
        setLayout(new BorderLayout());

        JPanel panelLogin = new JPanel(new GridLayout(3, 2));
        panelLogin.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelLogin.add(txtUsuario);
        panelLogin.add(new JLabel("Contraseña:"));
        txtContraseña = new JPasswordField();
        panelLogin.add(txtContraseña);

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistrar = new JButton("Registrarse");

        btnLogin.addActionListener(e -> intentarLogin());
        btnRegistrar.addActionListener(e -> mostrarRegistro());

        add(panelLogin, BorderLayout.CENTER);
        add(btnLogin, BorderLayout.SOUTH);
        add(btnRegistrar, BorderLayout.NORTH);
    }

    private void intentarLogin() {
        String usuario = txtUsuario.getText();
        String contraseña = new String(txtContraseña.getPassword());

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse");
            return;
        }

        for (Usuario u : sistema.getUsuarios()) {
            if (u.getNombreUsuario().equals(usuario) && u.getContraseña().equals(contraseña)) {
                sistema.mostrarPanelPrincipal();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
    }

    private void mostrarRegistro() {
        panelRegistro = new JPanel(new GridLayout(8, 2));
        panelRegistro.add(new JLabel("Nombre de Usuario:"));
        txtNombreUsuarioReg = new JTextField();
        panelRegistro.add(txtNombreUsuarioReg);
        panelRegistro.add(new JLabel("Nombre:"));
        txtNombreReg = new JTextField();
        panelRegistro.add(txtNombreReg);
        panelRegistro.add(new JLabel("Apellido:"));
        txtApellidoReg = new JTextField();
        panelRegistro.add(txtApellidoReg);
        panelRegistro.add(new JLabel("Teléfono:"));
        txtTelefonoReg = new JTextField();
        panelRegistro.add(txtTelefonoReg);
        panelRegistro.add(new JLabel("Correo:"));
        txtCorreoReg = new JTextField();
        panelRegistro.add(txtCorreoReg);
        panelRegistro.add(new JLabel("Contraseña:"));
        txtContraseñaReg = new JPasswordField();
        panelRegistro.add(txtContraseñaReg);
        panelRegistro.add(new JLabel("Confirmar Contraseña:"));
        txtConfirmarContraseñaReg = new JPasswordField();
        panelRegistro.add(txtConfirmarContraseñaReg);

        int result = JOptionPane.showConfirmDialog(this, panelRegistro, "Registro", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            registrarUsuario();
        }
    }

    private void registrarUsuario() {
        String[] campos = {
                "Nombre de Usuario", txtNombreUsuarioReg.getText(),
                "Nombre", txtNombreReg.getText(),
                "Apellido", txtApellidoReg.getText(),
                "Teléfono", txtTelefonoReg.getText(),
                "Correo", txtCorreoReg.getText()
        };

        for (int i = 0; i < campos.length; i += 2) {
            if (campos[i + 1].isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo " + campos[i] + " es obligatorio");
                return;
            }
        }

        String contraseña = new String(txtContraseñaReg.getPassword());
        String confirmarContraseña = new String(txtConfirmarContraseñaReg.getPassword());

        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
            return;
        }

        Usuario nuevoUsuario = new Usuario(
                txtNombreUsuarioReg.getText(),
                txtNombreReg.getText(),
                txtApellidoReg.getText(),
                txtTelefonoReg.getText(),
                txtCorreoReg.getText(),
                contraseña
        );

        sistema.getUsuarios().add(nuevoUsuario);
        JOptionPane.showMessageDialog(this, "Usuario registrado con éxito");
    }
}