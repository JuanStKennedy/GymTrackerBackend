package flow;

import model.Cliente;

import java.util.Scanner;

public class MenuFlujo {
    private Scanner scanner = new Scanner(System.in);
    public MenuFlujo(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu(){
        boolean continuar = true;
        while(continuar) {
            try {
                System.out.println("----- GYM TRACKER -----");
                System.out.println("1.Cliente Flujo");
                System.out.println("2.Staff Flujo");
                System.out.println("3.Movimiento Flujo");
                System.out.println("4.Plan Flujo");
                System.out.println("5.Membresia Flujo");
                System.out.println("6.Rutina Flujo");
                System.out.println("7.Ejercicio Flujo");
                System.out.println("8.Salir");
                System.out.println("Opcion:");
                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1 -> new ClienteFlujo().run();
                    case 2 -> irMenuStaff();
                    case 3 -> new MovimientoFlujo().run();
                    case 4 -> new PlanFlujo().run();
                    case 5 -> irMenuMembresia();
                    case 6 -> irMenuRutina();
                    case 7 -> irMenuEjercicio();
                    case 8 -> continuar = false;
                    default -> System.out.println("Opción invalida");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void irMenuCliente() {

    }
    private void irMenuStaff() {
        StaffFlujo staffFlujo = new StaffFlujo(scanner);
        staffFlujo.mostrarMenu();
    }
    private void irMenuMembresia() {
        MembresiaFlujo membresiaFlujo = new MembresiaFlujo(scanner);
        membresiaFlujo.mostrarMenu();
    }
    private void irMenuRutina() {
        RutinaFlujo rutinaFlujo = new RutinaFlujo(scanner);
        rutinaFlujo.mostrarMenu();
    }
    private void irMenuEjercicio() {
        EjercicioFlujo ejercicioFlujo = new EjercicioFlujo(scanner);
        ejercicioFlujo.mostrarMenu();
    }
}
