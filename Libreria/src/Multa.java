import java.time.LocalDateTime;

public class Multa {

    public static final int VALOR_POR_DIA = 1000;

    private Cliente cliente;
    private Libro libro;
    private int diasRetraso;
    private int valor;
    private LocalDateTime fecha;
    private boolean pagada;

    public Multa(Cliente cliente, Libro libro, int diasRetraso,
                 int valor, LocalDateTime fecha) {

        this.cliente = cliente;
        this.libro = libro;
        this.diasRetraso = diasRetraso;
        this.valor = valor;
        this.fecha = fecha;
        this.pagada = false;
    }

    public static int calcularValor(int diasRetraso) {
        return diasRetraso * VALOR_POR_DIA;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Libro getLibro() {
        return libro;
    }

    public int getDiasRetraso() {
        return diasRetraso;
    }

    public int getValor() {
        return valor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public boolean estaPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public void mostrarInformacion() {

        System.out.println("Libro: " + libro.getTitulo());
        System.out.println("Dias de retraso: " + diasRetraso);
        System.out.println("Valor: $" + valor + " COP");
        System.out.println("Fecha de la multa: " + Fechas.formatear(fecha));

        if (pagada) {
            System.out.println("Estado: Pagada");
        } else {
            System.out.println("Estado: Pendiente");
        }

        System.out.println("-----------------------------");
    }
}
