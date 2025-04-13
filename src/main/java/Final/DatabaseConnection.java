package Final;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/registros";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            throw e;
        }
    }


    public static void registerUser(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_usuario, nombre, apellido, telefono, correo, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getCorreo());
            stmt.setString(6, usuario.getContraseña());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas al registrar usuario: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            throw e;
        }
    }

    public static boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
            throw e;
        }
    }

    public static void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE nombre_usuario = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas al eliminar usuario: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            throw e;
        }
    }

    public static void updateUser(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, telefono = ?, correo = ? WHERE nombre_usuario = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getNombreUsuario());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas al actualizar usuario: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            throw e;
        }
    }

    public static Usuario getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getString("nombre_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                );
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            throw e;
        }
    }

    public static ResultSet getAllUsers() throws SQLException {
        String sql = "SELECT nombre_usuario, nombre, apellido, telefono, correo, contrasena FROM usuarios";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }


    public static void registerProduct(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, marca, categoria, precio, cantidad_disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getMarca());
            stmt.setString(3, producto.getCategoria());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getCantidadDisponible());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    producto.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Filas afectadas al registrar producto: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al registrar producto: " + e.getMessage());
            throw e;
        }
    }

    public static void updateProduct(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, marca = ?, categoria = ?, precio = ?, cantidad_disponible = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getMarca());
            stmt.setString(3, producto.getCategoria());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getCantidadDisponible());
            stmt.setInt(6, producto.getId());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas al actualizar producto: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            throw e;
        }
    }

    public static void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas al eliminar producto: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            throw e;
        }
    }

    public static ResultSet getAllProducts() throws SQLException {
        String sql = "SELECT id, nombre, marca, categoria, precio, cantidad_disponible FROM productos";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }
}