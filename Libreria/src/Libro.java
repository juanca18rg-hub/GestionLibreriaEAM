import java.util.ArrayList;

public class Libro {

    // Datos del libro
    private String identificador;
    private String titulo;
    private String autor;
    private String editorial;
    private int anioPublicacion;
    private String categoria;
    private String estado;

    // Lista donde se almacenan los libros
    private static ArrayList<Libro> libros = new ArrayList<>();

    // Constructor
    public Libro(String identificador, String titulo, String autor,
                 String editorial, int anioPublicacion, String categoria) {

        this.identificador = identificador;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
        this.estado = "Disponible";
    }

    // Getters
    public String getIdentificador() {
        return identificador;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEstado() {
        return estado;
    }

    // Cambiar estado del libro
    public void cambiarEstado(String nuevoEstado) {
        estado = nuevoEstado;
    }

    // Mostrar información de un libro
    public void mostrarInformacion() {

        System.out.println("ID: " + identificador);
        System.out.println("Titulo: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Editorial: " + editorial);
        System.out.println("Año de publicacion: " + anioPublicacion);
        System.out.println("Categoria: " + categoria);
        System.out.println("Estado: " + estado);
        System.out.println("-----------------------------");
    }

    // Registrar un libro
    public static void registrarLibro(Libro libro) {

        for (Libro l : libros) {

            if (l.getIdentificador().equals(libro.getIdentificador())) {
                System.out.println("Error: ya existe un libro con ese identificador.");
                return;
            }
        }

        libros.add(libro);

        System.out.println("Libro registrado correctamente.");
    }

    // Mostrar todos los libros
    public static void listarLibros() {

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("===== INVENTARIO DE LIBROS =====");

        for (Libro libro : libros) {
            libro.mostrarInformacion();
        }
    }
}