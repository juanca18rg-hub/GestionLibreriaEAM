import java.util.Scanner;


public class Entrada {

    private static Scanner teclado = new Scanner(System.in);


    public static String leerLinea() {

        if (!teclado.hasNextLine()) {

            System.out.println();
            System.out.println("No hay mas datos de entrada. Saliendo del sistema...");
            System.exit(0);
        }

        return teclado.nextLine();
    }


    public static String leerTexto(String mensaje) {

        String texto = "";
        boolean valido = false;

        while (!valido) {

            System.out.print(mensaje);
            texto = leerLinea().trim();

            if (texto.isEmpty()) {

                System.out.println("Error: este campo no puede estar vacio.");

            } else if (texto.contains("|")) {

                System.out.println("Error: no se permite el caracter |");

            } else {

                valido = true;
            }
        }

        return texto;
    }


    public static int leerEntero(String mensaje) {

        int numero = 0;
        boolean valido = false;

        while (!valido) {

            System.out.print(mensaje);
            String texto = leerLinea().trim();

            try {

                numero = Integer.parseInt(texto);
                valido = true;

            } catch (NumberFormatException e) {

                System.out.println("Error: debe escribir un numero entero.");
            }
        }

        return numero;
    }


    public static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {

        int numero = leerEntero(mensaje);

        while (numero < minimo || numero > maximo) {

            System.out.println("Error: el numero debe estar entre "
                    + minimo + " y " + maximo + ".");

            numero = leerEntero(mensaje);
        }

        return numero;
    }


    public static String leerTelefono(String mensaje) {

        String telefono = "";
        boolean valido = false;

        while (!valido) {

            telefono = leerTexto(mensaje);

            boolean soloNumeros = true;

            for (int i = 0; i < telefono.length(); i++) {

                if (!Character.isDigit(telefono.charAt(i))) {
                    soloNumeros = false;
                }
            }

            if (!soloNumeros || telefono.length() < 7 || telefono.length() > 10) {

                System.out.println(
                        "Error: el telefono debe tener solo numeros (entre 7 y 10 digitos)."
                );

            } else {

                valido = true;
            }
        }

        return telefono;
    }


    public static boolean confirmar(String mensaje) {

        while (true) {

            System.out.print(mensaje + " (S/N): ");
            String respuesta = leerLinea().trim();

            if (respuesta.equalsIgnoreCase("S")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Error: escriba S o N.");
        }
    }
}
