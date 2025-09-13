
import dao.PlanDAO;

import dao.EjercicioDAO;
import db.databaseConection;
import model.Plan;
import utils.dbLogger;

import java.math.BigDecimal;

import model.Ejercicio;
import model.GrupoMuscular;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import model.Movimiento;
import dao.MovimientoDAO;
import flow.MovimientoFlujo;
import flow.MenuFlujo;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        MenuFlujo menu = new MenuFlujo();
        int opcion;
        Scanner sc = new Scanner(System.in);
        //ClienteFlujo cl = new ClienteFlujo();


        menu.menuPrincipal();
        do {
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    //cl.menuCliente();

                    break;
                case 2:
                    //flujo de ;
                    break;
                case 3:
            }
        } while (opcion != 9);
    }
}