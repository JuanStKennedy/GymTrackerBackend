package flow;
import dao.PlanDAO;
import model.Plan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PlanFlujo {
    private final PlanDAO planDAO;
    private final Scanner in;

    public PlanFlujo() {
        planDAO = new PlanDAO();
        in = new Scanner(System.in);
    }

    public void run(){
        int op;
                do{
                    mostrarMenuPrincipal();
                    System.out.println("Opcion: ");
                    op = in.nextInt();
                    in.nextLine();
                    manejarOpcionPrincipal(op);
                }while (op!=0);
                System.out.println("Saliendo del menú Plan...");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== MENU PLANES ===");
        System.out.println("1) Alta de plan");
        System.out.println("2) Baja (eliminar) plan");
        System.out.println("3) Modificar plan");
        System.out.println("4) Listar planes");
        System.out.println("5) Buscar plan por ID");
        System.out.println("0) Salir");
    }

    private void manejarOpcionPrincipal(int opcion){
        switch (opcion){
            case 1->altaPlan();
            case 2->bajaPlan();
            case 3->modificarPlan();
            case 4->listarMenu();
            case 5->buscarPorId();
            case 0-> {}//salir
            default -> System.out.println("Opcion no valida");
        }
    }

    //acciones del flujo
    private void altaPlan(){
        System.out.println("       Alta de plan");
        //como se repite mucho mucho lo de leer texto/dato teniendo que imprimir y escanear
        //busqué como ahorrar líneas a partir de funciones que de paso verifiquen que sean
        //datos válidos, esto podría ser transversal a tod0 el proyecto...
        String nombre = leerNoVacio("Nombre: ");
        BigDecimal valor = leerBigDecimal("Valor (Ej: 1459.99");
        short duracionTotal = (short)leerEntero("Duracion total (numero): ");
        byte duracionUnidad = (byte)leerEntero("Duracion unidad: ");
        String urlImagen = leerOpcional("URL Imagen (Opcional, enter para omitir)");
        boolean estado = leerBooleanSiNo("¿Activo? (s/n): ");

        Plan p = new Plan(nombre, valor, duracionTotal, duracionUnidad,
                (urlImagen.isBlank() ? null : urlImagen), estado); //breve chequeo para url.
        planDAO.agregarPlan(p);
    }

    private void bajaPlan(){
        System.out.println("       Baja de plan");
        int id = leerEntero("ID del plan a eliiminar: ");
        if (leerBooleanSiNo("¿Confirmar eliminacion? (s/n)")){
            boolean ok = planDAO.eliminarPlan(id);
            if (ok)System.out.println("Plan eliminado");
        }
    }

    private void modificarPlan(){
        System.out.println("       Modificar plan");
        int id = leerEntero("ID del plan a modificar: ");
        Plan actual = planDAO.buscarPorId(id);
        if (actual==null){
            System.out.println("No existe un plan con ese ID");
            return;
        }

        System.out.println("Valores actuales (Enter para mantener)");
        System.out.println(detallePlanLinea(actual)); //formateamos en texto de 1 linea

        //Aqui logramos que se imprima la linea sin saltear para que si el usr quiere modificar, deba alterar la linea y dar enter
        String nombre = leerOpcional("Nombre [" + actual.getNombre() + "]: ");
        String valorStr = leerOpcional("Valor [" + actual.getValor() + "]: ");
        String durTotStr = leerOpcional("Duración total [" + actual.getDuracionTotal() + "]: ");
        String durUniStr = leerOpcional("Unidad duración ID [" + actual.getDuracionUnidadId() + "]: ");
        String urlImg = leerOpcional("URL Imagen [" + actual.getUrlImagen() + "]: ");
        String estadoStr = leerOpcional("¿Activo? (s/n) [" + (actual.isEstado() ? "s" : "n") + "]: ");

        //Chequeamos valores y actualizamos en actual
        if (!nombre.isBlank()) actual.setNombre(nombre);
        if (!valorStr.isBlank()) actual.setValor(parseBigDecimal(valorStr, actual.getValor()));
        if (!durTotStr.isBlank()) actual.setDuracionTotal((short) parseEntero(durTotStr, actual.getDuracionTotal()));
        if (!durUniStr.isBlank()) actual.setDuracionUnidadId((byte) parseEntero(durUniStr, actual.getDuracionUnidadId()));
        if (!urlImg.isBlank()) actual.setUrlImagen(urlImg);
        if (!estadoStr.isBlank()) actual.setEstado(parseSiNo(estadoStr, actual.isEstado()));

        int filas = planDAO.modificarPlan(actual);
        if (filas > 0) System.out.println("Modificado.");
    }

    private void listarMenu(){
        int op;
        do {
            System.out.println("        Listar");
            System.out.println("1) Listar todos");
            System.out.println("2) Listar activos");
            System.out.println("3) Listar inactivos");
            System.out.println("0) Volver");
            op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> listarOrdenado(planDAO.listarTodos());
                case 2 -> listarOrdenado(planDAO.listarActivos());
                case 3 -> listarOrdenado(planDAO.listarInactivos());
                case 0 -> { /* volver */ }
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    private void listarOrdenado(List<Plan> lista) {
        if (lista == null) lista = new ArrayList<>();
        if (lista.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        //orden fijo por lo obtenido de dao
        lista.sort(Comparator.comparingInt(Plan::getId));
        imprimirTabla(lista);
    }

    private void buscarPorId() {
        System.out.println("\n--- Buscar por ID ---");
        int id = leerEntero("ID: ");
        Plan p = planDAO.buscarPorId(id);
        if (p == null) {
            System.out.println("No encontrado.");
            return;
        }
        System.out.println("\n" + detallePlanBloque(p));
    }


    //Utilidades: Lecturas
    private String leerNoVacio(String prompt) {
        String dato;
        do {
            System.out.print(prompt);
            dato = in.nextLine().trim(); //quitamos espacios
            if (dato.isBlank()) System.out.println("El valor no puede ser vacío.");
        } while (dato.isBlank());
        return dato;
    }

    private BigDecimal leerBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dato = in.nextLine().trim();
            try{
                return new BigDecimal(dato);
            }catch(Exception ignored){
                System.out.println("Ingrese un valor decimal válido Ej:1459.99");
            }
        }
    }

    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dato = in.nextLine().trim();
            try{
                return Integer.parseInt(dato);
            }catch(Exception ignored){
                System.out.println("Ingrese un número entero");
            }
        }
    }
    private String leerOpcional(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }
    private boolean leerBooleanSiNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dato = in.nextLine().trim().toLowerCase(Locale.ROOT); //Lo de locale es para que sea independiente al idioma
            if (dato.equals("n")) return false;
            if (dato.equals("s")) return true;
            System.out.println("Responda 's' o 'n'.");
        }
    }

    //Utilidades: mostrar
    private String detallePlanLinea(Plan p) {
        return String.format("(%d) %s | $%s | %d (uni %d) | %s | %s",
                p.getId(),
                nvl(p.getNombre()),
                nvl(p.getValor()),
                p.getDuracionTotal(),
                p.getDuracionUnidadId(),
                (p.isEstado() ? "Activo" : "Inactivo"),
                nvl(p.getUrlImagen()));
    }
    private String nvl(Object o) { return o == null ? "" : String.valueOf(o); }


    private void imprimirTabla(List<Plan> lista) { //formateamos el texto en tablas
        String header = String.format("%-4s | %-24s | %-12s | %-8s | %-6s | %-8s | %-40s",
                "ID", "Nombre", "Valor", "DurTot", "UniID", "Estado", "URL Imagen");
        System.out.println("\n" + header);
        System.out.println("-".repeat(header.length()));
        for (Plan p : lista) {
            System.out.println(String.format(
                    "%-4d | %-24s | %-12s | %-8d | %-6d | %-8s | %-40s",
                    p.getId(),
                    nvl(p.getNombre()),
                    nvl(p.getValor()),
                    p.getDuracionTotal(),
                    p.getDuracionUnidadId(),
                    (p.isEstado() ? "Activo" : "Inactivo"),
                    nvl(p.getUrlImagen())
            ));
        }
    }

    private String detallePlanBloque(Plan p) {
        return "Plan {\n" +
                "  id=" + p.getId() + ",\n" +
                "  nombre='" + nvl(p.getNombre()) + "',\n" +
                "  valor=" + nvl(p.getValor()) + ",\n" +
                "  duracionTotal=" + p.getDuracionTotal() + ",\n" +
                "  duracionUnidadId=" + p.getDuracionUnidadId() + ",\n" +
                "  urlImagen='" + nvl(p.getUrlImagen()) + "',\n" +
                "  estado=" + (p.isEstado() ? "Activo" : "Inactivo") + "\n" +
                "}";
    }

    //Utilidades: parsear valores
    private BigDecimal parseBigDecimal(String raw, BigDecimal def) {
        try {
            return new BigDecimal(raw.trim());
        } catch (Exception ignored) {
            return def;
        }
    }

    private int parseEntero(String raw, int def) {
        try {
            return Integer.parseInt(raw.trim());
        } catch (Exception ignored) {
            return def;
        }
    }

    private boolean parseSiNo(String raw, boolean def) {
        String s = raw.trim().toLowerCase(Locale.ROOT);
        if (s.equals("s")) return true;
        if (s.equals("n")) return false;
        return def;
    }
}
