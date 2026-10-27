import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Prestamo {

    // Dias que tiene el cliente para devolver el libro
    public static final int DIAS_PRESTAMO = 7;

    private LocalDateTime fechitaDeprestamo;
    private LocalDateTime fechaLimite;
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

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    // Calcula cuantos dias de retraso hay si el libro se devuelve en la
    // fecha indicada. Si se devuelve a tiempo el resultado es 0.
    public int calcularDiasRetraso(LocalDateTime fechaDevolucion) {

        long dias = ChronoUnit.DAYS.between(
                fechaLimite.toLocalDate(),
                fechaDevolucion.toLocalDate()
        );

        if (dias < 0) {
            dias = 0;
        }

        return (int) dias;
    }

    public void prestarlibro() {

        if (libro.getEstado().equalsIgnoreCase("Disponible")
                && !cliente.tieneLibroPrestado()) {

            libro.cambiarEstado("Prestado");

            cliente.setTieneLibroPrestado(true);

            setFechitaDeprestamo(LocalDateTime.now());
            setFechaLimite(fechitaDeprestamo.plusDays(DIAS_PRESTAMO));

            System.out.println(
                    "Se presto el libro "
                            + libro.getTitulo()
                            + " a "
                            + cliente.getNombre()
            );

            System.out.println(
                    "Fecha limite de devolucion: "
                            + Fechas.formatear(fechaLimite)
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
