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
                String[] datos = linea.split(",");
                if(datos.length == 3) {
                    int rut = Integer.parseInt(datos[0]); 
                    sistema.agregarAlumno(new Alumno(rut, datos[1], datos[2]));
                }
            }
        } catch (Exception e) { 
        }
    }
    
    private static void cargarAsistencias(SistemaAsistencia sistema){
        try (BufferedReader br = new BufferedReader(new FileReader("asistencias.csv"))){
            String linea;
            while ((linea = br.readLine()) != null){
                String[] datos = linea.split(",");
                if(datos.length == 4) {
                    int rut = Integer.parseInt(datos[0]);
                    int dia = Integer.parseInt(datos[1]);
                    int mes = Integer.parseInt(datos[2]);
                    int estado = Integer.parseInt(datos[3]);
                    sistema.marcarAsistencia(rut, dia, mes, estado);
                }
            }
        } catch (Exception e) {}
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