package flow;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Locale;
import java.util.Scanner;

public final class UtilidadesFlujo {

    private static final Scanner IN = new Scanner(System.in);

    private UtilidadesFlujo() {} // no instanciable

    // ===== Entrada básica =====

    public static String leerNoVacio(String prompt) {
        String s;
        do {
            System.out.print(prompt);
            s = IN.nextLine().trim();
            if (s.isBlank()) System.out.println("El valor no puede ser vacío.");
        } while (s.isBlank());
        return s;
    }

    public static String leerOpcional(String prompt) {
        System.out.print(prompt);
        return IN.nextLine().trim();
    }

    public static int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = IN.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (Exception ignored) {
                System.out.println("Ingrese un número entero.");
            }
        }
    }

    public static int leerEnteroPositivo(String prompt) {
        int v;
        do {
            v = leerEntero(prompt);
            if (v <= 0) System.out.println("Debe ser > 0.");
        } while (v <= 0);
        return v;
    }

    public static short leerShortPositivo(String prompt) {
        return (short) leerEnteroPositivo(prompt);
    }

    public static byte leerBytePositivo(String prompt) {
        int v;
        do {
            v = leerEnteroPositivo(prompt);
            if (v > Byte.MAX_VALUE) System.out.println("Debe caber en un byte (<= 127).");
        } while (v > Byte.MAX_VALUE);
        return (byte) v;
    }

    public static BigDecimal leerBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = IN.nextLine().trim();
            s = s.replace(',', '.'); // admite coma o punto
            try {
                // Evitamos cambiar el Locale global
                return new BigDecimal(s);
            } catch (Exception ignored) {
                System.out.println("Ingrese un decimal válido. Ej: 1499.99");
            }
        }
    }

    public static Date leerFechaSql(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = IN.nextLine().trim();
            try {
                // Formato esperado: YYYY-MM-DD
                return Date.valueOf(s);
            } catch (Exception ignored) {
                System.out.println("Formato inválido. Use YYYY-MM-DD (ej: 2025-09-01).");
            }
        }
    }

    public static boolean leerBooleanSiNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = IN.nextLine().trim().toLowerCase(Locale.ROOT);
            if (s.equals("s")) return true;
            if (s.equals("n")) return false;
            System.out.println("Responda 's' o 'n'.");
        }
    }

    public static boolean confirmar(String prompt) {
        return leerBooleanSiNo(prompt);
    }

    // ===== Parsers "con defecto" para pantallas de modificar =====

    public static BigDecimal parseBigDecimalOr(String raw, BigDecimal def) {
        if (raw == null || raw.isBlank()) return def;
        try { return new BigDecimal(raw.trim().replace(',', '.')); } catch (Exception ignored) { return def; }
    }

    public static int parseEnteroOr(String raw, int def) {
        if (raw == null || raw.isBlank()) return def;
        try { return Integer.parseInt(raw.trim()); } catch (Exception ignored) { return def; }
    }

    public static short parseShortOr(String raw, short def) {
        return (short) parseEnteroOr(raw, def);
    }

    public static byte parseByteOr(String raw, byte def) {
        return (byte) parseEnteroOr(raw, def);
    }

    public static boolean parseSiNoOr(String raw, boolean def) {
        if (raw == null || raw.isBlank()) return def;
        String s = raw.trim().toLowerCase(Locale.ROOT);
        if (s.equals("s")) return true;
        if (s.equals("n")) return false;
        return def;
    }

    public static Date parseFechaOr(String raw, Date def) {
        if (raw == null || raw.isBlank()) return def;
        try { return Date.valueOf(raw.trim()); } catch (Exception ignored) { return def; }
    }

    // ===== Formato / tabla =====

    public static String nvl(Object o) { return o == null ? "" : String.valueOf(o); }

    public static void printHeader(String[] headers, int[] widths) {
        String line = formatRow(headers, widths);
        System.out.println("\n" + line);
        System.out.println(repeat('-', line.length()));
    }

    public static String formatRow(Object[] values, int[] widths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            String s = nvl(values[i]);
            if (s.length() > widths[i]) s = s.substring(0, widths[i]);
            sb.append(String.format("%-" + widths[i] + "s", s));
            if (i < values.length - 1) sb.append(" | ");
        }
        return sb.toString();
    }

    public static String repeat(char c, int n) {
        return String.valueOf(c).repeat(Math.max(0, n));
    }
}
