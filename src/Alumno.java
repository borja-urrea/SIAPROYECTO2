package siaproyecto;
import java.util.ArrayList;
import java.util.List;

public class Alumno {
    private int rut; 
    private String nombre; 
    private String curso; 
    private List<RegistroAsistencia> historial;

    public Alumno(int rut, String nombre, String curso) {
        this.rut = rut; 
        this.nombre = nombre; 
        this.curso = curso;
        this.historial = new ArrayList<>();
    }
    
    public int getRut() { return rut; }
    public void setRut(int rut) { this.rut = rut; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    
    public List<RegistroAsistencia> getHistorial() { return historial; }
    
    public void agregarRegistro(RegistroAsistencia registro) throws RegistroDupException {
        for (RegistroAsistencia r : historial){
            if (r.getFecha().equals(registro.getFecha())){
                throw new RegistroDupException("Registro duplicado en la fecha " + registro.getFecha());
            }
        }
        historial.add(registro);
    }

    /* Tenemos la idea de implementar aqui un metodo para calcular y mostrar a los alumnos con riesgo de repitencia, por tiempo
    no pudimos en esta entrega, pero para la proxima, ademas del feedback queremos implementarlo*/ 
    
    
    @Override
    public String toString() {
        return "RUT: " + rut + " - Nombre: " + nombre + " - Curso: " + curso;
    }
    public RegistroAsistencia buscarRegistro(int dia, int mes) {
        for (RegistroAsistencia r : historial) {
            if (r.getFecha().getDia() == dia && r.getFecha().getMes() == mes) return r;
        }
        return null;
    }

    public boolean eliminarRegistro(int dia, int mes) {
        RegistroAsistencia r = buscarRegistro(dia, mes);
        if (r != null) {
            historial.remove(r);
            return true;
        }
        return false;
    }

    public boolean modificarRegistro(int dia, int mes, int nuevoEstado) {
        RegistroAsistencia r = buscarRegistro(dia, mes);
        if (r != null) {
            r.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }
}