import java.util.ArrayList;

public class Libreria {
    ArrayList<Cliente> clientes;
    ArrayList<Libro> libros;
    ArrayList<Prestamo> prestamos;
    ArrayList<Devolucion> devoluciones;

    public Libreria(ArrayList<Cliente> clientes, ArrayList<Libro> libros, ArrayList<Prestamo> prestamos, ArrayList<Devolucion> devoluciones) {
        this.clientes = clientes;
        this.libros = libros;
        this.prestamos = prestamos;
        this.devoluciones = devoluciones;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }

    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(ArrayList<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public ArrayList<Devolucion> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(ArrayList<Devolucion> devoluciones) {
        this.devoluciones = devoluciones;
    }
}

