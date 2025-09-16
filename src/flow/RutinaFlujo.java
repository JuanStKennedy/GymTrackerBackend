package flow;

import java.util.List;
import java.util.Scanner;
import dao.RutinaDAO;
import model.Rutina;
import model.enums.Objetivo;

public class RutinaFlujo {
    private final Scanner scanner;
    public RutinaFlujo(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("--- Rutina Flujo ---");
            System.out.println("1. Crear Rutina");
            System.out.println("2. Eliminar Rutina");
            System.out.println("3. Editar Rutina");
            System.out.println("4. Listar Rutinas");
            System.out.println("5. Listar Rutinas por Objetivo");
            System.out.println("6. Volver");
            System.out.print("Opcion: ");
            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1 -> crearRutina();
                case 2 -> eliminarRutina();
                case 3 -> editarRutina();
                case 4 -> listarRutinas();
                case 5 -> listarRutinasPorObjetivo();
                case 6 -> seguir = false;
                default -> System.out.println("Opcion invalida");
            }
        }
    }
    private void crearRutina() {
        System.out.println("Ingrese el nombre de la rutina:");
        String nombre = scanner.nextLine();

        Objetivo objetivo = null;
        System.out.println("Ingrese el objetivo de la rutina (Hipertrofia, Fuerza, Resistencia):");
        String objetivoStr = scanner.nextLine();
        objetivo = Objetivo.valueOf(objetivoStr.toUpperCase());

        System.out.println("Ingrese la duracion en semanas:");
        int semanas = scanner.nextInt();

        Rutina nuevaRutina = new Rutina(nombre, objetivo, semanas);
        RutinaDAO rutinaDAO = new RutinaDAO();
        rutinaDAO.agregarRutina(nuevaRutina);

        System.out.println("Rutina creada correctamente");
    }

    private void listarRutinas() {
        System.out.println("Listado de rutinas:");
        RutinaDAO rutinaDAO = new RutinaDAO();
        List<Rutina> rutinas = rutinaDAO.listarRutinas();
        for (Rutina rutina : rutinas) {
            System.out.println(rutina.toStringID());
        }
    }

    private void eliminarRutina() {
        listarRutinas();
        System.out.println("Ingrese el ID de la rutina:");
        int id = scanner.nextInt();

        Rutina rutinaEliminar = new Rutina(id);
        RutinaDAO rutinaDAO = new RutinaDAO();
        rutinaDAO.eliminarRutina(rutinaEliminar);

        System.out.println("Rutina eliminada correctamente");

    }

    private void editarRutina() {
        listarRutinas();
        System.out.println("Ingrese el ID de la rutina:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese el nuevo nombre de la rutina:");
        String nombre = scanner.nextLine();

        Objetivo objetivo = null;
        System.out.println("Ingrese el objetivo de la rutina (Hipertrofia, Fuerza, Resistencia):");
        String objetivoStr = scanner.nextLine();
        objetivo = Objetivo.valueOf(objetivoStr.toUpperCase());

        System.out.println("Ingrese la duracion en semanas:");
        int semanas = scanner.nextInt();

        Rutina nuevaRutina = new Rutina(id, nombre, objetivo, semanas);
        RutinaDAO rutinaDAO2 = new RutinaDAO();
        rutinaDAO2.modificarRutina(nuevaRutina);

        System.out.println("Rutina actualizada correctamente");

    }

    private void listarRutinasPorObjetivo() {
        Objetivo objetivo = null;
        System.out.println("Ingrese el objetivo de la rutina (Hipertrofia, Fuerza, Resistencia):");
        String objetivoStr = scanner.nextLine();
        objetivo = Objetivo.valueOf(objetivoStr.toUpperCase());
        RutinaDAO rutinaDAO = new RutinaDAO();

        List<Rutina> rutinas = rutinaDAO.listarRutinasPorObjetivo(objetivo);
        for (Rutina rutina : rutinas) {
            System.out.println(rutina.toString());
        }
    }
}
