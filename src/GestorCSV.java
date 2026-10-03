package siaproyecto;
import java.io.*;

/**
 * Gestor de persistencia del sistema basado en archivos planos (CSV).
 * Separa la carga y escritura de la información en flujos independientes para
 * alumnos y sus historiales de asistencia.
 */
public class GestorCSV {
    
    /**
     * Ejecuta el proceso de carga completa (alumnos y asistencias) al iniciar la aplicación.
     * 
     * @param sistema Instancia en memoria donde se alojarán los datos recuperados.
     */
    public static void cargarDatos(SistemaAsistencia sistema){
        cargarDatosAlumnos(sistema);
        cargarAsistencias(sistema);
    }
    
    /**
     * Recupera los perfiles de los estudiantes desde el archivo alumnos.csv.
     * Implementa manejo de excepciones para ignorar líneas corruptas y continuar la lectura.
     * 
     * @param sistema Instancia central para poblar la colección de alumnos.
     */
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
    
    /**
     * Recupera los historiales de asistencia cruzándolos con los alumnos previamente cargados.
     * 
     * @param sistema Instancia central donde se registrarán las fechas recuperadas.
     */
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
    
    /**
     * Dispara el volcado en disco de todos los registros que actualmente
     * residen en la memoria principal.
     * 
     * @param sistema Instancia central que contiene la información actualizada.
     */
    public static void guardarDatos(SistemaAsistencia sistema) {
        guardarAlumnos(sistema);
        guardarAsistencias(sistema);
    }

    /**
     * Escribe la información general de los estudiantes en su respectivo archivo CSV.
     * 
     * @param sistema Instancia central que provee el catálogo de estudiantes.
     */
    private static void guardarAlumnos(SistemaAsistencia sistema) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("alumnos.csv"))) {
            for (Alumno al : sistema.getAlumnos().values()) {
                pw.println(al.getRut() + "," + al.getNombre() + "," + al.getCurso());
            }
        } catch (IOException e) {
            System.out.println("Error guardando alumnos: " + e.getMessage());
        }
    }

    /**
     * Escribe la totalidad de los historiales de asistencia en su respectivo archivo CSV.
     * Recorre cada alumno y extrae sus fechas para generar un formato plano estructurado.
     * 
     * @param sistema Instancia central de la cual se recuperan los datos.
     */
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