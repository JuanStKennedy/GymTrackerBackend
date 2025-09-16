package flow;

import dao.EjercicioDAO;
import model.Ejercicio;
import model.enums.Dificultad;
import java.util.List;
import java.util.Scanner;

public class EjercicioFlujo {
    private final Scanner scanner;
    public EjercicioFlujo(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("--- Ejercicio Flujo ---");
            System.out.println("1. Crear Ejercicio");
            System.out.println("2. Eliminar Ejercicio");
            System.out.println("3. Editar Ejercicio");
            System.out.println("4. Listar Ejercicios");
            System.out.println("5. Listar Ejercicios por Grupo Muscular");
            System.out.println("6. Volver");
            System.out.print("Opcion: ");
            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1 -> crearEjercicio();
                case 2 -> eliminarEjercicio();
                case 3 -> editarEjercicio();
                case 4 -> listarEjercicios();
                case 5 -> listarEjerciciosPorGrupoMuscular();
                case 6 -> seguir = false;
                default -> System.out.println("Opcion invalida");
            }
        }
    }
    private void crearEjercicio() {
        System.out.println("Ingrese el nombre del ejercicio:");
        String nombre = scanner.nextLine();

        EjercicioDAO ejercicioDAO = new EjercicioDAO();
        ejercicioDAO.listarGruposMusculares();

        System.out.print("Ingrese el ID del grupo muscular: ");
        int grupoMuscularId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese la dificultad del ejercicio (PRINCIPIANTE, INTERMEDIO, AVANZADO):");
        String dificultadStr = scanner.nextLine();
        Dificultad dificultad = Dificultad.valueOf(dificultadStr.toUpperCase());

        Ejercicio nuevoEjercicio = new Ejercicio(nombre, grupoMuscularId, dificultad);
        ejercicioDAO.agregarEjercicio(nuevoEjercicio);

        System.out.println("Ejercicio creado correctamente.");
    }

    private void eliminarEjercicio() {
        listarEjercicios();
        System.out.println("Ingrese el ID del ejercicio:");
        int id = scanner.nextInt();

        Ejercicio ejercicioEliminar = new Ejercicio(id);
        EjercicioDAO ejercicioDAO = new EjercicioDAO();
        ejercicioDAO.eliminarEjercicio(ejercicioEliminar);

        System.out.println("Ejercicio eliminado correctamente.");

    }

    private void editarEjercicio() {
        listarEjercicios();
        System.out.println("Ingrese el id del ejercicio:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese el nuevo nombre del ejercicio:");
        String nombre = scanner.nextLine();

        EjercicioDAO ejercicioDAO = new EjercicioDAO();
        ejercicioDAO.listarGruposMusculares();

        System.out.print("Ingrese el ID del grupo muscular: ");
        int grupoMuscularId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese la dificultad del ejercicio (PRINCIPIANTE, INTERMEDIO, AVANZADO):");
        String dificultadStr = scanner.nextLine();
        Dificultad dificultad = Dificultad.valueOf(dificultadStr.toUpperCase());

        Ejercicio nuevoEjercicio = new Ejercicio(id, nombre, grupoMuscularId, dificultad);
        EjercicioDAO ejercicioDAO2 = new EjercicioDAO();
        ejercicioDAO2.modificarEjercicio(nuevoEjercicio);
        System.out.println("Ejercicio editado correctamente.");

    }

    private void listarEjercicios() {
        System.out.println("Listado de ejercicios:");
        EjercicioDAO ejercicioDAO = new EjercicioDAO();
        List<Ejercicio> ejercicios = ejercicioDAO.listarEjercicios();
        for (Ejercicio ejercicio : ejercicios) {
            System.out.println(ejercicio.toString());
        }
    }

    private void listarEjerciciosPorGrupoMuscular(){
        EjercicioDAO ejercicioDAO1 = new EjercicioDAO();
        ejercicioDAO1.listarGruposMusculares();
        System.out.println("Ingrese el nombre del grupo muscular:");
        String nombre = scanner.nextLine();

        EjercicioDAO ejercicioDAO2 = new EjercicioDAO();
        List<Ejercicio> ejercicios = ejercicioDAO2.listarEjerciciosPorGrupoMuscular(nombre);
        if(ejercicios.isEmpty()){
            System.out.println("No existe ejercicio con ese grupo muscular: "+nombre);
        }else{
            System.out.println("Listado de ejercicios:");
            for (Ejercicio ejercicio : ejercicios) {
                System.out.println(ejercicio.toString());
            }
        }
    }

}
