package siaproyecto;
import java.util.*;

public class SistemaAsistencia {
    private Map<Integer, Alumno> alumnos;
    
    public SistemaAsistencia(){
        this.alumnos = new HashMap<>();
    }
    
    public Map<Integer, Alumno> getAlumnos() { return Collections.unmodifiableMap(alumnos); }
    
    public void agregarAlumno(Alumno alumno) {
        alumnos.put(alumno.getRut(), alumno);
    }

    public Alumno buscarAlumno(int rut) throws AlumnoNoEncontradoExceptions {
        if(!alumnos.containsKey(rut)) {
            throw new AlumnoNoEncontradoExceptions("No se encontró el RUT: " + rut);
        }
        return alumnos.get(rut);
    }

    public Alumno buscarAlumno(String rutStr) throws AlumnoNoEncontradoExceptions {
        try {
            int rut = Integer.parseInt(rutStr);
            return buscarAlumno(rut);
        } catch (NumberFormatException e) {
            throw new AlumnoNoEncontradoExceptions("Formato de RUT inválido. Debe ser numérico.");
        }
    }

    public void marcarAsistencia(int rut, Fecha fecha, int estado) throws AlumnoNoEncontradoExceptions, RegistroDupException {
        Alumno alumno = buscarAlumno(rut);
        alumno.agregarRegistro(new RegistroAsistencia(fecha, estado));
    }
    
    public void marcarAsistencia(int rut, int dia, int mes, int estado) throws AlumnoNoEncontradoExceptions, RegistroDupException {
        marcarAsistencia(rut, new Fecha(dia, mes), estado);
    }


    public boolean eliminarAlumno(int rut) {
        if (alumnos.containsKey(rut)) {
            alumnos.remove(rut);
            return true;
        }
        return false;
    }
    
    /*public Alumno buscarAlumno(String nombreExacto) {
        for (Alumno a : alumnos.values()) {
            if (a.getNombre().equalsIgnoreCase(nombreExacto.trim())) return a;
        }
        return null;
    }*/


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

    public void modificarAlumno(int rut, String nuevoNombre, String nuevoCurso) throws AlumnoNoEncontradoExceptions {
        Alumno a = buscarAlumno(rut);
        if (a != null) {
            a.setNombre(nuevoNombre);
            a.setCurso(nuevoCurso);
        }
    }

}