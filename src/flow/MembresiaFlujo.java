package flow;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Scanner;
import java.sql.Date;

import dao.ClienteDAO;
import dao.EventoMembresiaDAO;
import dao.MembresiaDAO;
import dao.PlanDAO;
import model.Cliente;
import model.EventoMembresia;
import model.Plan;
import model.Membresia;


public class MembresiaFlujo {
    private final Scanner scanner;
    public MembresiaFlujo(Scanner scanner) {
        this.scanner = scanner;
    }
    public void registrarMembresia() {
        System.out.print("Ingrese la cédula del cliente: ");
        String idcliente = scanner.nextLine();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorCi(idcliente);
        if (cliente == null) {
            System.out.println("No se encontró el cliente");
            return;
        }
        MembresiaDAO membresiaDAO = new MembresiaDAO();
        Membresia membresia = membresiaDAO.obtenerMembresiaPorCedula(idcliente);
        if (membresia != null) {
            System.out.println("Este cliente ya tiene una membresía. Puede renovarla.");
            return;
        }
        System.out.println("Datos del cliente:");
        System.out.println("-------------------------------");
        System.out.println("CI: " + cliente.getCi());
        System.out.println("Nombre: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Ciudad: " + cliente.getCiudad());
        System.out.println("Dirección: " + cliente.getDireccion());
        System.out.println("Teléfono: " + cliente.getTel());
        System.out.println("País: " + cliente.getPais());
        System.out.println("Fecha de ingreso: " + cliente.getFechaIngreso());
        System.out.println("-------------------------------");
        PlanDAO planDAO = new PlanDAO();
        Plan plan = null;
        String idplan = null;
        while (plan == null) {
            System.out.print("Ingrese el número del plan: ");
            idplan = scanner.nextLine();
            plan = planDAO.buscarPorId(Integer.parseInt(idplan));
            if (plan == null) {
                System.out.println("No se encontró el plan");
            }
        }
        int dias = switch (plan.getDuracionUnidadId()) {
            case 1 -> // Día
                    plan.getDuracionTotal();
            case 2 -> // Semana
                    plan.getDuracionTotal() * 7;
            case 3 -> // Mes
                    plan.getDuracionTotal() * 30;
            case 4 -> // Año
                    plan.getDuracionTotal() * 365;
            default -> 0;
        };
        Date fechaDeInicio = Date.valueOf(LocalDate.now());
        Date fechaDeFin = Date.valueOf(LocalDate.now().plusDays(dias));
        try {
            int idNuevaMembresia = membresiaDAO.agregarMembresia(new Membresia(-1, Integer.parseInt(idplan), idcliente, fechaDeInicio, fechaDeFin, 1));
            if (idNuevaMembresia == -1) {
                System.out.println("Error al registrar la membresia");
            }
            else {
                EventoMembresiaDAO eventoDAO = new EventoMembresiaDAO();
                eventoDAO.agregarEventoMembresia(new EventoMembresia(-1, 1, idNuevaMembresia, 2, Timestamp.from(Instant.now()), ""));
                System.out.println("Membresia registrada exitosamente. Fecha Inicio: " + fechaDeInicio + ", Fecha Fin: " + fechaDeFin);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar la membresia: " + e.getMessage());
        }

    }

    public void renovarMembresia() {
        System.out.print("Ingrese la cédula del cliente: ");
        String idcliente = scanner.nextLine();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorCi(idcliente);
        if (cliente == null) {
            System.out.println("No se encontró el cliente");
            return;
        }
        System.out.println("Datos del cliente:");
        System.out.println("-------------------------------");
        System.out.println("CI: " + cliente.getCi());
        System.out.println("Nombre: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Ciudad: " + cliente.getCiudad());
        System.out.println("Dirección: " + cliente.getDireccion());
        System.out.println("Teléfono: " + cliente.getTel());
        System.out.println("País: " + cliente.getPais());
        System.out.println("Fecha de ingreso: " + cliente.getFechaIngreso());
        System.out.println("-------------------------------");
        MembresiaDAO membresiaDAO = new MembresiaDAO();
        Membresia membresia = membresiaDAO.obtenerMembresiaPorCedula(idcliente);
        if (membresia == null) {
            System.out.println("No se encontró una membresía para este cliente. Regístrela primeno.");
        }
        else {
            System.out.println("Datos de la membresía:");
            System.out.println("------------------------------");
            System.out.println("Número de plan: " + membresia.getIdPlan());
            System.out.println("Fecha de inicio: " + membresia.getFechaInicio());
            System.out.println("Fecha de fin: " + membresia.getFechaFin());
            System.out.println("Estado: " + (membresia.getEstadoId() == 1 ? "Activa" : "Inactiva"));
            System.out.println("------------------------------");
            PlanDAO planDAO = new PlanDAO();
            Plan plan = null;
            String idplan = null;
            while (plan == null) {
                System.out.print("Ingrese el número del plan: ");
                idplan = scanner.nextLine();
                plan = planDAO.buscarPorId(Integer.parseInt(idplan));
                if (plan == null) {
                    System.out.println("No se encontró el plan");
                }
            }
            int dias = switch (plan.getDuracionUnidadId()) {
                case 1 -> // Día
                        plan.getDuracionTotal();
                case 2 -> // Semana
                        plan.getDuracionTotal() * 7;
                case 3 -> // Mes
                        plan.getDuracionTotal() * 30;
                case 4 -> // Año
                        plan.getDuracionTotal() * 365;
                default -> 0;
            };
            try {
                Date fechaDeInicio = Date.valueOf(LocalDate.now());
                Date fechaDeFin = Date.valueOf(LocalDate.now().plusDays(dias));
                membresiaDAO.modificarMembresia(new Membresia(membresia.getId(), Integer.parseInt(idplan), idcliente, fechaDeInicio, fechaDeFin, 1));
                EventoMembresiaDAO eventoDAO = new EventoMembresiaDAO();
                eventoDAO.agregarEventoMembresia(new EventoMembresia(-1, 1, membresia.getId(), 1, Timestamp.from(Instant.now()), ""));
                System.out.println("Membresia renovada exitosamente. Fecha Inicio: " + fechaDeInicio + ", Fecha Fin: " + fechaDeFin);
            } catch (Exception e) {
                System.out.println("Error al renovar la membresia" + e.getMessage());
            }
        }
    }

    public void listarMembresias() {
        MembresiaDAO membresiaDAO = new MembresiaDAO();
        membresiaDAO.listarMembresia();
    }

    public void eventosMembresia() {
        EventoMembresiaDAO eventoMembresiaDAO = new EventoMembresiaDAO();
        eventoMembresiaDAO.listarEventoMembresia();
    }

    public void mostrarMenu() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("--- Membresia Flujo ---");
            System.out.println("1. Registrar membresía");
            System.out.println("2. Renovar membresía");
            System.out.println("3. Listar membresías");
            System.out.println("4. Eventos membresías");
            System.out.println("5. Volver");
            System.out.print("Opcion: ");
            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1 -> registrarMembresia();
                case 2 -> renovarMembresia();
                case 3 -> listarMembresias();
                case 4 -> eventosMembresia();
                case 5 -> seguir = false;
                default -> System.out.println("Opcion invalida");
            }
        }
    }
}
