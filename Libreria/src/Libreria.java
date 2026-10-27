import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Libreria {


    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Prestamo> prestamos = new ArrayList<>();
    private static ArrayList<Devolucion> devoluciones = new ArrayList<>();
    private static ArrayList<Multa> multas = new ArrayList<>();

    public static void main(String[] args) {


        Persistencia.cargarLibros();
        Persistencia.cargarClientes(clientes);
        Persistencia.cargarPrestamos(prestamos, clientes);
        Persistencia.cargarHistorial(devoluciones, clientes);
        Persistencia.cargarMultas(multas, clientes);
        Persistencia.cargarReservas(clientes);

        System.out.println("Datos cargados correctamente.");

        int opcion;

        do {

            System.out.println();
            System.out.println("======================================");
            System.out.println("         BIBLIOTECA LIBROSYMAS");
            System.out.println("======================================");
            System.out.println("1. Registrar libro");
            System.out.println("2. Mostrar inventario de libros");
            System.out.println("3. Registrar cliente");
            System.out.println("4. Mostrar clientes");
            System.out.println("5. Prestar libro");
            System.out.println("6. Devolver libro");
            System.out.println("7. Mostrar prestamos activos");
            System.out.println("8. Ver multas de un cliente");
            System.out.println("9. Pagar multas de un cliente");
            System.out.println("10. Registrar reserva de un libro");
            System.out.println("11. Consultar reservas de un libro");
            System.out.println("12. Reporte general del sistema");
            System.out.println("13. Salir");
            System.out.println("======================================");

            opcion = Entrada.leerEntero("Seleccione una opcion: ");

            switch (opcion) {

                case 1:
                    registrarLibro();
                    break;

                case 2:
                    System.out.println();
                    Libro.listarLibros();
                    break;

                case 3:
                    registrarCliente();
                    break;

                case 4:
                    mostrarClientes();
                    break;

                case 5:
                    prestarLibro();
                    break;

                case 6:
                    devolverLibro();
                    break;

                case 7:
                    mostrarPrestamosActivos();
                    break;

                case 8:
                    verMultasDeCliente();
                    break;

                case 9:
                    pagarMultasDeCliente();
                    break;

                case 10:
                    registrarReserva();
                    break;

                case 11:
                    consultarReservasDeLibro();
                    break;

                case 12:
                    mostrarReporteGeneral();
                    break;

                case 13:
                    guardarTodo();
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 13);
    }



    private static void guardarTodo() {
        Persistencia.guardarTodo(clientes, prestamos, devoluciones, multas);
    }

    private static Cliente buscarCliente(String id) {

        for (Cliente cliente : clientes) {

            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }

        return null;
    }


    private static Cliente pedirCliente() {

        String id = Entrada.leerTexto("ID del cliente: ");

        Cliente cliente = buscarCliente(id);

        if (cliente == null) {
            System.out.println("El cliente no existe.");
        }

        return cliente;
    }


    private static Libro pedirLibro() {

        String id = Entrada.leerTexto("ID del libro: ");

        Libro libro = Libro.buscarPorId(id);

        if (libro == null) {
            System.out.println("El libro no existe.");
        }

        return libro;
    }


    private static int contarPrestamosDeCliente(Cliente cliente) {

        int total = 0;

        for (Prestamo prestamo : prestamos) {

            if (prestamo.getCliente().getId().equals(cliente.getId())) {
                total++;
            }
        }

        for (Devolucion devolucion : devoluciones) {

            if (devolucion.getCliente().getId().equals(cliente.getId())) {
                total++;
            }
        }

        return total;
    }


    private static int contarPrestamosDeLibro(Libro libro) {

        int total = 0;

        for (Prestamo prestamo : prestamos) {

            if (prestamo.getLibro().getIdentificador()
                    .equals(libro.getIdentificador())) {
                total++;
            }
        }

        for (Devolucion devolucion : devoluciones) {

            if (devolucion.getLibro().getIdentificador()
                    .equals(libro.getIdentificador())) {
                total++;
            }
        }

        return total;
    }


    private static void registrarLibro() {

        System.out.println();
        System.out.println("===== REGISTRAR LIBRO =====");

        String identificador = Entrada.leerTexto("Identificador: ");

        if (Libro.buscarPorId(identificador) != null) {
            System.out.println("Error: ya existe un libro con ese identificador.");
            return;
        }

        String titulo = Entrada.leerTexto("Titulo: ");
        String autor = Entrada.leerTexto("Autor: ");
        String editorial = Entrada.leerTexto("Editorial: ");

        int anioActual = LocalDate.now().getYear();
        int anio = Entrada.leerEnteroEnRango(
                "Año de publicacion: ", 1000, anioActual);

        System.out.println();
        System.out.println("Categorias:");
        System.out.println("1. Literatura");
        System.out.println("2. Ciencia");
        System.out.println("3. Historia");
        System.out.println("4. Tecnologia");

        int opcionCategoria = Entrada.leerEnteroEnRango(
                "Seleccione una categoria: ", 1, 4);

        String categoria;

        if (opcionCategoria == 1) {
            categoria = "Literatura";
        } else if (opcionCategoria == 2) {
            categoria = "Ciencia";
        } else if (opcionCategoria == 3) {
            categoria = "Historia";
        } else {
            categoria = "Tecnologia";
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
        Persistencia.guardarLibros();
    }


    private static void registrarCliente() {

        System.out.println();
        System.out.println("===== REGISTRAR CLIENTE =====");

        String idCliente = Entrada.leerTexto("ID del cliente: ");

        if (buscarCliente(idCliente) != null) {
            System.out.println("Ya existe un cliente con ese ID.");
            return;
        }

        String nombreCliente = Entrada.leerTexto("Nombre: ");
        String telefonoCliente = Entrada.leerTelefono("Telefono: ");
        String direccionCliente = Entrada.leerTexto("Direccion: ");

        Cliente nuevoCliente = new Cliente(
                idCliente,
                nombreCliente,
                telefonoCliente,
                direccionCliente
        );

        clientes.add(nuevoCliente);
        Persistencia.guardarClientes(clientes);

        System.out.println("Cliente registrado correctamente.");
    }



    private static void mostrarClientes() {

        System.out.println();
        System.out.println("===== CLIENTES REGISTRADOS =====");

        if (clientes.isEmpty()) {

            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : clientes) {

            cliente.mostrarInformacion();
            System.out.println("-----------------------------");
        }
    }


    private static void prestarLibro() {

        System.out.println();
        System.out.println("===== PRESTAR LIBRO =====");

        Cliente cliente = pedirCliente();

        if (cliente == null) {
            return;
        }

        Libro libro = pedirLibro();

        if (libro == null) {
            return;
        }

        Prestamo nuevoPrestamo = new Prestamo(cliente, libro);

        boolean libroDisponible =
                libro.getEstado().equalsIgnoreCase("Disponible");

        boolean clienteLibre = !cliente.tieneLibroPrestado();

        nuevoPrestamo.prestarlibro();

        if (libroDisponible && clienteLibre) {

            prestamos.add(nuevoPrestamo);

            // Si el cliente estaba en la lista de espera, su reserva se cumple
            if (libro.quitarReservaDeCliente(cliente)) {
                System.out.println(
                        "Su reserva de este libro se cumplio y salio de la lista de espera."
                );
            }

            guardarTodo();

        } else if (!libroDisponible) {

            System.out.println(
                    "Puede reservarlo con la opcion 10 del menu."
            );
        }
    }



    private static void devolverLibro() {

        System.out.println();
        System.out.println("===== DEVOLVER LIBRO =====");

        Cliente cliente = pedirCliente();

        if (cliente == null) {
            return;
        }

        String idLibro = Entrada.leerTexto("ID del libro: ");

        Prestamo prestamoEncontrado = null;

        for (Prestamo prestamo : prestamos) {

            if (prestamo.getCliente().getId().equals(cliente.getId())
                    && prestamo.getLibro().getIdentificador().equals(idLibro)) {

                prestamoEncontrado = prestamo;
                break;
            }
        }

        if (prestamoEncontrado == null) {

            System.out.println(
                    "No existe un prestamo activo para ese cliente y libro."
            );

            return;
        }

        Libro libro = prestamoEncontrado.getLibro();

        Devolucion nuevaDevolucion = new Devolucion(cliente, libro);
        nuevaDevolucion.setFechaPrestamo(
                prestamoEncontrado.getFechitaDeprestamo()
        );

        nuevaDevolucion.devolverLibro();

        devoluciones.add(nuevaDevolucion);
        prestamos.remove(prestamoEncontrado);


        int diasRetraso = prestamoEncontrado.calcularDiasRetraso(
                nuevaDevolucion.getFechaDevolucion()
        );

        if (diasRetraso > 0) {

            int valorMulta = Multa.calcularValor(diasRetraso);

            Multa nuevaMulta = new Multa(
                    cliente,
                    libro,
                    diasRetraso,
                    valorMulta,
                    nuevaDevolucion.getFechaDevolucion()
            );

            multas.add(nuevaMulta);
            cliente.agregarMulta(valorMulta);

            System.out.println();
            System.out.println("El libro se devolvio con " + diasRetraso
                    + " dia(s) de retraso.");
            System.out.println("Multa generada: $" + valorMulta + " COP");
            System.out.println("Total pendiente de " + cliente.getNombre()
                    + ": $" + cliente.getMultaPendiente() + " COP");

        } else {

            System.out.println("Devuelto a tiempo. No se genera multa.");
        }


        Reserva siguiente = libro.siguienteReserva();

        if (siguiente != null) {

            System.out.println();
            System.out.println("El libro tiene reservas pendientes.");
            System.out.println("Siguiente cliente en la lista de espera: "
                    + siguiente.getCliente().getNombre()
                    + " (ID: " + siguiente.getCliente().getId() + ")");
        }

        guardarTodo();
    }



    private static void mostrarPrestamosActivos() {

        System.out.println();
        System.out.println("===== PRESTAMOS ACTIVOS =====");

        if (prestamos.isEmpty()) {

            System.out.println("No hay prestamos activos.");
            return;
        }

        for (Prestamo prestamo : prestamos) {

            System.out.println("Cliente: " + prestamo.getCliente().getNombre());
            System.out.println("ID cliente: " + prestamo.getCliente().getId());
            System.out.println("Libro: " + prestamo.getLibro().getTitulo());
            System.out.println("ID libro: " + prestamo.getLibro().getIdentificador());
            System.out.println("Fecha del prestamo: "
                    + Fechas.formatear(prestamo.getFechitaDeprestamo()));
            System.out.println("Fecha limite de devolucion: "
                    + Fechas.formatear(prestamo.getFechaLimite()));
            System.out.println("-----------------------------");
        }
    }



    private static void verMultasDeCliente() {

        System.out.println();
        System.out.println("===== MULTAS DE UN CLIENTE =====");

        Cliente cliente = pedirCliente();

        if (cliente == null) {
            return;
        }

        System.out.println();
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Total pendiente: $"
                + cliente.getMultaPendiente() + " COP");
        System.out.println("-----------------------------");

        boolean tieneMultas = false;

        for (Multa multa : multas) {

            if (multa.getCliente().getId().equals(cliente.getId())) {

                multa.mostrarInformacion();
                tieneMultas = true;
            }
        }

        if (!tieneMultas) {
            System.out.println("El cliente no tiene multas registradas.");
        }
    }



    private static void pagarMultasDeCliente() {

        System.out.println();
        System.out.println("===== PAGAR MULTAS =====");

        Cliente cliente = pedirCliente();

        if (cliente == null) {
            return;
        }

        if (cliente.getMultaPendiente() == 0) {

            System.out.println("El cliente no tiene multas pendientes.");
            return;
        }

        System.out.println("Total pendiente de " + cliente.getNombre()
                + ": $" + cliente.getMultaPendiente() + " COP");

        if (!Entrada.confirmar("Desea registrar el pago total")) {

            System.out.println("Pago cancelado.");
            return;
        }

        cliente.pagarMultas();

        for (Multa multa : multas) {

            if (multa.getCliente().getId().equals(cliente.getId())) {
                multa.setPagada(true);
            }
        }

        guardarTodo();

        System.out.println("Pago registrado. El cliente ya no tiene multas pendientes.");
    }



    private static void registrarReserva() {

        System.out.println();
        System.out.println("===== REGISTRAR RESERVA =====");

        Cliente cliente = pedirCliente();

        if (cliente == null) {
            return;
        }

        Libro libro = pedirLibro();

        if (libro == null) {
            return;
        }


        if (libro.getEstado().equalsIgnoreCase("Disponible")) {

            System.out.println(
                    "El libro esta disponible, no necesita reserva. "
                            + "Puede prestarlo con la opcion 5."
            );

            return;
        }


        for (Prestamo prestamo : prestamos) {

            if (prestamo.getCliente().getId().equals(cliente.getId())
                    && prestamo.getLibro().getIdentificador()
                    .equals(libro.getIdentificador())) {

                System.out.println("Ese libro ya lo tiene prestado este cliente.");
                return;
            }
        }


        if (libro.buscarReservaDeCliente(cliente) != null) {

            System.out.println("El cliente ya tiene una reserva para este libro.");
            return;
        }

        libro.agregarReserva(new Reserva(cliente, libro, LocalDateTime.now()));

        Persistencia.guardarReservas();

        System.out.println("Reserva registrada correctamente.");
        System.out.println("Posicion en la lista de espera: "
                + libro.getReservas().size());
    }



    private static void consultarReservasDeLibro() {

        System.out.println();
        System.out.println("===== RESERVAS DE UN LIBRO =====");

        Libro libro = pedirLibro();

        if (libro == null) {
            return;
        }

        System.out.println();
        System.out.println("Libro: " + libro.getTitulo());
        System.out.println("Estado: " + libro.getEstado());
        System.out.println("-----------------------------");

        if (libro.getReservas().isEmpty()) {

            System.out.println("El libro no tiene reservas.");
            return;
        }

        System.out.println("Lista de espera (en orden de solicitud):");

        int posicion = 1;

        for (Reserva reserva : libro.getReservas()) {

            System.out.println(posicion + ". "
                    + reserva.getCliente().getNombre()
                    + " (ID: " + reserva.getCliente().getId() + ")"
                    + " - reservado el "
                    + Fechas.formatear(reserva.getFechaReserva()));

            posicion++;
        }
    }



    private static void mostrarReporteGeneral() {

        int totalLibros = Libro.getLibros().size();
        int totalClientes = clientes.size();
        int prestamosActivos = prestamos.size();
        int prestamosHistoricos = prestamos.size() + devoluciones.size();

        System.out.println();
        System.out.println("======================================");
        System.out.println("          REPORTE GENERAL");
        System.out.println("======================================");
        System.out.println("Total de libros registrados: " + totalLibros);
        System.out.println("Total de clientes: " + totalClientes);
        System.out.println("Prestamos activos: " + prestamosActivos);
        System.out.println("Total de prestamos historicos: " + prestamosHistoricos);


        if (prestamosHistoricos == 0) {

            System.out.println("Cliente con mas prestamos: aun no hay prestamos.");

        } else {

            Cliente mejorCliente = null;
            int mayorCantidad = 0;

            for (Cliente cliente : clientes) {

                int cantidad = contarPrestamosDeCliente(cliente);

                if (cantidad > mayorCantidad) {
                    mayorCantidad = cantidad;
                    mejorCliente = cliente;
                }
            }

            System.out.println("Cliente con mas prestamos: "
                    + mejorCliente.getNombre()
                    + " (ID: " + mejorCliente.getId() + ") - "
                    + mayorCantidad + " prestamo(s)");
        }


        if (totalLibros == 0) {

            System.out.println("Libro menos solicitado: no hay libros registrados.");

        } else {

            Libro menosSolicitado = null;
            int menorCantidad = 0;
            int empatados = 0;

            for (Libro libro : Libro.getLibros()) {

                int cantidad = contarPrestamosDeLibro(libro);

                if (menosSolicitado == null || cantidad < menorCantidad) {

                    menosSolicitado = libro;
                    menorCantidad = cantidad;
                    empatados = 0;

                } else if (cantidad == menorCantidad) {

                    empatados++;
                }
            }

            System.out.println("Libro menos solicitado: "
                    + menosSolicitado.getTitulo()
                    + " (ID: " + menosSolicitado.getIdentificador() + ") - "
                    + menorCantidad + " prestamo(s)");

            if (empatados > 0) {

                System.out.println("   (otros " + empatados
                        + " libro(s) tienen la misma cantidad de prestamos)");
            }
        }

        System.out.println("======================================");
    }
}
