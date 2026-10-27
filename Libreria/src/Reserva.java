import java.time.LocalDateTime;


public class Reserva {

    private Cliente cliente;
    private Libro libro;
    private LocalDateTime fechaReserva;

    public Reserva(Cliente cliente, Libro libro, LocalDateTime fechaReserva) {

        this.cliente = cliente;
        this.libro = libro;
        this.fechaReserva = fechaReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }
}
