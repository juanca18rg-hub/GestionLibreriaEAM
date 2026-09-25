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

    public void devolverLibro() {

        if (libro.getEstado().equalsIgnoreCase("Prestado")) {

            libro.cambiarEstado("Disponible");

            cliente.setTieneLibroPrestado(false);

            setFechaDevolucion(LocalDateTime.now());

            System.out.println(
                    "El libro "
                            + libro.getTitulo()
                            + " ahora esta disponible."
            );

        } else {

            System.out.println(
                    "El libro ya esta disponible."
            );
        }
    }
}