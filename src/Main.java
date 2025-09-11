
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
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String nombre = "Clínico";
        BigDecimal valor= BigDecimal.valueOf(1500);
        short duracionTotal= 5;
        byte duracionUnidadId= 1;
        String urlImagen= "hola.com";
        boolean estado= true;
        Plan p = new Plan(nombre, valor, duracionTotal, duracionUnidadId, urlImagen, estado);
        PlanDAO plandao = new PlanDAO();
        System.out.println("....................");
        plandao.listarActivos();
        System.out.println("....................");
        plandao.listarInactivos();
        System.out.println("....................");
        String nombre2 = "Deportivo";
        int id = 2;
        Plan p2 = new Plan(id, nombre2, valor, duracionTotal, duracionUnidadId, urlImagen, estado);
        plandao.modificarPlan(p2);
        plandao.listarTodos();
        plandao.eliminarPlan(2);
        plandao.listarTodos();

    }
}