import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fechas {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public static String formatear(LocalDateTime fecha) {

        if (fecha == null) {
            return "-";
        }

        return fecha.format(FORMATO);
    }
}
