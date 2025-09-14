package flow;

import java.util.Scanner;

public class StaffFlujo {
    private final Scanner scanner;
    public StaffFlujo(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("--- Staff Flujo ---");
            System.out.println("1. ");
            System.out.println("2. ");
            System.out.println("3. ");
            System.out.println("4. Volver");
            System.out.print("Opcion: ");
            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1 -> mostrarMenu();
                case 2 -> mostrarMenu();
                case 3 -> mostrarMenu();
                case 4 -> seguir = false;
                default -> System.out.println("Opcion invalida");
            }
        }
    }
}
