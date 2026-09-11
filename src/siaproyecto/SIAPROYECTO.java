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

        String[] opciones = {"Consola", "Ventana (GUI)"};
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
            System.out.println("1. Listar Alumnos (Ordenados por Curso)\n2. Agregar Alumno\n3. Eliminar Alumno\n4. Ver Asistencia de Alumno\n5. Marcar Asistencia Diaria\n6. Salir");
            System.out.print("Elija una opción: ");
            
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
                    
                    System.out.print("Nombre: "); String n = sc.nextLine();
                    if (n.trim().isEmpty()) { System.out.println("Error: Nombre vacío."); continue; }
                    
                    System.out.println("Cursos ya registrados: " + obtenerCursos(sistema));
                    System.out.print("Curso: "); String c = sc.nextLine();
                    if (c.trim().isEmpty()) { System.out.println("Error: Curso vacío."); continue; }
                    
                    sistema.agregarAlumno(new Alumno(r, n, c));
                    System.out.println("Alumno agregado exitosamente.");
                    
                } else if (opcion == 3) {
                    System.out.print("RUT del Alumno a eliminar (solo números): "); 
                    int r = Integer.parseInt(sc.nextLine());
                    if (sistema.eliminarAlumno(r)) {
                        System.out.println("Alumno eliminado correctamente.");
                    } else {
                        System.out.println("Error: No se encontró un alumno con ese RUT.");
                    }
                } else if (opcion == 4) {
                    System.out.print("RUT del Alumno (solo números): "); 
                    int r = Integer.parseInt(sc.nextLine());
                    Alumno al = sistema.buscarAlumno(r);
                    System.out.println(al.toString());
                    System.out.println(obtenerHistorialPorMes(al));
                } else if (opcion == 5) {
                    System.out.print("RUT (solo números): "); 
                    int r = Integer.parseInt(sc.nextLine());
                    System.out.print("Día (número): ");
                    int d = Integer.parseInt(sc.nextLine()); 
                    System.out.print("Mes (número): ");
                    int m = Integer.parseInt(sc.nextLine());
                    System.out.print("Estado (1:Pres, 2:Falta, 3:FaltaExt, 4:SalTemp): ");
                    int est = Integer.parseInt(sc.nextLine());
                    
                    sistema.marcarAsistencia(r, d, m, est);
                    System.out.println("Asistencia registrada con éxito.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error de ingreso: Ha ingresado texto donde se esperaba un número.");
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (opcion != 6);
    }

    private static void iniciarVentana(SistemaAsistencia sistema) {
        String[] menu = {"Listar Alumnos", "Agregar Alumno", "Eliminar Alumno", "Ver Ficha", "Marcar Asistencia", "Salir"};
        int opcion = 0;
        
        do {
            opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Menú Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, menu, menu[0]);
            
            try {
                if (opcion == 0) {
                    if (sistema.getAlumnos().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay alumnos registrados.");
                    } else {
                        JOptionPane.showMessageDialog(null, obtenerListadoPorCurso(sistema), "Lista de Alumnos", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (opcion == 1) {
                    String rutStr = JOptionPane.showInputDialog("RUT (solo números, 8 o 9 dígitos):");
                    if (rutStr == null) continue;
                    
                    if (rutStr.trim().length() < 8 || rutStr.trim().length() > 9) {
                        JOptionPane.showMessageDialog(null, "Error: El RUT debe tener 8 o 9 dígitos.", "RUT Inválido", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    int rut = Integer.parseInt(rutStr); 
                    
                    String nom = JOptionPane.showInputDialog("Nombre:");
                    if (nom == null || nom.trim().isEmpty()) continue;
                    
                    String mensajeCurso = "Cursos registrados: " + obtenerCursos(sistema) + "\n\nIngrese el Curso:";
                    String cur = JOptionPane.showInputDialog(mensajeCurso);
                    if (cur == null || cur.trim().isEmpty()) continue;
                    
                    sistema.agregarAlumno(new Alumno(rut, nom, cur));
                    JOptionPane.showMessageDialog(null, "Agregado correctamente.");
                    
                } else if (opcion == 2) {
                    String rutStr = JOptionPane.showInputDialog("RUT del Alumno a eliminar (solo números):");
                    if (rutStr == null) continue;
                    int rut = Integer.parseInt(rutStr);
                    
                    if (sistema.eliminarAlumno(rut)) {
                        JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: No se encontró un alumno con ese RUT.", "No encontrado", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else if (opcion == 3) {
                    String rutStr = JOptionPane.showInputDialog("RUT del Alumno (solo números):");
                    if (rutStr == null) continue; 
                    
                    int rut = Integer.parseInt(rutStr);
                    Alumno alumno = sistema.buscarAlumno(rut);
                    
                    StringBuilder ficha = new StringBuilder(alumno.toString());
                    ficha.append("\n\n").append(obtenerHistorialPorMes(alumno));
                    
                    JOptionPane.showMessageDialog(null, ficha.toString(), "Ficha del alumno", JOptionPane.INFORMATION_MESSAGE);
                    
                } else if (opcion == 4) {
                    String rutStr = JOptionPane.showInputDialog("RUT del Alumno (solo números):");
                    if (rutStr == null) continue;
                    int rut = Integer.parseInt(rutStr);
                    
                    String diaStr = JOptionPane.showInputDialog("Día (número):");
                    if (diaStr == null) continue;
                    int dia = Integer.parseInt(diaStr); 
                    
                    String mesStr = JOptionPane.showInputDialog("Mes (número):");
                    if (mesStr == null) continue;
                    int mes = Integer.parseInt(mesStr);
                    
                    String estStr = JOptionPane.showInputDialog("Estado (1:Pres, 2:Falta, 3:FaltaExt, 4:SalTemp):");
                    if (estStr == null) continue;
                    int estado = Integer.parseInt(estStr);
                    
                    sistema.marcarAsistencia(rut, dia, mes, estado);
                    JOptionPane.showMessageDialog(null, "Asistencia registrada.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: Debe ingresar valores numéricos válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
            }
        } while (opcion != 5 && opcion != -1); 
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
        
        StringBuilder sb = new StringBuilder("ALUMNOS POR CURSO:\n");
        for (Map.Entry<String, List<Alumno>> entrada : agrupados.entrySet()) {
            sb.append("\n--- ").append(entrada.getKey()).append(" ---\n");
            for (Alumno a : entrada.getValue()) {
                sb.append("  ").append(a.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    private static String obtenerHistorialPorMes(Alumno alumno) {
        int totalDias = alumno.getHistorial().size();
        int presentes = 0;
        
        for (RegistroAsistencia reg : alumno.getHistorial()) {
            if (reg.getEstado() == EstadoAsistencia.PRESENTE) {
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