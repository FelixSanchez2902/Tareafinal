package Final;

import javax.swing.*;
import java.awt.*;


public class PanelPrincipal extends JPanel {
    private SistemaGestion sistema;
    private JButton btnGestionUsuarios, btnGestionProductos, btnCerrarSesion;

    public PanelPrincipal(SistemaGestion sistema) {
        this.sistema = sistema;
        setLayout(new FlowLayout());

        btnGestionUsuarios = new JButton("Gestión de Usuarios");
        btnGestionProductos = new JButton("Gestión de Productos");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        btnGestionUsuarios.addActionListener(e -> new GestionUsuarios(sistema).setVisible(true));
        btnGestionProductos.addActionListener(e -> new GestionProductos(sistema).setVisible(true));
        btnCerrarSesion.addActionListener(e -> sistema.mostrarLogin());

        add(btnGestionUsuarios);
        add(btnGestionProductos);
        add(btnCerrarSesion);
    }
}