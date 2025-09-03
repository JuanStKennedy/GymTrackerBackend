package dao;

import db.databaseConection;
import utils.dbLogger;
import model.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class usuarioDAO {
    private dbLogger logger = new dbLogger();
    public void agregarUsuario(usuario u) {
        String sql = "INSERT INTO usuarios (nombre, email) VALUES (?,?)";
        try{
           Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, u.getNombre());
            sentencia.setString(2, u.getEmail());

            sentencia.execute();
            System.out.println("Usuario agregado correctamente  .");
            logger.insertarLog(dbLogger.Accion.INSERT, "Nuevo usuario creado");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void agregarUsuarioConID(usuario u) {
        String sql = "INSERT INTO usuarios (id,nombre, email) VALUES (?,?,?)";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, u.getID());
            sentencia.setString(2, u.getNombre());
            sentencia.setString(3, u.getEmail());

            sentencia.execute();
            System.out.println("Usuario agregado correctamente  .");
            logger.insertarLog(dbLogger.Accion.INSERT, "Nuevo usuario creado");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            sentencia.execute();
            logger.insertarLog(dbLogger.Accion.DELETE, "Usuario eliminado");
            System.out.println("Usuario eliminado correctamente  .");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void modificarUsuario(int id, String nombre, String email) {
        String sql = "UPDATE usuarios SET nombre = ?, email= ? WHERE id= ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, email);
            sentencia.setInt(3, id);

            sentencia.executeUpdate();
            logger.insertarLog(dbLogger.Accion.UPDATE, "Usuario modificado");
            System.out.println("Usuario modificado correctamente  .");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void listarUsuarios(){
        String sql = "SELECT * FROM usuarios";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String email = resultado.getString("email");

                System.out.println("Id Usuario:" + id);
                System.out.println("Nombre Usuario:" + nombre);
                System.out.println("Email Usuario:" + email);
            }
            resultado.close();
            sentencia.close();
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void buscarPalabra(String palabra){
        String sql = "SELECT * FROM usuarios WHERE email LIKE ?";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1,"%"+palabra+"%");

            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String email = resultado.getString("email");

                System.out.println("Id Usuario:" + id);
                System.out.println("Nombre Usuario:" + nombre);
                System.out.println("Email Usuario:" + email);
            }
            resultado.close();
            sentencia.close();
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
