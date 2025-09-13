package dao;

import db.databaseConection;
import model.RutinaCliente;
import utils.dbLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RutinaClienteDAO {

    private dbLogger logger = new dbLogger();

    public void agregarRutinaCliente(RutinaCliente rc) {
        String sql = "INSERT INTO rutina_cliente (id_cliente, id_rutina, fecha_asignacion, estado) VALUES (?, ?, ?, ?)";
        try (Connection con = databaseConection.getInstancia().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, rc.getIdCliente());
            ps.setInt(2, rc.getIdRutina());
            ps.setDate(3, rc.getFechaAsignacion());
            ps.setString(4, rc.getEstado());
            ps.executeUpdate();

            logger.insertarLog(dbLogger.Accion.INSERT, "Rutina Cliente creada para cliente= " + rc.getIdCliente());
            System.out.println("Rutina Cliente insertada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al insertar rutina cliente: " + e.getMessage());
        }
    }

    public void editarRutinaCliente(RutinaCliente rc) {
        String sql = "UPDATE rutina_cliente SET id_cliente = ?, id_rutina = ?, fecha_asignacion = ?, estado = ? WHERE id = ?";
        try (Connection con = databaseConection.getInstancia().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, rc.getIdCliente());
            ps.setInt(2, rc.getIdRutina());
            ps.setDate(3, rc.getFechaAsignacion());
            ps.setString(4, rc.getEstado());
            ps.setInt(5, rc.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                logger.insertarLog(dbLogger.Accion.UPDATE, "Rutina cliente de " + rc.getIdCliente() + " editada correctamente.");
                System.out.println("Rutina cliente modificada correctamente.");
            } else {
                System.out.println("No se encontro RutinaCliente con ID: " + rc.getId());
            }
        } catch (Exception e) {
            System.out.println("Error al modificar RutinaCliente: " + e.getMessage());
        }
    }

    public void eliminarRutinaCliente(int id) {
        String sql = "DELETE FROM rutina_cliente WHERE id = ?";
        try (Connection con = databaseConection.getInstancia().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();

            logger.insertarLog(dbLogger.Accion.DELETE, "RutinaCliente eliminada ID=" + id);
            System.out.println("RutinaCliente eliminada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar Rutina Cliente: " + e.getMessage());
        }
    }

    public RutinaCliente obtenerRutinaPorId(int id) {
        String sql = "SELECT * FROM rutina_cliente WHERE id = ?";
        try (Connection con = databaseConection.getInstancia().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            ps.setInt(1, id);
            if (rs.next()) {
                RutinaCliente rc = new RutinaCliente(rs.getInt("id"),
                        rs.getString("id_cliente"),
                        rs.getInt("id_rutina"),
                        rs.getDate("fecha_asignacion"),
                        rs.getString("estado")
                );
                return rc;
            }
        } catch (Exception e) {
            System.out.println("Error al obtener rutina_cliente: " + e.getMessage());
        }
        return null;
    }
}
