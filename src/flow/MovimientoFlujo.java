package flow;
import dao.MovimientoDAO;
import model.Movimiento;
import model.MovimientoDetalle;
import dao.MovimientoDetalleDAO;

import java.util.List;

public class MovimientoFlujo  {
    MovimientoDAO dao = new MovimientoDAO();
    MovimientoDetalleDAO daoDetalle = new MovimientoDetalleDAO();
    //listar tod0
    public void listarTodo(){
        try{
        List<Movimiento> lista = dao.listarTodos();
        for(Movimiento m : lista){
            System.out.println(m.toString());
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void listarTodoDetalle(){
        try{
            List<MovimientoDetalle> lista = daoDetalle.listarDetallePorStaff(1);
            for(MovimientoDetalle m : lista){
                System.out.println(m.toString());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
