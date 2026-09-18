import java.util.ArrayList;
import java.util.Scanner;

public class Libreria {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        ArrayList<Devolucion> devoluciones = new ArrayList<>();

        int opcion;

        do {

            System.out.println();
            System.out.println("======================================");
            System.out.println("        BIBLIOTECA LIBROSYMAS");
            System.out.println("======================================");
            System.out.println("1. Registrar libro");
            System.out.println("2. Mostrar inventario de libros");
            System.out.println("3. Registrar cliente");
            System.out.println("4. Mostrar clientes");
            System.out.println("5. Prestar libro");
            System.out.println("6. Devolver libro");
            System.out.println("7. Mostrar prestamos activos");
            System.out.println("8. Salir");
            System.out.println("======================================");
            System.out.print("Seleccione una opcion: ");

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {

                // =========================================
                // REGISTRAR LIBRO
                // =========================================
                case 1:

                    System.out.println();
                    System.out.println("===== REGISTRAR LIBRO =====");

                    System.out.print("Identificador: ");
                    String identificador = teclado.nextLine();

                    System.out.print("Titulo: ");
                    String titulo = teclado.nextLine();

                    System.out.print("Autor: ");
                    String autor = teclado.nextLine();

                    System.out.print("Editorial: ");
                    String editorial = teclado.nextLine();

                    System.out.print("Año de publicacion: ");
                    int anio = teclado.nextInt();
                    teclado.nextLine();

                    System.out.println();
                    System.out.println("Categorias:");
                    System.out.println("1. Literatura");
                    System.out.println("2. Ciencia");
                    System.out.println("3. Historia");
                    System.out.println("4. Tecnologia");
                    System.out.print("Seleccione una categoria: ");

                    int opcionCategoria = teclado.nextInt();
                    teclado.nextLine();

                    String categoria;

                    if (opcionCategoria == 1) {

                        categoria = "Literatura";

                    } else if (opcionCategoria == 2) {

                        categoria = "Ciencia";

                    } else if (opcionCategoria == 3) {

                        categoria = "Historia";

                    } else if (opcionCategoria == 4) {

                        categoria = "Tecnologia";

                    } else {

                        System.out.println("Categoria no valida.");
                        break;
                    }

                    Libro nuevoLibro = new Libro(
                            identificador,
                            titulo,
                            autor,
                            editorial,
                            anio,
                            categoria
                    );

                    Libro.registrarLibro(nuevoLibro);

                    break;

                // =========================================
                // MOSTRAR LIBROS
                // =========================================
                case 2:

                    System.out.println();
                    Libro.listarLibros();

                    break;

                // =========================================
                // REGISTRAR CLIENTE
                // =========================================
                case 3:

                    System.out.println();
                    System.out.println("===== REGISTRAR CLIENTE =====");

                    System.out.print("Documento de identidad: ");
                    String idCliente = teclado.nextLine();

                    System.out.print("Nombre completo: ");
                    String nombreCliente = teclado.nextLine();

                    System.out.print("Direccion: ");
                    String direccionCliente = teclado.nextLine();

                    boolean clienteExiste = false;

                    for (Cliente cliente : clientes) {

                        if (cliente.getId().equals(idCliente)) {

                            clienteExiste = true;
                            break;
                        }
                    }

                    if (clienteExiste) {

                        System.out.println(
                                "Ya existe un cliente con ese documento."
                        );

                    } else {

                        Cliente nuevoCliente = new Cliente(
                                idCliente,
                                direccionCliente,
                                nombreCliente,
                                0
                        );

                        clientes.add(nuevoCliente);

                        System.out.println(
                                "Cliente registrado correctamente."
                        );
                    }

                    break;

                // =========================================
                // MOSTRAR CLIENTES
                // =========================================
                case 4:

                    System.out.println();
                    System.out.println("===== CLIENTES REGISTRADOS =====");

                    if (clientes.isEmpty()) {

                        System.out.println(
                                "No hay clientes registrados."
                        );

                    } else {

                        for (Cliente cliente : clientes) {

                            cliente.mostrarInformacion();

                            System.out.println(
                                    "-----------------------------"
                            );
                        }
                    }

                    break;

                // =========================================
                // PRESTAR LIBRO
                // =========================================
                case 5:

                    System.out.println();
                    System.out.println("===== PRESTAR LIBRO =====");

                    System.out.print("Documento del cliente: ");
                    String idPrestamo = teclado.nextLine();

                    Cliente clientePrestamo = null;

                    for (Cliente cliente : clientes) {

                        if (cliente.getId().equals(idPrestamo)) {

                            clientePrestamo = cliente;
                            break;
                        }
                    }

                    if (clientePrestamo == null) {

                        System.out.println(
                                "El cliente no existe."
                        );

                        break;
                    }

                    System.out.print(
                            "Identificador del libro: "
                    );

                    String idLibroPrestamo = teclado.nextLine();

                    Libro libroPrestamo = null;

                    for (Libro libro : Libro.getLibros()) {

                        if (libro.getIdentificador()
                                .equals(idLibroPrestamo)) {

                            libroPrestamo = libro;
                            break;
                        }
                    }

                    if (libroPrestamo == null) {

                        System.out.println(
                                "El libro no existe."
                        );

                        break;
                    }

                    Prestamo nuevoPrestamo = new Prestamo(
                            clientePrestamo,
                            libroPrestamo
                    );

                    // Verificamos antes de hacer el préstamo
                    if (libroPrestamo.getEstado()
                            .equals("Disponible")
                            && clientePrestamo.getPrestamosActivos() == 0) {

                        nuevoPrestamo.prestarlibro();

                        prestamos.add(nuevoPrestamo);

                    } else {

                        nuevoPrestamo.prestarlibro();
                    }

                    break;

                // =========================================
                // DEVOLVER LIBRO
                // =========================================
                case 6:

                    System.out.println();
                    System.out.println("===== DEVOLVER LIBRO =====");

                    System.out.print("Documento del cliente: ");
                    String idDevolucion = teclado.nextLine();

                    Cliente clienteDevolucion = null;

                    for (Cliente cliente : clientes) {

                        if (cliente.getId().equals(idDevolucion)) {

                            clienteDevolucion = cliente;
                            break;
                        }
                    }

                    if (clienteDevolucion == null) {

                        System.out.println(
                                "El cliente no existe."
                        );

                        break;
                    }

                    System.out.print(
                            "Identificador del libro: "
                    );

                    String idLibroDevolucion = teclado.nextLine();

                    Prestamo prestamoEncontrado = null;

                    for (Prestamo prestamo : prestamos) {

                        if (prestamo.getCliente()
                                .getId()
                                .equals(idDevolucion)
                                && prestamo.getLibro()
                                .getIdentificador()
                                .equals(idLibroDevolucion)) {

                            prestamoEncontrado = prestamo;
                            break;
                        }
                    }

                    if (prestamoEncontrado == null) {

                        System.out.println(
                                "No existe un prestamo activo "
                                        + "para ese cliente y libro."
                        );

                        break;
                    }

                    Devolucion nuevaDevolucion =
                            new Devolucion(
                                    clienteDevolucion,
                                    prestamoEncontrado.getLibro()
                            );

                    nuevaDevolucion.Devolverlibro();

                    devoluciones.add(nuevaDevolucion);

                    prestamos.remove(prestamoEncontrado);

                    break;

                // =========================================
                // MOSTRAR PRESTAMOS
                // =========================================
                case 7:

                    System.out.println();
                    System.out.println(
                            "===== PRESTAMOS ACTIVOS ====="
                    );

                    if (prestamos.isEmpty()) {

                        System.out.println(
                                "No hay prestamos activos."
                        );

                    } else {

                        for (Prestamo prestamo : prestamos) {

                            System.out.println(
                                    "Cliente: "
                                            + prestamo.getCliente()
                                            .getNombre()
                            );

                            System.out.println(
                                    "Documento: "
                                            + prestamo.getCliente().getId()
                            );

                            System.out.println(
                                    "Libro: "
                                            + prestamo.getLibro().getTitulo()
                            );

                            System.out.println(
                                    "ID del libro: "
                                            + prestamo.getLibro()
                                            .getIdentificador()
                            );

                            System.out.println(
                                    "Fecha del prestamo: "
                                            + prestamo
                                            .getFechitaDeprestamo()
                            );

                            System.out.println(
                                    "-----------------------------"
                            );
                        }
                    }

                    break;

                // =========================================
                // SALIR
                // =========================================
                case 8:

                    System.out.println(
                            "Saliendo del sistema..."
                    );

                    break;

                default:

                    System.out.println(
                            "Opcion no valida."
                    );
            }

        } while (opcion != 8);

        teclado.close();
    }
}