import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Persistencia {


    private static final String DIRECTORIO = obtenerDirectorio();

    private static final String ARCHIVO_LIBROS = "libros.txt";
    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_PRESTAMOS = "prestamos.txt";
    private static final String ARCHIVO_HISTORIAL = "historial.txt";
    private static final String ARCHIVO_MULTAS = "multas.txt";
    private static final String ARCHIVO_RESERVAS = "reservas.txt";

    private static String obtenerDirectorio() {

        String directorio = System.getenv("DATOS_DIR");

        if (directorio == null || directorio.trim().isEmpty()) {
            directorio = "datos";
        }

        return directorio;
    }

    private static File archivo(String nombre) {

        File carpeta = new File(DIRECTORIO);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        return new File(carpeta, nombre);
    }

    private static BufferedWriter abrirEscritor(String nombre) throws IOException {
        return new BufferedWriter(
                new FileWriter(archivo(nombre), StandardCharsets.UTF_8));
    }

    private static BufferedReader abrirLector(String nombre) throws IOException {
        return new BufferedReader(
                new FileReader(archivo(nombre), StandardCharsets.UTF_8));
    }

    private static String fechaATexto(LocalDateTime fecha) {

        if (fecha == null) {
            return "";
        }

        return fecha.toString();
    }

    private static LocalDateTime textoAFecha(String texto) {

        if (texto.isEmpty() || texto.equals("null")) {
            return null;
        }

        return LocalDateTime.parse(texto);
    }

    private static Cliente buscarCliente(ArrayList<Cliente> clientes, String id) {

        for (Cliente cliente : clientes) {

            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }

        return null;
    }


    public static void guardarLibros() {

        try (BufferedWriter escritor = abrirEscritor(ARCHIVO_LIBROS)) {

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


    public static void cargarLibros() {

        Libro.getLibros().clear();

        try (BufferedReader lector = abrirLector(ARCHIVO_LIBROS)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 7) {

                    try {

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

                    } catch (NumberFormatException e) {
                        System.out.println("Linea de libro invalida omitida: "
                                + linea);
                    }
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de libros.");
        } catch (IOException e) {
            System.out.println("Error al cargar los libros: "
                    + e.getMessage());
        }
    }


    public static void guardarClientes(ArrayList<Cliente> clientes) {

        try (BufferedWriter escritor = abrirEscritor(ARCHIVO_CLIENTES)) {

            for (Cliente cliente : clientes) {

                escritor.write(
                        cliente.getId() + "|"
                                + cliente.getNombre() + "|"
                                + cliente.getTelefono() + "|"
                                + cliente.getDireccion() + "|"
                                + cliente.tieneLibroPrestado() + "|"
                                + cliente.getMultaPendiente()
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar los clientes: "
                    + e.getMessage());
        }
    }


    public static void cargarClientes(ArrayList<Cliente> clientes) {

        clientes.clear();

        try (BufferedReader lector = abrirLector(ARCHIVO_CLIENTES)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);


                if (datos.length == 5 || datos.length == 6) {

                    try {

                        Cliente cliente = new Cliente(
                                datos[0],
                                datos[1],
                                datos[2],
                                datos[3]
                        );

                        cliente.setTieneLibroPrestado(
                                Boolean.parseBoolean(datos[4])
                        );

                        if (datos.length == 6) {
                            cliente.setMultaPendiente(
                                    Integer.parseInt(datos[5])
                            );
                        }

                        clientes.add(cliente);

                    } catch (NumberFormatException e) {
                        System.out.println("Linea de cliente invalida omitida: "
                                + linea);
                    }
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de clientes.");
        } catch (IOException e) {
            System.out.println("Error al cargar los clientes: "
                    + e.getMessage());
        }
    }


    public static void guardarPrestamos(ArrayList<Prestamo> prestamos) {

        try (BufferedWriter escritor = abrirEscritor(ARCHIVO_PRESTAMOS)) {

            for (Prestamo prestamo : prestamos) {

                escritor.write(
                        prestamo.getCliente().getId() + "|"
                                + prestamo.getLibro().getIdentificador() + "|"
                                + fechaATexto(prestamo.getFechitaDeprestamo()) + "|"
                                + fechaATexto(prestamo.getFechaLimite())
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar los prestamos: "
                    + e.getMessage());
        }
    }


    public static void cargarPrestamos(
            ArrayList<Prestamo> prestamos,
            ArrayList<Cliente> clientes) {

        prestamos.clear();

        try (BufferedReader lector = abrirLector(ARCHIVO_PRESTAMOS)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 3 || datos.length == 4) {

                    Cliente clienteEncontrado = buscarCliente(clientes, datos[0]);
                    Libro libroEncontrado = Libro.buscarPorId(datos[1]);

                    if (clienteEncontrado != null
                            && libroEncontrado != null) {

                        try {

                            Prestamo prestamo = new Prestamo(
                                    clienteEncontrado,
                                    libroEncontrado
                            );

                            LocalDateTime fechaPrestamo = LocalDateTime.parse(datos[2]);

                            prestamo.setFechitaDeprestamo(fechaPrestamo);

                            if (datos.length == 4 && !datos[3].isEmpty()) {

                                prestamo.setFechaLimite(
                                        LocalDateTime.parse(datos[3])
                                );

                            } else {

                                prestamo.setFechaLimite(
                                        fechaPrestamo.plusDays(Prestamo.DIAS_PRESTAMO)
                                );
                            }

                            prestamos.add(prestamo);

                            clienteEncontrado.setTieneLibroPrestado(true);
                            libroEncontrado.cambiarEstado("Prestado");

                        } catch (DateTimeParseException e) {
                            System.out.println("Linea de prestamo invalida omitida: "
                                    + linea);
                        }
                    }
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de prestamos.");
        } catch (IOException e) {
            System.out.println("Error al cargar los prestamos: "
                    + e.getMessage());
        }
    }


    public static void guardarHistorial(ArrayList<Devolucion> devoluciones) {

        try (BufferedWriter escritor = abrirEscritor(ARCHIVO_HISTORIAL)) {

            for (Devolucion devolucion : devoluciones) {

                escritor.write(
                        devolucion.getCliente().getId() + "|"
                                + devolucion.getLibro().getIdentificador() + "|"
                                + fechaATexto(devolucion.getFechaPrestamo()) + "|"
                                + fechaATexto(devolucion.getFechaDevolucion())
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar el historial: "
                    + e.getMessage());
        }
    }


    public static void cargarHistorial(
            ArrayList<Devolucion> devoluciones,
            ArrayList<Cliente> clientes) {

        devoluciones.clear();

        try (BufferedReader lector = abrirLector(ARCHIVO_HISTORIAL)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 4) {

                    Cliente clienteEncontrado = buscarCliente(clientes, datos[0]);
                    Libro libroEncontrado = Libro.buscarPorId(datos[1]);

                    if (clienteEncontrado != null
                            && libroEncontrado != null) {

                        try {

                            Devolucion devolucion = new Devolucion(
                                    clienteEncontrado,
                                    libroEncontrado
                            );

                            devolucion.setFechaPrestamo(textoAFecha(datos[2]));
                            devolucion.setFechaDevolucion(textoAFecha(datos[3]));

                            devoluciones.add(devolucion);

                        } catch (DateTimeParseException e) {
                            System.out.println("Linea de historial invalida omitida: "
                                    + linea);
                        }
                    }
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de historial.");
        } catch (IOException e) {
            System.out.println("Error al cargar el historial: "
                    + e.getMessage());
        }
    }


    public static void guardarMultas(ArrayList<Multa> multas) {

        try (BufferedWriter escritor = abrirEscritor(ARCHIVO_MULTAS)) {

            for (Multa multa : multas) {

                escritor.write(
                        multa.getCliente().getId() + "|"
                                + multa.getLibro().getIdentificador() + "|"
                                + multa.getDiasRetraso() + "|"
                                + multa.getValor() + "|"
                                + fechaATexto(multa.getFecha()) + "|"
                                + multa.estaPagada()
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar las multas: "
                    + e.getMessage());
        }
    }


    public static void cargarMultas(
            ArrayList<Multa> multas,
            ArrayList<Cliente> clientes) {

        multas.clear();

        try (BufferedReader lector = abrirLector(ARCHIVO_MULTAS)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 6) {

                    Cliente clienteEncontrado = buscarCliente(clientes, datos[0]);
                    Libro libroEncontrado = Libro.buscarPorId(datos[1]);

                    if (clienteEncontrado != null
                            && libroEncontrado != null) {

                        try {

                            Multa multa = new Multa(
                                    clienteEncontrado,
                                    libroEncontrado,
                                    Integer.parseInt(datos[2]),
                                    Integer.parseInt(datos[3]),
                                    textoAFecha(datos[4])
                            );

                            multa.setPagada(Boolean.parseBoolean(datos[5]));

                            multas.add(multa);

                        } catch (NumberFormatException | DateTimeParseException e) {
                            System.out.println("Linea de multa invalida omitida: "
                                    + linea);
                        }
                    }
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de multas.");
        } catch (IOException e) {
            System.out.println("Error al cargar las multas: "
                    + e.getMessage());
        }
    }


    public static void guardarReservas() {

        try (BufferedWriter escritor = abrirEscritor(ARCHIVO_RESERVAS)) {

            for (Libro libro : Libro.getLibros()) {

                for (Reserva reserva : libro.getReservas()) {

                    escritor.write(
                            libro.getIdentificador() + "|"
                                    + reserva.getCliente().getId() + "|"
                                    + fechaATexto(reserva.getFechaReserva())
                    );

                    escritor.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error al guardar las reservas: "
                    + e.getMessage());
        }
    }


    public static void cargarReservas(ArrayList<Cliente> clientes) {

        for (Libro libro : Libro.getLibros()) {
            libro.getReservas().clear();
        }

        try (BufferedReader lector = abrirLector(ARCHIVO_RESERVAS)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 3) {

                    Libro libroEncontrado = Libro.buscarPorId(datos[0]);
                    Cliente clienteEncontrado = buscarCliente(clientes, datos[1]);

                    if (clienteEncontrado != null
                            && libroEncontrado != null) {

                        try {

                            libroEncontrado.agregarReserva(
                                    new Reserva(
                                            clienteEncontrado,
                                            libroEncontrado,
                                            textoAFecha(datos[2])
                                    )
                            );

                        } catch (DateTimeParseException e) {
                            System.out.println("Linea de reserva invalida omitida: "
                                    + linea);
                        }
                    }
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Aun no existe el archivo de reservas.");
        } catch (IOException e) {
            System.out.println("Error al cargar las reservas: "
                    + e.getMessage());
        }
    }


    public static void guardarTodo(
            ArrayList<Cliente> clientes,
            ArrayList<Prestamo> prestamos,
            ArrayList<Devolucion> devoluciones,
            ArrayList<Multa> multas) {

        guardarLibros();
        guardarClientes(clientes);
        guardarPrestamos(prestamos);
        guardarHistorial(devoluciones);
        guardarMultas(multas);
        guardarReservas();
    }
}
