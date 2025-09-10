package dao;

import db.databaseConection;
import model.Dificultad;
import model.GrupoMuscular;
import utils.dbLogger;
import model.Ejercicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EjercicioDAO {
    private dbLogger logger = new dbLogger();
    public void agregarEjercicio(Ejercicio e) {
        String sql = "INSERT INTO ejercicio (nombre, grupo_muscular_id, dificultad) VALUES (?,?, ?)";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, e.getNombre());
            sentencia.setInt(2, e.getGrupoMuscular());
            sentencia.setString(3, e.getDificultad().name());

            sentencia.execute();
            System.out.println("Ejercicio agregado correctamente.");
            logger.insertarLog(dbLogger.Accion.INSERT, "Nuevo ejercicio creado");
        }catch(Exception err){
            System.out.println("Error: "+err.getMessage());
        }
    }

    public void eliminarEjercicio(Ejercicio e) {
        String sql = "DELETE FROM ejercicio WHERE id = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, e.getId());
            sentencia.execute();
            logger.insertarLog(dbLogger.Accion.DELETE, "Ejercicio eliminado exitosamente");
            System.out.println("Ejercicio eliminado correctamente  .");
        }catch(Exception err){
            System.out.println("Error: "+err.getMessage());
        }
    }

    public void modificarEjercicio(Ejercicio e) {
        String sql = "UPDATE ejercicio SET nombre = ?, grupo_muscular_id = ?, dificultad = ? WHERE id = ?";
        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, e.getNombre());
            sentencia.setInt(2, e.getGrupoMuscular());
            sentencia.setString(3, e.getDificultad().name());
            sentencia.setInt(4, e.getId());

            sentencia.executeUpdate();
            logger.insertarLog(dbLogger.Accion.UPDATE, "Ejercicio modificado");
            System.out.println("Ejercicio modificado correctamente.");
        } catch (Exception err) {
            System.out.println("Error: " + err.getMessage());
        }
    }


    public List<Ejercicio> listarEjercicios() {
        List<Ejercicio> lista = new ArrayList<>();

        // Traemos el ID y el nombre del grupo muscular
        String sql = "SELECT e.id, e.nombre, e.grupo_muscular_id, g.nombre AS grupo_muscular, e.dificultad " +
                "FROM ejercicio e " +
                "INNER JOIN grupo_muscular g ON e.grupo_muscular_id = g.id";

        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Ejercicio e = new Ejercicio();
                e.setId(resultado.getInt("id"));
                e.setNombre(resultado.getString("nombre"));

//                // Guardamos el ID del grupo muscular (int)
//                e.setGrupoMuscular(resultado.getInt("grupo_muscular_id"));

                // Obtenemos el nombre solo para mostrarlo
                String nombreGrupo = resultado.getString("grupo_muscular");
                System.out.println("Grupo Muscular: " + nombreGrupo);

                // Dificultad
                String dif = resultado.getString("dificultad");
                e.setDificultad(Dificultad.valueOf(dif.toUpperCase()));

                lista.add(e);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return lista;
    }



}
