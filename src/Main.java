import dao.EjercicioDAO;
import db.databaseConection;
import utils.dbLogger;
import model.Ejercicio;
import model.GrupoMuscular;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EjercicioDAO dao = new EjercicioDAO();

        // Llamar a la función listarEjercicios
        List<Ejercicio> ejercicios = dao.listarEjercicios();

        // Recorrer la lista y mostrar los datos
        for (Ejercicio e : ejercicios) {
            System.out.println("ID: " + e.getId());
            System.out.println("Nombre: " + e.getNombre());
            // Si quieres mostrar también el nombre del grupo muscular, lo tendrías que traer de otra forma o imprimirlo dentro de listarEjercicios
            System.out.println("-----------------------------");
        }
    }
}