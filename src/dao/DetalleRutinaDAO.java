package dao;

import db.databaseConection;
import model.DetalleRutina;
import utils.dbLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetalleRutinaDAO {
    private final dbLogger logger = new dbLogger();

    public void agregarDetalle(DetalleRutina d) {
        String sql = "INSERT INTO detalle_rutina (id_ejercicio, id_rutina, series, repeticiones) VALUES (?, ?, ?, ?)";
        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, d.getId_ejercicio());
            sentencia.setInt(2, d.getId_rutina());
            sentencia.setInt(3, d.getSeries());
            sentencia.setInt(4, d.getRepeticiones());

            sentencia.execute();
            logger.insertarLog(dbLogger.Accion.INSERT, "Detalle de rutina agregado (Rutina " + d.getId_rutina() + ")");
            System.out.println("Detalle de rutina agregado correctamente.");
        } catch (Exception err) {
            System.out.println("Error al agregar detalle de rutina: " + err.getMessage());
        }
    }

    public void eliminarDetalle(int id) {
        String sql = "DELETE FROM detalle_rutina WHERE id = ?";
        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            sentencia.execute();
            logger.insertarLog(dbLogger.Accion.DELETE, "Detalle de rutina eliminado con ID: " + id);
            System.out.println("Detalle de rutina eliminado correctamente.");
        } catch (Exception err) {
            System.out.println("Error al eliminar detalle de rutina: " + err.getMessage());
        }
    }

    public void modificarDetalle(DetalleRutina d) {
        String sql = "UPDATE detalle_rutina SET id_ejercicio = ?, id_rutina = ?, series = ?, repeticiones = ? WHERE id = ?";
        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, d.getId_ejercicio());
            sentencia.setInt(2, d.getId_rutina());
            sentencia.setInt(3, d.getSeries());
            sentencia.setInt(4, d.getRepeticiones());
            sentencia.setInt(5, d.getId());

            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                logger.insertarLog(dbLogger.Accion.UPDATE, "Detalle de rutina modificado con ID: " + d.getId());
                System.out.println("Detalle de rutina modificado correctamente.");
            } else {
                System.out.println("No se encontró detalle con ID: " + d.getId());
            }
        } catch (Exception err) {
            System.out.println("Error al modificar detalle de rutina: " + err.getMessage());
        }
    }

    public List<DetalleRutina> listarDetalles() {
        List<DetalleRutina> lista = new ArrayList<>();
        String sql = "SELECT id, id_ejercicio, id_rutina, series, repeticiones FROM detalle_rutina";
        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                DetalleRutina d = new DetalleRutina();
                d.setId(resultado.getInt("id"));
                d.setId_ejercicio(resultado.getInt("id_ejercicio"));
                d.setId_rutina(resultado.getInt("id_rutina"));
                d.setSeries(resultado.getInt("series"));
                d.setRepeticiones(resultado.getInt("repeticiones"));

                lista.add(d);
            }
        } catch (Exception e) {
            System.out.println("Error al listar detalles de rutina: " + e.getMessage());
        }
        return lista;
    }

    public List<DetalleRutina> listarDetallesConNombres() {
        List<DetalleRutina> lista = new ArrayList<>(); //nos retornará una lista de objetos detalleRutina
        String sql = "SELECT dr.id, dr.series, dr.repeticiones, " +
                "e.nombre AS ejercicio_nombre, r.nombre AS rutina_nombre " +
                "FROM detalle_rutina dr " +
                "INNER JOIN ejercicio e ON dr.id_ejercicio = e.id " +
                "INNER JOIN rutina r ON dr.id_rutina = r.id";

        try (Connection conexion = databaseConection.getInstancia().getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                DetalleRutina d = new DetalleRutina();
                d.setId(resultado.getInt("id"));
                d.setSeries(resultado.getInt("series"));
                d.setRepeticiones(resultado.getInt("repeticiones"));

                // Mostrar los nombres sin guardarlos en el objeto
                String ejercicioNombre = resultado.getString("ejercicio_nombre");
                String rutinaNombre = resultado.getString("rutina_nombre");
                System.out.println("Rutina: " + rutinaNombre + " | Ejercicio: " + ejercicioNombre);

                lista.add(d);
            }
        } catch (Exception e) {
            System.out.println("Error al listar detalles de rutina con nombres: " + e.getMessage());
        }
        return lista;
    }


}

