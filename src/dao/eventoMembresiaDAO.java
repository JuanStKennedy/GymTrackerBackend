package dao;

import db.databaseConection;
import model.eventoMembresia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class eventoMembresiaDAO {
    public void agregarEventoMembresia(eventoMembresia c) {
        String sql = "INSERT INTO evento_membresia (id_staff, id_membresia, tipo_evento_id, fecha_evento, observaciones) " +
                "VALUES (?, ?, ?, ?, ?)";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, c.getIdStaff());
            sentencia.setInt(2, c.getIdMembresia());
            sentencia.setInt(3, c.getTipoEventoId());
            sentencia.setTimestamp(4, c.getFechaEvento());
            sentencia.setString(5, c.getObservaciones());
            sentencia.execute();
            System.out.println("Evento de Membresía cargado correctamente.");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void eliminarEventoMembresia(int id) {
        String sql = "DELETE FROM evento_membresia WHERE id = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            sentencia.execute();
            System.out.println("Evento de Membresía eliminado correctamente.");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    public void modificarEventoMembresia(eventoMembresia c) {
        String sql = "UPDATE evento_membresia SET id_staff = ?, id_membresia = ?, tipo_evento_id = ?, " +
                "fecha_evento = ?, observaciones = ? WHERE id = ?";
        try {
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setInt(1, c.getIdStaff());
            sentencia.setInt(2, c.getIdMembresia());
            sentencia.setInt(3, c.getTipoEventoId());
            sentencia.setTimestamp(4, c.getFechaEvento());
            sentencia.setString(5, c.getObservaciones());
            sentencia.setInt(6, c.getId());

            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                System.out.println("Evento de Membresía modificado correctamente.");
            } else {
                System.out.println("No se encontró evento de membresía con ID: " + c.getId());
            }

        } catch (Exception e) {
            System.out.println("Error al modificar evento de membresía: " + e.getMessage());
        }
    }
    public void listarEventoMembresia(){
        String sql = "SELECT * FROM evento_membresia";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                int id_staff = resultado.getInt("id_staff");
                int id_membresia = resultado.getInt("id_membresia");
                int tipo_evento_id = resultado.getInt("tipo_evento_id");
                java.sql.Timestamp fecha_evento = resultado.getTimestamp("fecha_evento");
                String observaciones = resultado.getString("observaciones");

                System.out.println("ID: " + id);
                System.out.println("ID Staff: " + id_staff);
                System.out.println("ID Membresía: " + id_membresia);
                System.out.println("ID Tipo Evento: " + tipo_evento_id);
                System.out.println("Fecha Evento: " + fecha_evento);
                System.out.println("Observaciones: " + observaciones);
                System.out.println("-------------------------------");
            }

            resultado.close();
            sentencia.close();
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }


    private eventoMembresia mapEventoMembresia(ResultSet rs) throws SQLException {
        eventoMembresia c = new eventoMembresia();
        c.setId(rs.getInt("id"));
        c.setIdStaff(rs.getInt("id_staff"));
        c.setIdMembresia(rs.getInt("id_membresia"));
        c.setTipoEventoId(rs.getInt("tipo_evento_id"));
        c.setFechaEvento(rs.getTimestamp("fecha_evento"));
        c.setObservaciones(rs.getString("observaciones"));
        return c;
    }

}
