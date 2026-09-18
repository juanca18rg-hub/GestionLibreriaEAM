import java.time.LocalDate;
import java.time.LocalDateTime;

public class Prestamo {

    private LocalDateTime fechitaDeprestamo;
   private Cliente cliente;
   private  Libro libro;

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

    public void prestarlibro(){
        if (libro.getEstado().equalsIgnoreCase("Disponible")){
            libro.cambiarEstado("Prestado");
            setFechitaDeprestamo(LocalDateTime.now());
            cliente.setPrestamosActivos(cliente.getPrestamosActivos()+1);
            System.out.println("Se presto el libro"+libro.getTitulo()+"a"+cliente.getNombre());
        }
        System.out.println("El libro"+libro.getTitulo()+"Esta prestado");
    }
}
