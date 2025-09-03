package dao;
import java.sql.Date;

import db.databaseConection;
import model.cliente;
import utils.dbLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class clienteDAO {
    public void agregarCliente(cliente c) {
        String sql = "INSERT INTO cliente (ci, email, nombre, apellido, ciudad, direccion, tel, pais, fecha_ingreso) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            //Preparamo lo dato

            sentencia.setString(1, c.getCi());
            sentencia.setString(2, c.getEmail());
            sentencia.setString(3, c.getNombre());
            sentencia.setString(4, c.getApellido());
            sentencia.setString(5, c.getCiudad());
            sentencia.setString(6, c.getDireccion());
            sentencia.setString(7, c.getTel());
            sentencia.setString(8, c.getPais());
            sentencia.setDate(9, c.getFechaIngreso());

            sentencia.execute();
            System.out.println("Cliente cargado correctamente  .");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void eliminarCliente(String ci) {
        String sql = "DELETE FROM usuarios WHERE ci = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, ci);
            sentencia.execute();
            System.out.println("Cliente eliminado correctamente  .");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    public void modificarCliente(cliente c) {
        String sql = "UPDATE cliente SET email = ?, nombre = ?, apellido = ?, " +
                "ciudad = ?, direccion = ?, tel = ?, pais = ?, fecha_ingreso = ? " +
                "WHERE ci = ?";
        try {
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            sentencia.setString(1, c.getEmail());
            sentencia.setString(2, c.getNombre());
            sentencia.setString(3, c.getApellido());
            sentencia.setString(4, c.getCiudad());
            sentencia.setString(5, c.getDireccion());
            sentencia.setString(6, c.getTel());
            sentencia.setString(7, c.getPais());
            sentencia.setDate(8, c.getFechaIngreso()); // java.sql.Date
            sentencia.setString(9, c.getCi()); // clave primaria en el WHERE

            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                System.out.println("Cliente modificado correctamente.");
            } else {
                System.out.println("No se encontró cliente con CI: " + c.getCi());
            }

        } catch (Exception e) {
            System.out.println("Error al modificar cliente: " + e.getMessage());
        }
    }
    public void listarClientes(){
        String sql = "SELECT * FROM cliente";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                String ci = resultado.getString("ci");
                String email = resultado.getString("email");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                String ciudad = resultado.getString("ciudad");
                String direccion = resultado.getString("direccion");
                String tel = resultado.getString("tel");
                String pais = resultado.getString("pais");
                Date fechaIngreso = resultado.getDate("fecha_ingreso"); // java.sql.Date

                System.out.println("CI: " + ci);
                System.out.println("Nombre: " + nombre + " " + apellido);
                System.out.println("Email: " + email);
                System.out.println("Ciudad: " + ciudad);
                System.out.println("Dirección: " + direccion);
                System.out.println("Teléfono: " + tel);
                System.out.println("País: " + pais);
                System.out.println("Fecha de ingreso: " + fechaIngreso);
                System.out.println("-------------------------------");
            }

            resultado.close();
            sentencia.close();
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

}
