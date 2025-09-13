package dao;

import db.databaseConection;
import model.Staff;
import utils.dbLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    private dbLogger logger = new dbLogger();

    public void crearStaff(Staff s) {
        String sql = "INSERT INTO staff (usuario_login, nombre_completo, rol, estado) VALUES (?,?,?,?)";
        try (
                Connection con = databaseConection.getInstancia().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getUsuarioLogin());
            ps.setString(2, s.getNombreCompleto());
            ps.setInt(3, s.getRol());
            ps.setInt(4, s.getEstado());

            ps.executeUpdate();

            logger.insertarLog(dbLogger.Accion.INSERT, "Staff creado: " + s.getUsuarioLogin());
            System.out.println("Staff creado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al crear staff: " + e.getMessage());
        }
    }

    public List<Staff> listarStaff() {
        List<Staff> staffList = new ArrayList<>();
        String consulta = "SELECT * FROM staff";
        try {
            Statement st = db.databaseConection.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while (rs.next()) {
                Staff staff = new Staff(rs.getInt("id"),
                        rs.getString("usuario_login"),
                        rs.getString("nombre_completo"),
                        rs.getInt("rol"),
                        rs.getInt("estado")
                );
                staffList.add(staff);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return staffList;
    }

    public void eliminarStaff(int id) {
        String consulta = "DELETE FROM staff WHERE id = ?";
        try {
            PreparedStatement ps = db.databaseConection.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, id);

            ps.executeUpdate();
            logger.insertarLog(dbLogger.Accion.DELETE, "Staff con id = " + id + " eliminado");
            System.out.println("Staff eliminado correctamente ");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
