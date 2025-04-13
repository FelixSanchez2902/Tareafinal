package Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SistemaGestion {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    private JFrame frame;
    private Login login;
    private PanelPrincipal panelPrincipal;

    public SistemaGestion() {
        login = new Login(this);
        frame = new JFrame("Sistema de Gestión de Almacén");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        mostrarLogin();
    }

    public void mostrarLogin() {
        frame.setContentPane(login);
        frame.setVisible(true);
    }

    public void mostrarPanelPrincipal() {
        panelPrincipal = new PanelPrincipal(this);
        frame.setContentPane(panelPrincipal);
        frame.revalidate();
    }

    public ArrayList<Usuario> getUsuarios() { return usuarios; }
    public ArrayList<Producto> getProductos() { return productos; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaGestion::new);
    }
}