package siaproyecto;
import java.io.*;

public class GestorCSV {
    
    public static void cargarDatos(SistemaAsistencia sistema){
        cargarDatosAlumnos(sistema);
        cargarAsistencias(sistema);
    }
    
    public static void cargarDatosAlumnos(SistemaAsistencia sistema) {
        try (BufferedReader br = new BufferedReader(new FileReader("alumnos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue; 
                String[] datos = linea.split(",");
                if(datos.length == 3) {
                    try {
                        int rut = Integer.parseInt(datos[0].trim()); 
                        sistema.agregarAlumno(new Alumno(rut, datos[1].trim(), datos[2].trim()));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (Exception e) { 
            System.out.println("Aviso: No se pudo cargar alumnos.csv (puede que no exista aun).");
        }
    }
    
    private static void cargarAsistencias(SistemaAsistencia sistema){
        try (BufferedReader br = new BufferedReader(new FileReader("asistencias.csv"))){
            String linea;
            while ((linea = br.readLine()) != null){
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(",");
                if(datos.length >= 4) {
                    try {
                        int rut = Integer.parseInt(datos[0].trim());
                        int dia = Integer.parseInt(datos[1].trim());
                        int mes = Integer.parseInt(datos[2].trim());
                        int estado = Integer.parseInt(datos[3].trim());
                        
                        sistema.marcarAsistencia(rut, dia, mes, estado);
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Aviso: No se pudo cargar asistencias.csv (puede que no exista aun).");
        }
    }
    
    public static void guardarDatos(SistemaAsistencia sistema) {
        guardarAlumnos(sistema);
        guardarAsistencias(sistema);
    }

    private static void guardarAlumnos(SistemaAsistencia sistema) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("alumnos.csv"))) {
            for (Alumno al : sistema.getAlumnos().values()) {
                pw.println(al.getRut() + "," + al.getNombre() + "," + al.getCurso());
            }
        } catch (IOException e) {
            System.out.println("Error guardando alumnos: " + e.getMessage());
        }
    }

    private static void guardarAsistencias(SistemaAsistencia sistema) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("asistencias.csv"))) {
            for (Alumno al : sistema.getAlumnos().values()) {
                for (RegistroAsistencia registro : al.getHistorial()) {
                    pw.println(al.getRut() + "," 
                             + registro.getFecha().getDia() + "," 
                             + registro.getFecha().getMes() + "," 
                             + registro.getEstado());
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando asistencias: " + e.getMessage());
        }
    }
}