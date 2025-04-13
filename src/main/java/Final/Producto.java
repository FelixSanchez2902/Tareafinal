package Final;

public class Producto {
    private int id;
    private String nombre;
    private String marca;
    private String categoria;
    private double precio;
    private int cantidadDisponible;

    public Producto(int id, String nombre, String marca, String categoria, double precio, int cantidadDisponible) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Producto(String nombre, String marca, String categoria, double precio, int cantidadDisponible) {
        this(-1, nombre, marca, categoria, precio, cantidadDisponible); // -1 indica ID no asignado aún
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
}