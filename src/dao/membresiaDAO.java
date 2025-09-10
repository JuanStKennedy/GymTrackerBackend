package dao;

import db.databaseConection;
import model.membresia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class membresiaDAO {
    public void agregarMembresia(membresia c) {
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
    public void modificarMembresia(membresia c) {
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

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                int id_plan = resultado.getInt("id_plan");
                String id_cliente = resultado.getString("id_cliente");
                Date fecha_inicio = resultado.getDate("fecha_inicio");
                Date fecha_fin = resultado.getDate("fecha_fin");
                int estado_id = resultado.getInt("estado_id");

                System.out.println("ID: " + id);
                System.out.println("ID Plan: " + id_plan);
                System.out.println("ID Cliente: " + id_cliente);
                System.out.println("Fecha Inicio: " + fecha_inicio);
                System.out.println("Fecha Fin: " + fecha_fin);
                System.out.println("ID Estado: " + estado_id);
                System.out.println("-------------------------------");
            }

            resultado.close();
            sentencia.close();
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }


    private membresia mapMembresia(ResultSet rs) throws SQLException {
        membresia c = new membresia();
        c.setId(rs.getInt("id"));
        c.setIdPlan(rs.getInt("id_plan"));
        c.setIdCliente(rs.getString("id_cliente"));
        c.setFechaInicio(rs.getDate("fecha_inicio"));
        c.setFechaFin(rs.getDate("fecha_fin"));
        c.setEstadoId(rs.getInt("estado_id"));
        return c;
    }

}
