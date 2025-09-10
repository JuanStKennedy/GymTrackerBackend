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

    public void CrearStaff(String usuarioLogin,String nombreCompleto,int rol, int estado){
        String consulta = "INSERT INTO staff (usuario_login, nombre_completo, rol, estado) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = db.databaseConection.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, usuarioLogin);
            ps.setString(2, nombreCompleto);
            ps.setInt(3, rol);
            ps.setInt(4, estado);

            ps.executeUpdate();
            logger.insertarLog(dbLogger.Accion.INSERT, "Staff creado: " + usuarioLogin);
            System.out.println("Staff creado correctamente ");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editarStaff(int id, String nombreCompleto, int rol, int estado) {
        String consulta = "UPDATE staff SET nombre_completo = ?, rol = ?, estado = ? WHERE id = ?";
        try {
            PreparedStatement ps = db.databaseConection.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, nombreCompleto);
            ps.setInt(2, rol);
            ps.setInt(3, estado);
            ps.setInt(4, id);

            ps.executeUpdate();
            logger.insertarLog(dbLogger.Accion.UPDATE, "Staff con id = " + id + " editado");
            System.out.println("Staff actualizado correctamente ");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
