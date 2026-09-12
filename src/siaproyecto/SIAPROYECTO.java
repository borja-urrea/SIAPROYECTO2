package siaproyecto;

import javax.swing.JOptionPane;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SIAPROYECTO {

    public static void main(String[] args) {
        SistemaAsistencia sistema = new SistemaAsistencia();
        
        GestorCSV.cargarDatos(sistema);

        String[] opciones = {"Consola", "Ventana (Menú de opciones)"};
        int seleccion = JOptionPane.showOptionDialog(null, 
            "¿Cómo desea ejecutar el sistema?", "Inicio - Gestión Asistencia",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
            null, opciones, opciones[0]);

        if (seleccion == 0) {
            iniciarConsola(sistema);
        } else if (seleccion == 1) {
            iniciarVentana(sistema);
        }

        GestorCSV.guardarDatos(sistema);
        System.out.println("Datos guardados en alumnos.csv y asistencias.csv. Sistema cerrado correctamente.");
    }

    private static void iniciarConsola(SistemaAsistencia sistema) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        
        do {
            System.out.println("\n--- MENU CONSOLA ---");
            System.out.println("1. Listar Alumnos");
            System.out.println("2. Agregar Alumno");
            System.out.println("3. Editar Alumno");
            System.out.println("4. Eliminar Alumno");
            System.out.println("5. Ver Ficha Alumno");
            System.out.println("6. Marcar Asistencia");
            System.out.println("7. Editar Asistencia");
            System.out.println("8. Eliminar Asistencia");
            System.out.println("9. Buscar Asistencia Específica");
            System.out.println("10. Salir");
            System.out.print("Opción: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                continue; 
            }

            try {
                if (opcion == 1) {
                    if (sistema.getAlumnos().isEmpty()) {
                        System.out.println("No hay alumnos registrados.");
                    } else {
                        System.out.println(obtenerListadoPorCurso(sistema));
                    }
                } else if (opcion == 2) {
                    System.out.print("RUT (solo números, 8 o 9 dígitos): "); 
                    String rutStr = sc.nextLine();
                    
                    if (rutStr.trim().length() < 8 || rutStr.trim().length() > 9) {
                        System.out.println("Error: El RUT debe tener 8 o 9 dígitos.");
                        continue;
                    }
                    
                    int r = Integer.parseInt(rutStr);
                    if (sistema.getAlumnos().containsKey(r)) {
                        System.out.println("Error: El RUT ya está registrado.");
                        continue;
                    }
                    
                    System.out.print("Nombre: "); 
                    String n = sc.nextLine();
                    if (n.trim().isEmpty()) { 
                        System.out.println("Error: Nombre vacío."); 
                        continue; 
                    }
                    
                    System.out.println("Cursos ya registrados: " + obtenerCursos(sistema));
                    System.out.print("Curso: "); 
                    String c = sc.nextLine();
                    if (c.trim().isEmpty()) { 
                        System.out.println("Error: Curso vacío."); 
                        continue; 
                    }
                    
                    c = c.toUpperCase();
                    sistema.agregarAlumno(new Alumno(r, n, c));
                    System.out.println("Alumno agregado exitosamente.");
                    
                } else if (opcion == 3) {
                    System.out.print("RUT del Alumno a modificar: "); 
                    int r = Integer.parseInt(sc.nextLine());
                    
                    System.out.print("Nuevo Nombre: "); 
                    String n = sc.nextLine();
                    
                    System.out.print("Nuevo Curso: "); 
                    String c = sc.nextLine();
                    c = c.toUpperCase();
                    
                    sistema.modificarAlumno(r, n, c);
                    System.out.println("Datos modificados correctamente.");
                    
                } else if (opcion == 4) {
                    System.out.print("RUT del Alumno a eliminar: "); 
                    int r = Integer.parseInt(sc.nextLine());
                    
                    if (sistema.eliminarAlumno(r)) {
                        System.out.println("Alumno eliminado correctamente.");
                    } else {
                        System.out.println("Error: No se encontró un alumno con ese RUT.");
                    }
                    
                } else if (opcion == 5) {
                    System.out.print("RUT del Alumno: "); 
                    int r = Integer.parseInt(sc.nextLine());
                    Alumno al = sistema.buscarAlumno(r);
                    
                    System.out.println(al.toString());
                    System.out.println(obtenerHistorialPorMes(al));
                    
                } else if (opcion == 6) {
                    System.out.print("RUT: "); 
                    int r = Integer.parseInt(sc.nextLine());
                    System.out.print("Día (número): "); 
                    int d = Integer.parseInt(sc.nextLine());
                    System.out.print("Mes (número): "); 
                    int m = Integer.parseInt(sc.nextLine());
                    System.out.print("Estado (1:Pres, 2:Falta, 3:FaltaExt, 4:SalTemp): "); 
                    int est = Integer.parseInt(sc.nextLine());
                    
                    sistema.marcarAsistencia(r, d, m, est);
                    System.out.println("Asistencia marcada.");
                    
                } else if (opcion == 7) {
                    System.out.print("RUT: "); 
                    int r = Integer.parseInt(sc.nextLine());
                    System.out.print("Día (número): "); 
                    int d = Integer.parseInt(sc.nextLine());
                    System.out.print("Mes (número): "); 
                    int m = Integer.parseInt(sc.nextLine());
                    System.out.print("Nuevo Estado (1:Pres, 2:Falta, 3:FaltaExt, 4:SalTemp): "); 
                    int est = Integer.parseInt(sc.nextLine());
                    
                    Alumno al = sistema.buscarAlumno(r);
                    if (al.modificarRegistro(d, m, est)) {
                        System.out.println("Registro editado correctamente.");
                    } else {
                        System.out.println("No se encontró el registro.");
                    }
                    
                } else if (opcion == 8) {
                    System.out.print("RUT: "); 
                    int r = Integer.parseInt(sc.nextLine());
                    System.out.print("Día (número): "); 
                    int d = Integer.parseInt(sc.nextLine());
                    System.out.print("Mes (número): "); 
                    int m = Integer.parseInt(sc.nextLine());
                    
                    Alumno al = sistema.buscarAlumno(r);
                    if (al.eliminarRegistro(d, m)) {
                        System.out.println("Registro eliminado.");
                    } else {
                        System.out.println("No se encontró el registro.");
                    }
                    
                } else if (opcion == 9) {
                    System.out.print("RUT: "); 
                    int r = Integer.parseInt(sc.nextLine());
                    System.out.print("Día (número): "); 
                    int d = Integer.parseInt(sc.nextLine());
                    System.out.print("Mes (número): "); 
                    int m = Integer.parseInt(sc.nextLine());
                    
                    Alumno al = sistema.buscarAlumno(r);
                    RegistroAsistencia reg = al.buscarRegistro(d, m);
                    
                    if (reg != null) {
                        System.out.println("Fecha: " + reg.getFecha() + " | Estado: " + EstadoAsistencia.describir(reg.getEstado()));
                    } else {
                        System.out.println("No existe registro en esa fecha.");
                    }
                    
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (opcion != 10);
    }

    private static void iniciarVentana(SistemaAsistencia sistema) {
        String[] menu = {
            "1. Listar Alumnos", 
            "2. Agregar Alumno", 
            "3. Editar Alumno", 
            "4. Eliminar Alumno", 
            "5. Ver Ficha Alumno",
            "6. Marcar Asistencia", 
            "7. Editar Asistencia", 
            "8. Eliminar Asistencia", 
            "9. Buscar Asistencia Específica", 
            "10. Salir"
        };
        
        while (true) {
            String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione una operación:", "Menú Principal", JOptionPane.QUESTION_MESSAGE, null, menu, menu[0]);
            
            if (seleccion == null || seleccion.equals("10. Salir")) {
                break;
            }

            try {
                if (seleccion.startsWith("1")) {
                    if (sistema.getAlumnos().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay alumnos registrados.");
                    } else {
                        JOptionPane.showMessageDialog(null, obtenerListadoPorCurso(sistema), "Lista de Alumnos", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                } else if (seleccion.startsWith("2")) {
                    String rutStr = JOptionPane.showInputDialog("RUT (solo números, 8 o 9 dígitos):");
                    if (rutStr == null) {
                        continue;
                    }
                    
                    if (rutStr.trim().length() < 8 || rutStr.trim().length() > 9) {
                        JOptionPane.showMessageDialog(null, "Error: El RUT debe tener 8 o 9 dígitos.", "RUT Inválido", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    
                    int rut = Integer.parseInt(rutStr); 
                    if (sistema.getAlumnos().containsKey(rut)) {
                        JOptionPane.showMessageDialog(null, "Error: El RUT ya está registrado.", "RUT Duplicado", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    
                    String nom = JOptionPane.showInputDialog("Nombre:");
                    if (nom == null || nom.trim().isEmpty()) {
                        continue;
                    }
                    
                    String mensajeCurso = "Cursos registrados: " + obtenerCursos(sistema) + "\n\nIngrese el Curso:";
                    String cur = JOptionPane.showInputDialog(mensajeCurso);
                    if (cur == null || cur.trim().isEmpty()) {
                        continue;
                    }
                    
                    cur = cur.toUpperCase();
                    sistema.agregarAlumno(new Alumno(rut, nom, cur));
                    JOptionPane.showMessageDialog(null, "Agregado correctamente.");
                    
                } else if (seleccion.startsWith("3")) {
                    String rutStr = JOptionPane.showInputDialog("RUT a editar:");
                    if (rutStr == null) {
                        continue;
                    }
                    int rut = Integer.parseInt(rutStr);
                    
                    String nom = JOptionPane.showInputDialog("Nuevo Nombre:");
                    if (nom == null || nom.trim().isEmpty()) {
                        continue;
                    }
                    
                    String cur = JOptionPane.showInputDialog("Nuevo Curso:");
                    if (cur == null || cur.trim().isEmpty()) {
                        continue;
                    }
                    
                    cur = cur.toUpperCase();
                    sistema.modificarAlumno(rut, nom, cur);
                    JOptionPane.showMessageDialog(null, "Modificado correctamente.");
                    
                } else if (seleccion.startsWith("4")) {
                    String rutStr = JOptionPane.showInputDialog("RUT a eliminar:");
                    if (rutStr == null) {
                        continue;
                    }
                    int rut = Integer.parseInt(rutStr);
                    
                    if (sistema.eliminarAlumno(rut)) {
                        JOptionPane.showMessageDialog(null, "Eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el alumno.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else if (seleccion.startsWith("5")) {
                    String rutStr = JOptionPane.showInputDialog("RUT:");
                    if (rutStr == null) {
                        continue;
                    }
                    int rut = Integer.parseInt(rutStr);
                    Alumno al = sistema.buscarAlumno(rut);
                    
                    StringBuilder ficha = new StringBuilder(al.toString());
                    ficha.append("\n\n").append(obtenerHistorialPorMes(al));
                    JOptionPane.showMessageDialog(null, ficha.toString(), "Ficha del alumno", JOptionPane.INFORMATION_MESSAGE);
                    
                } else if (seleccion.startsWith("6")) {
                    String rutStr = JOptionPane.showInputDialog("RUT:");
                    if (rutStr == null) {
                        continue;
                    }
                    int rut = Integer.parseInt(rutStr);
                    
                    String dStr = JOptionPane.showInputDialog("Día (número):");
                    if (dStr == null) {
                        continue;
                    }
                    int dia = Integer.parseInt(dStr);
                    
                    String mStr = JOptionPane.showInputDialog("Mes (número):");
                    if (mStr == null) {
                        continue;
                    }
                    int mes = Integer.parseInt(mStr);
                    
                    String estStr = JOptionPane.showInputDialog("Estado (1:Pres, 2:Falta, 3:FaltaExt, 4:SalTemp):");
                    if (estStr == null) {
                        continue;
                    }
                    int estado = Integer.parseInt(estStr);
                    
                    sistema.marcarAsistencia(rut, dia, mes, estado);
                    JOptionPane.showMessageDialog(null, "Asistencia registrada.");
                    
                } else if (seleccion.startsWith("7")) {
                    String rutStr = JOptionPane.showInputDialog("RUT:");
                    if (rutStr == null) {
                        continue;
                    }
                    int rut = Integer.parseInt(rutStr);
                    
                    String dStr = JOptionPane.showInputDialog("Día (número):");
                    if (dStr == null) {
                        continue;
                    }
                    int dia = Integer.parseInt(dStr);
                    
                    String mStr = JOptionPane.showInputDialog("Mes (número):");
                    if (mStr == null) {
                        continue;
                    }
                    int mes = Integer.parseInt(mStr);
                    
                    String estStr = JOptionPane.showInputDialog("Nuevo Estado (1:Pres, 2:Falta, 3:FaltaExt, 4:SalTemp):");
                    if (estStr == null) {
                        continue;
                    }
                    int estado = Integer.parseInt(estStr);
                    
                    Alumno al = sistema.buscarAlumno(rut);
                    if (al.modificarRegistro(dia, mes, estado)) {
                        JOptionPane.showMessageDialog(null, "Registro editado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Registro no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else if (seleccion.startsWith("8")) {
                    String rutStr = JOptionPane.showInputDialog("RUT:");
                    if (rutStr == null) {
                        continue;
                    }
                    int rut = Integer.parseInt(rutStr);
                    
                    String dStr = JOptionPane.showInputDialog("Día (número):");
                    if (dStr == null) {
                        continue;
                    }
                    int dia = Integer.parseInt(dStr);
                    
                    String mStr = JOptionPane.showInputDialog("Mes (número):");
                    if (mStr == null) {
                        continue;
                    }
                    int mes = Integer.parseInt(mStr);
                    
                    Alumno al = sistema.buscarAlumno(rut);
                    if (al.eliminarRegistro(dia, mes)) {
                        JOptionPane.showMessageDialog(null, "Registro eliminado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Registro no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else if (seleccion.startsWith("9")) {
                    String rutStr = JOptionPane.showInputDialog("RUT:");
                    if (rutStr == null) {
                        continue;
                    }
                    int rut = Integer.parseInt(rutStr);
                    
                    String dStr = JOptionPane.showInputDialog("Día (número):");
                    if (dStr == null) {
                        continue;
                    }
                    int dia = Integer.parseInt(dStr);
                    
                    String mStr = JOptionPane.showInputDialog("Mes (número):");
                    if (mStr == null) {
                        continue;
                    }
                    int mes = Integer.parseInt(mStr);
                    
                    Alumno al = sistema.buscarAlumno(rut);
                    RegistroAsistencia reg = al.buscarRegistro(dia, mes);
                    
                    if (reg != null) {
                        JOptionPane.showMessageDialog(null, "Fecha: " + reg.getFecha() + "\nEstado: " + EstadoAsistencia.describir(reg.getEstado()));
                    } else {
                        JOptionPane.showMessageDialog(null, "Registro no encontrado.", "Búsqueda", JOptionPane.WARNING_MESSAGE);
                    }
                    
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: Verifique los datos ingresados.");
            }
        }
    }

    private static String obtenerCursos(SistemaAsistencia sistema) {
        TreeSet<String> cursos = new TreeSet<>();
        
        for (Alumno a : sistema.getAlumnos().values()) {
            cursos.add(a.getCurso());
        }
        
        if (cursos.isEmpty()) {
            return "(Ninguno)";
        }
        
        return String.join(", ", cursos);
    }

    private static String obtenerListadoPorCurso(SistemaAsistencia sistema) {
        TreeMap<String, List<Alumno>> agrupados = new TreeMap<>();
        
        for (Alumno a : sistema.getAlumnos().values()) {
            if (!agrupados.containsKey(a.getCurso())) {
                agrupados.put(a.getCurso(), new ArrayList<>());
            }
            agrupados.get(a.getCurso()).add(a);
        }
        
        if (agrupados.isEmpty()) {
            return "No hay alumnos.";
        }
        
        StringBuilder sb = new StringBuilder("ALUMNOS POR CURSO:\n");
        for (Map.Entry<String, List<Alumno>> entrada : agrupados.entrySet()) {
            sb.append("\n----- ").append(entrada.getKey()).append(" ------\n");
            for (Alumno a : entrada.getValue()) {
                sb.append(a.getRut()).append(" ").append(a.getNombre()).append("\n");
            }
        }
        
        return sb.toString();
    }

    private static String obtenerHistorialPorMes(Alumno alumno) {
        int totalDias = alumno.getHistorial().size();
        int presentes = 0;
        
        for (RegistroAsistencia reg : alumno.getHistorial()) {
            if (reg.getEstado() == 1) {
                presentes++;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Días considerados: ").append(totalDias)
          .append(" | Presentes: ").append(presentes).append("\n\n");
        
        if (totalDias == 0) {
            sb.append("Sin registros de asistencia.");
            return sb.toString();
        }
        
        TreeMap<Integer, List<RegistroAsistencia>> agrupados = new TreeMap<>();
        for (RegistroAsistencia reg : alumno.getHistorial()) {
            int mes = reg.getFecha().getMes(); 
            if (!agrupados.containsKey(mes)) {
                agrupados.put(mes, new ArrayList<>());
            }
            agrupados.get(mes).add(reg);
        }
        
        sb.append("HISTORIAL ORDENADO POR MES:\n");
        for (Map.Entry<Integer, List<RegistroAsistencia>> entrada : agrupados.entrySet()) {
            sb.append("\n--- Mes ").append(entrada.getKey()).append(" ---\n");
            for (RegistroAsistencia reg : entrada.getValue()) {
                sb.append("  ").append(reg.getFecha().toString()).append(" - ").append(EstadoAsistencia.describir(reg.getEstado())).append("\n");
            }
        }
        return sb.toString();
    }
}