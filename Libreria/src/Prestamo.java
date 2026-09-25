import java.time.LocalDateTime;

public class Prestamo {

    private LocalDateTime fechitaDeprestamo;
    private Cliente cliente;
    private Libro libro;

    public Prestamo(Cliente cliente, Libro libro) {

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

    public LocalDateTime getFechitaDeprestamo() {
        return fechitaDeprestamo;
    }

    public void setFechitaDeprestamo(LocalDateTime fechitaDeprestamo) {
        this.fechitaDeprestamo = fechitaDeprestamo;
    }

    public void prestarlibro() {

        if (libro.getEstado().equalsIgnoreCase("Disponible")
                && !cliente.tieneLibroPrestado()) {

            libro.cambiarEstado("Prestado");

            cliente.setTieneLibroPrestado(true);

            setFechitaDeprestamo(LocalDateTime.now());

            System.out.println(
                    "Se presto el libro "
                            + libro.getTitulo()
                            + " a "
                            + cliente.getNombre()
            );

        } else {

            if (!libro.getEstado().equalsIgnoreCase("Disponible")) {

                System.out.println(
                        "El libro ya esta prestado."
                );

            } else {

                System.out.println(
                        "El cliente ya tiene un libro prestado."
                );
            }
        }
    }
}