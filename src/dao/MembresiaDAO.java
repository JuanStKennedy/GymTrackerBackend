package dao;

import db.databaseConection;
import model.Membresia;
import model.Movimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static flow.UtilidadesFlujo.*;
import static flow.UtilidadesFlujo.nvl;

public class MembresiaDAO {
    public void agregarMembresia(Membresia c) {
        String sql = "INSERT INTO membresia (id_plan, id_cliente, fecha_inicio, fecha_fin, estado_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, c.getIdPlan());
            sentencia.setString(2, c.getIdCliente());
            sentencia.setDate(3, c.getFechaInicio());
            sentencia.setDate(4, c.getFechaFin());
            sentencia.setInt(5, c.getEstadoId());
            sentencia.execute();
            System.out.println("Membresía cargada correctamente.");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void eliminarMembresia(int id) {
        String sql = "DELETE FROM membresia WHERE id = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            sentencia.execute();
            System.out.println("Membresía eliminada correctamente.");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    public void modificarMembresia(Membresia c) {
        String sql = "UPDATE membresia SET id_plan = ?, id_cliente = ?, fecha_inicio = ?, " +
                "fecha_fin = ?, estado_id = ? WHERE id = ?";
        try {
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, c.getIdPlan());
            sentencia.setString(2, c.getIdCliente());
            sentencia.setDate(3, c.getFechaInicio());
            sentencia.setDate(4, c.getFechaFin());
            sentencia.setInt(5, c.getEstadoId());
            sentencia.setInt(6, c.getId());

            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                System.out.println("Membresía modificada correctamente.");
            } else {
                System.out.println("No se encontró membresía con ID: " + c.getId());
            }

        } catch (Exception e) {
            System.out.println("Error al modificar membresía: " + e.getMessage());
        }
    }

    public void listarMembresia(){
        String sql = "SELECT * FROM membresia";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            List<Membresia> listaMembresia = new ArrayList<>();
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                listaMembresia.add(mapMembresia(resultado));
            }
            String[] headers = {"Cédula", "Nº Plan", "Fecha Inicio", "Fecha Fin", "Estado"};
            int[] anchos = {9, 9, 13, 11, 10};
            printHeader(headers, anchos);
            for (Membresia m : listaMembresia) {
                System.out.println(formatRow(new Object[]{
                        nvl(m.getIdCliente()),
                        nvl(m.getIdPlan()),
                        nvl(m.getFechaInicio()),
                        nvl(m.getFechaFin()),
                        nvl((m.getEstadoId() == 1 ? "Activa" : "Inactiva"))
                }, anchos));
            }
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public List<Membresia> obtenerMembresias(){
        List<Membresia> lista = new ArrayList<>();
        String sql = "SELECT * FROM membresia";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                lista.add(mapMembresia(resultado));
            }
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return lista;
    }

    public Membresia obtenerMembresiaPorCedula(String cedula){
        String sql = "SELECT * FROM membresia WHERE id_cliente = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, cedula);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                return mapMembresia(resultado);
            }
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return null;
    }

    private Membresia mapMembresia(ResultSet rs) throws SQLException {
        Membresia c = new Membresia();
        c.setId(rs.getInt("id"));
        c.setIdPlan(rs.getInt("id_plan"));
        c.setIdCliente(rs.getString("id_cliente"));
        c.setFechaInicio(rs.getDate("fecha_inicio"));
        c.setFechaFin(rs.getDate("fecha_fin"));
        c.setEstadoId(rs.getInt("estado_id"));
        return c;
    }

}
