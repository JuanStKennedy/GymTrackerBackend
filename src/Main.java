
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

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        MovimientoFlujo f = new MovimientoFlujo();
        try{
            f.listarTodoDetalle();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}