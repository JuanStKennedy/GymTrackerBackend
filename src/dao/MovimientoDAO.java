package dao;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.sql.*;
import db.databaseConection;
import model.Movimiento;

public class MovimientoDAO {
    //insert
    public void insertarMovimiento(Movimiento movimiento){
        String sql ="INSERT INTO movimiento (id_staff, fecha_hora, importe," +
                " medio_pago_id, tipo_cliente_id, origen_id, id_membresia, id_cliente)"+
                "VALUES (?,?,?,?,?,?,?,?)";
        try(Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);){
            // a continuar sentencia.set..
        }catch(SQLException ex){
            System.out.print(ex.getMessage());
        }
    }
}
