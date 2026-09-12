package siaproyecto;
import java.util.*;

public class SistemaAsistencia {
    private Map<Integer, Alumno> alumnos;
    
    public SistemaAsistencia(){
        this.alumnos = new HashMap<>();
    }
    
    public Map<Integer, Alumno> getAlumnos() { return alumnos; }
    
    public void agregarAlumno(Alumno alumno) {
        alumnos.put(alumno.getRut(), alumno);
    }

    public Alumno buscarAlumno(int rut) throws AlumnoNoEncontradoExceptions {
        if(!alumnos.containsKey(rut)) {
            throw new AlumnoNoEncontradoExceptions("No se encontró el RUT: " + rut);
        }
        return alumnos.get(rut);
    }

    public void marcarAsistencia(int rut, Fecha fecha, int estado) throws AlumnoNoEncontradoExceptions, RegistroDupException {
        Alumno alumno = buscarAlumno(rut);
        alumno.agregarRegistro(new RegistroAsistencia(fecha, estado));
    }
    
    public void marcarAsistencia(int rut, int dia, int mes, int estado) throws AlumnoNoEncontradoExceptions, RegistroDupException {
        marcarAsistencia(rut, new Fecha(dia, mes), estado);
    }

    public List<Alumno> obtenerAlumnosEnRiesgo(double porcentajeMinimo) {
        List<Alumno> enRiesgo = new ArrayList<>();
        for(Alumno al : alumnos.values()) {
            if(!al.getHistorial().isEmpty() && al.calcularPorcentajeAsistencia() < porcentajeMinimo) {
                enRiesgo.add(al);
            }
        }
        return enRiesgo;
    }
    public boolean eliminarAlumno(int rut) {
        if (alumnos.containsKey(rut)) {
            alumnos.remove(rut);
            return true;
        }
        return false;
    }
    
    public Alumno buscarAlumno(String nombreExacto) {
        for (Alumno a : alumnos.values()) {
            if (a.getNombre().equalsIgnoreCase(nombreExacto.trim())) return a;
        }
        return null;
    }

    public void modificarAlumno(int rut, String nuevoNombre, String nuevoCurso) throws AlumnoNoEncontradoExceptions {
        Alumno a = buscarAlumno(rut);
        if (a != null) {
            a.setNombre(nuevoNombre);
            a.setCurso(nuevoCurso);
        }
    }

    public List<Alumno> obtenerAlumnosEnRiesgo() {
        List<Alumno> enRiesgo = new ArrayList<>();
        for (Alumno a : alumnos.values()) {
            int total = a.getHistorial().size();
            if (total == 0) continue;
            
            int presentes = 0;
            for (RegistroAsistencia r : a.getHistorial()) {
                if (r.getEstado() == 1) presentes++;
            }
            
            double porcentaje = (double) presentes / total;
            if (porcentaje < 0.75) {
                enRiesgo.add(a);
            }
        }
        return enRiesgo;
    }
}