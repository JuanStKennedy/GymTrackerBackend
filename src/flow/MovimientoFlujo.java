package flow;
import dao.MovimientoDAO;
import model.Movimiento;
import model.MovimientoDetalle;
import dao.MovimientoDetalleDAO;

import java.util.List;
import java.util.Scanner;

public class MovimientoFlujo  {
    private final Scanner scanner;
    public MovimientoFlujo(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("--- Nombre ---");
            System.out.println("1. ");
            System.out.println("2. ");
            System.out.println("3. ");
            System.out.println("4. Volver");
            System.out.print("Opción: ");
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
//    MovimientoDAO dao = new MovimientoDAO();
//    MovimientoDetalleDAO daoDetalle = new MovimientoDetalleDAO();
//    //listar tod0
//    public void listarTodo(){
//        try{
//        List<Movimiento> lista = dao.listarTodos();
//        for(Movimiento m : lista){
//            System.out.println(m.toString());
//        }
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//    public void listarTodoDetalle(){
//        try{
//            List<MovimientoDetalle> lista = daoDetalle.listarDetallePorStaff(1);
//            for(MovimientoDetalle m : lista){
//                System.out.println(m.toString());
//            }
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
}
