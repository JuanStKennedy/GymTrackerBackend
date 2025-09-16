package flow;
import dao.MovimientoDAO;
import model.Movimiento;
import dto.MovimientoView;
import dao.MovimientoDetalleDAO;
import static flow.UtilidadesFlujo.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MovimientoFlujo  {
    MovimientoDAO dao = new MovimientoDAO();
    MovimientoDetalleDAO daoDetalle = new MovimientoDetalleDAO();

    public void run() {
        int op;
        do {
            mostrarMenu();
            op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> altaMovimiento();
                case 2 -> listarTodosView();
                case 3 -> listarPorRangoView();
                case 4 -> listarPorStaffView();
                case 5 -> sumarImportesPeriodo();
                case 6 -> eliminarMovimiento();
                case 0 -> System.out.println("Saliendo de MovimientoFlujo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=== MENÚ MOVIMIENTOS ===");
        System.out.println("1) Alta de movimiento");
        System.out.println("2) Listar todos");
        System.out.println("3) Listar por rango de fechas");
        System.out.println("4) Listar por staff (con rango opcional)");
        System.out.println("5) Sumar importes por período");
        System.out.println("6) Eliminar movimiento por ID");
        System.out.println("0) Salir");
    }

    //Acciones del flujo
    private void altaMovimiento() {
        System.out.println("\n      Alta de Movimiento");
        int idStaff = leerEnteroPositivo("ID Staff: ");
        LocalDateTime fechaHora = leerFechaHora("Fecha y hora (YYYY-MM-DD HH:MM): ");

        BigDecimal importe;
        //hasta que lo agregue correctamente
        while (true) {
            importe = leerBigDecimal("Importe (ej: 1499.99): ");
            if (importe != null && importe.compareTo(BigDecimal.ZERO) > 0) break;
            System.out.println("El importe debe ser > 0.");
        }

        byte medioPagoId = leerBytePositivo("MedioPago ID (1 Deb. Maest., 2 Cred. Maest., 3 Efectivo, 4 Mercado Pago, 5 Deb. Visa, 6 Cred. Visa): ");
        byte tipoClienteId = leerBytePositivo("TipoCliente ID (1 miembro, 3 socio, 4 cliente, 5 staff, 6 empresa): ");
        byte origenId = leerBytePositivo("Origen ID (1 membresia): ");


        //Ojo aca, en un movimiento debe haber o una membresia o un cliente, o ambas, pero al menos uno.
        String idMembStr = leerOpcional("ID Membresia (opcional): ");
        Integer idMembresia = parseIntegerOr(idMembStr, null); //esta me la googlee profe
        String idCliente = leerOpcional("CI Cliente (opcional): ");
        if ((idMembresia == null) && idCliente.isBlank()) {
            System.out.println("Debe indicar al menos ID de membresia o CI de cliente.");
            return;
        }
        if (idCliente.isBlank()) idCliente = null;

        //ordenadito ta mas bonito
        Movimiento m = new Movimiento(
                idStaff, fechaHora, importe,
                medioPagoId, tipoClienteId, origenId,
                idMembresia, idCliente
        );
        dao.insertarMovimiento(m);
        System.out.println("Movimiento insertado.");
    }

    //listar tod0
    private void listarTodos() {
        System.out.println("\n--- Listar todos (orden fecha DESC) ---");
        List<Movimiento> lista;
        try {
            lista = dao.listarTodos(); // existente
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
            return;
        }
        if (lista == null) lista = new ArrayList<>();
        if (lista.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        // Orden por fecha desc para presentación (por si el SQL alguna vez cambia)
        lista.sort(Comparator.comparing(Movimiento::getFechaHora, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        imprimirTabla(lista);
    }
    private void listarPorRango() {
        System.out.println("\n     Listar por rango ");
        LocalDateTime desde = leerFechaHora("Desde (YYYY-MM-DD HH:MM): ");
        LocalDateTime hasta = leerFechaHora("Hasta (YYYY-MM-DD HH:MM): ");
        List<Movimiento> lista;
        try {
            lista = dao.listarPorRango(desde, hasta);
        } catch (Exception e) {
            System.out.println("Error al listar por rango: " + e.getMessage());
            return;
        }
        if (lista.isEmpty()) {
            System.out.println("Sin resultados en ese rango.");
            return;
        }
        imprimirTabla(lista);
    }
    //ver los movimientos hechos por tal o cual staff, con opcion de si queremos darle un rango (por ej ver mes de un entrenador)
    private void listarPorStaff() {
        System.out.println("\n      Listar por staff");
        int idStaff = leerEnteroPositivo("ID Staff: ");
        String usarRango = leerOpcional("¿Filtrar por rango? (s/n, Enter = n): ").trim().toLowerCase();
        List<Movimiento> lista = null;
        try {
            if (usarRango.equals("s")) {
                LocalDateTime desde = leerFechaHora("Desde (YYYY-MM-DD HH:MM): ");
                LocalDateTime hasta = leerFechaHora("Hasta (YYYY-MM-DD HH:MM): ");
                try {
                    lista = dao.listarPorStaff(idStaff, desde, hasta); //polimorfismo aqui
                } catch (Exception e) {
                    System.out.println("Error al listar por rango: " + e.getMessage());
                }
            } else {
                lista = dao.listarPorStaff(idStaff);
            }
        } catch (Exception e) {
            System.out.println("Error al listar por staff: " + e.getMessage());
            return;
        }
        if (lista.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }
        imprimirTabla(lista);
    }

    private void sumarImportesPeriodo() {
        System.out.println("\n     Sumar importes de plazo");
        LocalDateTime desde = leerFechaHora("Desde (YYYY-MM-DD HH:MM): ");
        LocalDateTime hasta = leerFechaHora("Hasta (YYYY-MM-DD HH:MM): ");
        try {
            BigDecimal total = dao.sumarImportes(desde, hasta);
            System.out.println("Total: " + total);
        } catch (Exception e) {
            System.out.println("Error al sumar importes: " + e.getMessage());
        }
    }

    private void eliminarMovimiento() {
        System.out.println("\n      Eliminar movimiento ");
        long idMov = leerLongPositivo("ID del movimiento: ");
        if (!confirmar("¿Confirmar eliminacion? (s/n): ")) return;
        try {
            dao.eliminarMovimiento(idMov); // existente
            System.out.println("Eliminado.");
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    //Nuevos con View!
    private void listarTodosView() {
        System.out.println("\n      Listar todos ");
        List<MovimientoView> lista;
        try {
            lista = dao.listarViewTodos();
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
            return;
        }
        imprimirTablaViewOrdenada(lista);
    }

    private void listarPorRangoView() {
        System.out.println("\n       Listar por rango (nombres)");
        LocalDateTime desde = leerFechaHora("Desde (YYYY-MM-DD HH:MM): ");
        LocalDateTime hasta = leerFechaHora("Hasta (YYYY-MM-DD HH:MM): ");
        List<MovimientoView> lista;
        try {
            lista = dao.listarViewPorRango(desde, hasta);
        } catch (Exception e) {
            System.out.println("Error al listar por rango: " + e.getMessage());
            return;
        }
        imprimirTablaViewOrdenada(lista);
    }

    private void listarPorStaffView() {
        System.out.println("\n      Listar por staff (nombres)");
        int idStaff = leerEnteroPositivo("ID Staff: ");
        String usarRango = leerOpcional("¿Filtrar por rango? (s/n, Enter=n): ").trim().toLowerCase();
        List<MovimientoView> lista;
        try {
            if (usarRango.equals("s")) {
                LocalDateTime desde = leerFechaHora("Desde (YYYY-MM-DD HH:MM): ");
                LocalDateTime hasta = leerFechaHora("Hasta (YYYY-MM-DD HH:MM): ");
                lista = dao.listarViewPorStaff(idStaff, desde, hasta);
            } else {
                lista = dao.listarViewPorStaff(idStaff);
            }
        } catch (Exception e) {
            System.out.println("Error al listar por staff: " + e.getMessage());
            return;
        }
        imprimirTablaViewOrdenada(lista);
    }
    //fromateamos a tabla
    private void imprimirTabla(List<Movimiento> lista) {
        String[] headers = {"ID", "FechaHora", "Importe", "Staff", "Medio", "TipoCli", "Origen", "Memb.", "Cliente"};
        int[] anchos = {6, 16, 12, 6, 5, 7, 6, 6, 12};
        printHeader(headers, anchos);
        for (Movimiento m : lista) {
            System.out.println(formatRow(new Object[]{
                    nvl(m.getIdMov()),
                    nvl(m.getFechaHora()),      // ISO-8601
                    nvl(m.getImporte()),
                    nvl(m.getIdStaff()),
                    nvl(m.getMedioPagoID()),
                    nvl(m.getTipoClienteID()),
                    nvl(m.getOrigenId()),
                    nvl(m.getIdMembresia()),
                    nvl(m.getIdCliente())
            }, anchos));
        }
    }

    //formateamos tabla view
    private void imprimirTablaViewOrdenada(List<MovimientoView> lista) {
        if (lista == null) lista = new ArrayList<>();
        if (lista.isEmpty()) {
            System.out.println("Sin resultados.");
            return;
        }

        String[] headers = {"ID", "FechaHora", "Importe", "Staff", "MedioPago", "TipoCliente", "Origen", "Memb.", "Cliente"};
        int[] anchos= {6, 16, 12, 14, 12, 14, 12, 6, 20};
        printHeader(headers, anchos);

        for (MovimientoView v : lista) {
            System.out.println(formatRow(new Object[]{
                    nvl(v.getIdMov()),
                    nvl(v.getFechaHora()),
                    nvl(v.getImporte()),
                    nvl(v.getStaffNombre()),
                    nvl(v.getMedioPagoNombre()),
                    nvl(v.getTipoClienteNombre()),
                    nvl(v.getOrigenNombre()),
                    nvl(v.getIdMembresia()),
                    nvl(v.getClienteNombre())
            }, anchos));
        }
    }

    //Utilidades de movimiento:
    private Integer nullInt() { return null; }
}
