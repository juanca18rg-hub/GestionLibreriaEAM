import java.time.LocalDateTime;

public class Devolucion {
    private LocalDateTime fechaDevolucion;
   private Cliente cliente;
   private Libro libro;

    public Devolucion(Cliente cliente, Libro libro) {
        this.cliente = cliente;
        this.libro = libro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void prestarlibro(){
        if (libro.getEstado()=="Prestado"){
            libro.cambiarEstado("Disponible");
            setFechaDevolucion(LocalDateTime.now());
            cliente.setPrestamosActivos(-1);
        }
    }
}
