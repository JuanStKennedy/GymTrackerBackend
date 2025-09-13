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
            sentencia.setInt(1,movimiento.getIdStaff());
            sentencia.setTimestamp(2, Timestamp.valueOf(movimiento.getFechaHora()));
            sentencia.setBigDecimal(3, movimiento.getImporte());
            sentencia.setByte(4, movimiento.getMedioPagoID());
            sentencia.setByte(5, movimiento.getTipoClienteID());
            sentencia.setByte(6, movimiento.getOrigenId());
            sentencia.setInt(7, movimiento.getIdMembresia());
            sentencia.setString(8, movimiento.getIdCliente());
            sentencia.execute();
        }catch(SQLException ex){
            System.out.print(ex.getMessage());
        }
    }

    //Sumar importes de cierto período de tiempo
    public BigDecimal sumarImportes(LocalDateTime desde, LocalDateTime hasta) throws SQLException{
        String sentencia = "SELECT COALESCE(SUM(importe), 0) AS total FROM movimiento WHERE" +
                "fecha_hora >= ? AND fecha_hora < ?";
        try(Connection conexion = databaseConection.getInstancia().getConnection();
        PreparedStatement ps = conexion.prepareStatement(sentencia);){
            ps.setTimestamp(1, Timestamp.valueOf(hasta));
            ps.setTimestamp(2, Timestamp.valueOf(desde));
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getBigDecimal("total");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    // Obtener movimientos por Staff
    public List<Movimiento> listarPorStaff(Integer idStaff)throws SQLException{
        List<Movimiento> lista = new ArrayList<>();
        String sql ="SELECT * FROM movimiento WHERE id_staff = ?  ORDER BY fecha_hora DESC";

        try(Connection conexion = databaseConection.getInstancia().getConnection();
        PreparedStatement ps = conexion.prepareStatement(sql);){
            ps.setInt(1, idStaff);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    lista.add(mapMovimiento(rs));
                }
            }
        }
        return lista;
    }

    // Obtener todos los movmimentos
    public List<Movimiento> listarTodos() throws SQLException {
        List<Movimiento> lista = new ArrayList<>();
        String sentencia = "SELECT * FROM movimiento ORDER BY id_mov DESC";
        try (Connection conexion = databaseConection.getInstancia().getConnection();
        PreparedStatement pw = conexion.prepareStatement(sentencia);
        ResultSet rs = pw.executeQuery()) {
            while (rs.next()) {
                lista.add(mapMovimiento(rs));
            }
        }
        return lista;
    }

    //Eliminar
    public void eliminarMovimiento (Long idMov) throws SQLException{
        String sentencia = "DELETE  FROM movimiento WHERE id_mov = ?";
        try (Connection conexion = databaseConection.getInstancia().getConnection();
        PreparedStatement pw = conexion.prepareStatement(sentencia);){
            pw.setLong (1, idMov);
            pw.executeUpdate();
        }catch(SQLException ex){
            System.out.print(ex.getMessage());
        }
    }


    //Transformamos de resultset a objeto movimiento
    private Movimiento mapMovimiento (ResultSet rs) throws SQLException {
        Movimiento mov = new Movimiento();
        mov.setIdMov(rs.getLong("id_mov"));
        mov.setIdStaff(rs.getInt("id_staff"));
        mov.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        mov.setImporte(rs.getBigDecimal("importe"));
        mov.setMedioPagoID(rs.getByte("medio_pago_id"));
        mov.setTipoClienteID(rs.getByte("tipo_cliente_id"));
        mov.setOrigenId(rs.getByte("origen_id"));
        mov.setIdMembresia(rs.getInt("id_membresia"));
        mov.setIdCliente(rs.getString("id_cliente"));
        return mov;
    }

}
