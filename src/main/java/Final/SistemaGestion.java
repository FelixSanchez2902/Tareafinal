package Final;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SistemaGestion {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    private JFrame frame;
    private Login login;
    private PanelPrincipal panelPrincipal;

    public SistemaGestion() {
        cargarDatosIniciales();
        login = new Login(this);
        frame = new JFrame("Sistema de Gestión de Almacén");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        mostrarLogin();
    }

    private void cargarDatosIniciales() {
        try {

            ResultSet rsUsuarios = DatabaseConnection.getAllUsers();
            while (rsUsuarios.next()) {
                usuarios.add(new Usuario(
                        rsUsuarios.getString("nombre_usuario"),
                        rsUsuarios.getString("nombre"),
                        rsUsuarios.getString("apellido"),
                        rsUsuarios.getString("telefono"),
                        rsUsuarios.getString("correo"),
                        rsUsuarios.getString("contrasena")
                ));
            }


            ResultSet rsProductos = DatabaseConnection.getAllProducts();
            while (rsProductos.next()) {
                productos.add(new Producto(
                        rsProductos.getInt("id"),
                        rsProductos.getString("nombre"),
                        rsProductos.getString("marca"),
                        rsProductos.getString("categoria"),
                        rsProductos.getDouble("precio"),
                        rsProductos.getInt("cantidad_disponible")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos iniciales: " + e.getMessage());
        }
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