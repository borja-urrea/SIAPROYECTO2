package siaproyecto;
import java.util.*;

/**
 * Controlador principal del modelo de negocio.
 * Administra el almacenamiento en memoria de los alumnos, la gestión de sus asistencias
 * y la generación de lógicas cruzadas como los reportes de riesgo.
 */
public class SistemaAsistencia {
    private Map<Integer, Alumno> alumnos;
    
    /**
     * Inicializa el sistema creando la estructura de datos interna
     * basada en un mapa hash para búsquedas eficientes por RUT.
     */
    public SistemaAsistencia(){
        this.alumnos = new HashMap<>();
    }
    
    /**
     * Proporciona acceso de solo lectura al catálogo de alumnos registrados.
     * 
     * @return Mapa inmodificable de los estudiantes.
     */
    public Map<Integer, Alumno> getAlumnos() { 
        return Collections.unmodifiableMap(alumnos); 
    }
    
    /**
     * Registra un nuevo estudiante en la base de datos en memoria.
     * 
     * @param alumno Instancia del alumno a almacenar.
     */
    public void agregarAlumno(Alumno alumno) {
        alumnos.put(alumno.getRut(), alumno);
    }

    /**
     * Recupera un estudiante específico utilizando su identificador numérico.
     * 
     * @param rut RUT del estudiante (solo números).
     * @return El objeto Alumno correspondiente.
     * @throws AlumnoNoEncontradoExceptions Si el RUT no figura en los registros.
     */
    public Alumno buscarAlumno(int rut) throws AlumnoNoEncontradoExceptions {
        if(!alumnos.containsKey(rut)) {
            throw new AlumnoNoEncontradoExceptions("No se encontró el RUT: " + rut);
        }
        return alumnos.get(rut);
    }

    /**
     * Sobrecarga del método de búsqueda que maneja entradas en formato de texto.
     * Procesa la conversión y delega la lógica de negocio al método principal.
     * 
     * @param rutStr Cadena de texto que representa el RUT.
     * @return El objeto Alumno correspondiente.
     * @throws AlumnoNoEncontradoExceptions Si el formato es incorrecto o el alumno no existe.
     */
    public Alumno buscarAlumno(String rutStr) throws AlumnoNoEncontradoExceptions {
        try {
            int rut = Integer.parseInt(rutStr);
            return buscarAlumno(rut);
        } catch (NumberFormatException e) {
            throw new AlumnoNoEncontradoExceptions("Formato de RUT inválido. Debe ser numérico.");
        }
    }

    /**
     * Añade un registro de asistencia al historial de un estudiante determinado.
     * 
     * @param rut RUT del alumno.
     * @param fecha Objeto instanciado de la fecha a registrar.
     * @param estado Código del estado de asistencia.
     * @throws AlumnoNoEncontradoExceptions Si el alumno no existe.
     * @throws RegistroDupException Si la fecha ya se encuentra ingresada para ese estudiante.
     */
    public void marcarAsistencia(int rut, Fecha fecha, int estado) throws AlumnoNoEncontradoExceptions, RegistroDupException {
        Alumno alumno = buscarAlumno(rut);
        alumno.agregarRegistro(new RegistroAsistencia(fecha, estado));
    }
    
    /**
     * Sobrecarga que permite registrar una asistencia a partir de datos primitivos.
     * 
     * @param rut RUT del alumno.
     * @param dia Día correspondiente a la asistencia.
     * @param mes Mes correspondiente a la asistencia.
     * @param estado Código del estado de asistencia.
     * @throws AlumnoNoEncontradoExceptions Si el alumno no existe.
     * @throws RegistroDupException Si la fecha ya se encuentra ingresada.
     */
    public void marcarAsistencia(int rut, int dia, int mes, int estado) throws AlumnoNoEncontradoExceptions, RegistroDupException {
        marcarAsistencia(rut, new Fecha(dia, mes), estado);
    }

    /**
     * Retira a un estudiante del sistema basándose en su RUT.
     * 
     * @param rut Identificador del estudiante a eliminar.
     * @return true si el proceso de eliminación fue exitoso, false si no se encontró el estudiante.
     */
    public boolean eliminarAlumno(int rut) {
        if (alumnos.containsKey(rut)) {
            alumnos.remove(rut);
            return true;
        }
        return false;
    }
    
    /**
     * Genera un reporte analítico de todos los alumnos cuyo porcentaje de asistencia
     * efectiva esté por debajo del límite reglamentario del 75%.
     * 
     * @return Un bloque de texto con el listado detallado, o un mensaje de éxito si nadie corre riesgo.
     */
    public String obtenerAlumnosEnRiesgo() {
        if (alumnos.isEmpty()) return "No hay alumnos registrados en el sistema.";
        
        StringBuilder sb = new StringBuilder("ALUMNOS EN RIESGO DE REPITENCIA (< 75%):\n\n");
        boolean hayRiesgo = false;

        for (Alumno a : alumnos.values()) {
            double porcentaje = a.calcularPorcentajeAsistencia();
            if (porcentaje < 75.0) {
                sb.append("RUT: ").append(a.getRut())
                  .append(" | ").append(a.getNombre())
                  .append(" (Curso: ").append(a.getCurso()).append(")\n")
                  .append("-> Asistencia actual: ").append(String.format("%.1f", porcentaje)).append("%\n\n");
                hayRiesgo = true;
            }
        }

        if (!hayRiesgo) {
            return "Excelente: No hay alumnos en riesgo de repitencia en este momento.";
        }
        return sb.toString();
    }

    /**
     * Actualiza la información personal de un alumno registrado.
     * 
     * @param rut RUT original que identifica al alumno.
     * @param nuevoNombre Nombre actualizado a registrar.
     * @param nuevoCurso Curso actualizado a registrar.
     * @throws AlumnoNoEncontradoExceptions Si el RUT proporcionado no figura en el sistema.
     */
    public void modificarAlumno(int rut, String nuevoNombre, String nuevoCurso) throws AlumnoNoEncontradoExceptions {
        Alumno a = buscarAlumno(rut);
        if (a != null) {
            a.setNombre(nuevoNombre);
            a.setCurso(nuevoCurso);
        }
    }
}