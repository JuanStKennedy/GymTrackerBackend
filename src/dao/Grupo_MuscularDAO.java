package dao;

import db.databaseConection;
import model.GrupoMuscular;
import utils.dbLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Grupo_MuscularDAO {
    private dbLogger logger = new dbLogger();
    public void agregarGrupoMuscular(GrupoMuscular gm) {
        String sql = "INSERT INTO grupo_muscular (nombre) VALUES (?)";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, gm.getNombre());

            sentencia.execute();
            System.out.println("Nuevo grupo muscular creado.");
            logger.insertarLog(dbLogger.Accion.INSERT, "Nuevo grupo muscular creado");
        }catch(Exception err){
            System.out.println("Error: "+err.getMessage());
        }
    }

    public void eliminarGrupoMuscular(GrupoMuscular gm) {
        String sql = "DELETE FROM grupo_muscular WHERE id = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, gm.getId());
            sentencia.execute();
            logger.insertarLog(dbLogger.Accion.DELETE, "Grupo muscular eliminado");
            System.out.println("Grupo muscular eliminado correctamente  .");
        }catch(Exception err){
            System.out.println("Error: "+err.getMessage());
        }
    }

    public void modificarGrupoMuscular(GrupoMuscular gm) {
        String sql = "UPDATE grupo_muscular SET nombre = ? WHERE id = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, gm.getNombre());
            sentencia.setInt(2, gm.getId());

            sentencia.executeUpdate();
            logger.insertarLog(dbLogger.Accion.UPDATE, "Grupo muscular modificado");
            System.out.println("Grupo muscular modificado correctamente.");
        }catch(Exception err){
            System.out.println("Error: "+err.getMessage());
        }
    }

    public List<GrupoMuscular> listarGrupoMuscular() {
        List<GrupoMuscular> lista = new ArrayList<>();
        String sql = "SELECT * FROM grupo_muscular";
        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                GrupoMuscular gm = new GrupoMuscular();
                gm.setId(resultado.getInt("id"));
                gm.setNombre(resultado.getString("nombre"));
                lista.add(gm);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }
}
