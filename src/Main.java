import db.databaseConection;
import utils.dbLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String sql = "INSERT INTO tipo_cliente (nombre) VALUE (?)";
        try{
            Connection conexion = databaseConection.getInstancia().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, "miembro");
            sentencia.execute();
            System.out.println("Tipo agregado correctamente  .");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}