
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Persistencia {

    private static final String ARCHIVO_LIBROS = "libros.txt";
    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_PRESTAMOS = "prestamos.txt";

    // GUARDAR TODOS LOS LIBROS
    public static void guardarLibros() {

        try (BufferedWriter escritor =
                     new BufferedWriter(new FileWriter(ARCHIVO_LIBROS))) {

            for (Libro libro : Libro.getLibros()) {

                escritor.write(
                        libro.getIdentificador() + "|"
                                + libro.getTitulo() + "|"
                                + libro.getAutor() + "|"
                                + libro.getEditorial() + "|"
                                + libro.getAnioPublicacion() + "|"
                                + libro.getCategoria() + "|"
                                + libro.getEstado()
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar los libros: "
                    + e.getMessage());
        }
    }

    // CARGAR TODOS LOS LIBROS
    public static void cargarLibros() {

        Libro.getLibros().clear();

        try (BufferedReader lector =
                     new BufferedReader(new FileReader(ARCHIVO_LIBROS))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 7) {

                    Libro libro = new Libro(
                            datos[0],
                            datos[1],
                            datos[2],
                            datos[3],
                            Integer.parseInt(datos[4]),
                            datos[5]
                    );

                    libro.cambiarEstado(datos[6]);

                    Libro.getLibros().add(libro);
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de libros.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar los libros: "
                    + e.getMessage());
        }
    }

    // GUARDAR TODOS LOS CLIENTES
    public static void guardarClientes(ArrayList<Cliente> clientes) {

        try (BufferedWriter escritor =
                     new BufferedWriter(new FileWriter(ARCHIVO_CLIENTES))) {

            for (Cliente cliente : clientes) {

                escritor.write(
                        cliente.getId() + "|"
                                + cliente.getNombre() + "|"
                                + cliente.getTelefono() + "|"
                                + cliente.getDireccion() + "|"
                                + cliente.tieneLibroPrestado()
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar los clientes: "
                    + e.getMessage());
        }
    }

    // CARGAR TODOS LOS CLIENTES
    public static void cargarClientes(ArrayList<Cliente> clientes) {

        clientes.clear();

        try (BufferedReader lector =
                     new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 5) {

                    Cliente cliente = new Cliente(
                            datos[0],
                            datos[1],
                            datos[2],
                            datos[3]
                    );

                    cliente.setTieneLibroPrestado(
                            Boolean.parseBoolean(datos[4])
                    );

                    clientes.add(cliente);
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de clientes.");
        } catch (IOException e) {
            System.out.println("Error al cargar los clientes: "
                    + e.getMessage());
        }
    }

    // GUARDAR LOS PRESTAMOS ACTIVOS
    public static void guardarPrestamos(ArrayList<Prestamo> prestamos) {

        try (BufferedWriter escritor =
                     new BufferedWriter(new FileWriter(ARCHIVO_PRESTAMOS))) {

            for (Prestamo prestamo : prestamos) {

                escritor.write(
                        prestamo.getCliente().getId() + "|"
                                + prestamo.getLibro().getIdentificador() + "|"
                                + prestamo.getFechitaDeprestamo()
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar los prestamos: "
                    + e.getMessage());
        }
    }

    // CARGAR LOS PRESTAMOS ACTIVOS
    public static void cargarPrestamos(
            ArrayList<Prestamo> prestamos,
            ArrayList<Cliente> clientes) {

        prestamos.clear();

        try (BufferedReader lector =
                     new BufferedReader(new FileReader(ARCHIVO_PRESTAMOS))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 3) {

                    Cliente clienteEncontrado = null;
                    Libro libroEncontrado = null;

                    // Buscar el cliente por su ID
                    for (Cliente cliente : clientes) {

                        if (cliente.getId().equals(datos[0])) {
                            clienteEncontrado = cliente;
                            break;
                        }
                    }

                    // Buscar el libro por su identificador
                    for (Libro libro : Libro.getLibros()) {

                        if (libro.getIdentificador().equals(datos[1])) {
                            libroEncontrado = libro;
                            break;
                        }
                    }

                    // Reconstruir el prestamo
                    if (clienteEncontrado != null
                            && libroEncontrado != null) {

                        Prestamo prestamo = new Prestamo(
                                clienteEncontrado,
                                libroEncontrado
                        );

                        prestamo.setFechitaDeprestamo(
                                LocalDateTime.parse(datos[2])
                        );

                        prestamos.add(prestamo);

                        // Restaurar el estado del cliente y del libro
                        clienteEncontrado.setTieneLibroPrestado(true);
                        libroEncontrado.cambiarEstado("Prestado");
                    }
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de prestamos.");
        } catch (IOException | java.time.format.DateTimeParseException e) {
            System.out.println("Error al cargar los prestamos: "
                    + e.getMessage());
        }
    }

    // GUARDAR TODOS LOS DATOS
    public static void guardarTodo(
            ArrayList<Cliente> clientes,
            ArrayList<Prestamo> prestamos) {

        guardarLibros();
        guardarClientes(clientes);
        guardarPrestamos(prestamos);
    }
}

