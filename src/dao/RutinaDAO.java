package dao;

import db.databaseConection;
import utils.dbLogger;
import model.Rutina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RutinaDAO {
    private dbLogger logger = new dbLogger();
    public void agregarRutina(Rutina r) {
        String sql = "INSERT INTO rutina (nombre, objetivo, duracion_semanas) VALUES (?, ?, ?)";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, r.getNombre());
            sentencia.setString(2, r.getObjetivo());
            sentencia.setInt(3, r.getDuracionSemanas());

            sentencia.execute();
            System.out.println("Nueva rutina creado.");
            logger.insertarLog(dbLogger.Accion.INSERT, "Nueva rutina creada con éxito");
        }catch(Exception err){
            System.out.println("Error: "+err.getMessage());
        }
    }

    public void eliminarRutina(Rutina r) {
        String sql = "DELETE FROM rutina WHERE id = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, r.getId());
            sentencia.execute();
            logger.insertarLog(dbLogger.Accion.DELETE, "Rutina eliminada correctamente");
            System.out.println("Rutina eliminada correctamente.");
        }catch(Exception err){
            System.out.println("Error: "+err.getMessage());
        }
    }

    public void modificarRutina(Rutina r) {
        String sql = "UPDATE rutina SET nombre = ?, objetivo = ?, duracion_semanas = ? WHERE id = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, r.getNombre());
            sentencia.setString(2, r.getObjetivo());
            sentencia.setInt(3, r.getDuracionSemanas());
            sentencia.setInt(4, r.getId());

            sentencia.executeUpdate();
            logger.insertarLog(dbLogger.Accion.UPDATE, "Rutina modificada exitosamente");
            System.out.println("Rutina modificada correctamente.");
        }catch(Exception err){
            System.out.println("Error: "+err.getMessage());
        }
    }

    public List<Rutina> listarRutinas() {
        List<Rutina> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutina";  // tabla correcta
        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Rutina r = new Rutina();
                r.setId(resultado.getInt("id"));
                r.setNombre(resultado.getString("nombre"));
                r.setObjetivo(resultado.getString("objetivo"));
                r.setDuracionSemanas(resultado.getInt("duracion_semanas"));
                lista.add(r);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }
}
