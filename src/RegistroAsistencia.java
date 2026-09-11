package siaproyecto;

public class RegistroAsistencia {
    private Fecha fecha;
    private int estado;

    public RegistroAsistencia(Fecha fecha, int estado){
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        if (!EstadoAsistencia.esValido(estado)) {
            throw new IllegalArgumentException("El estado de asistencia no es válido.");
        }
        this.fecha = fecha;
        this.estado = estado;
    }
    
    public Fecha getFecha() { return fecha; }
    
    public void setFecha(Fecha fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        this.fecha = fecha;
    }
    
    public int getEstado() { return estado; }
    
    public void setEstado(int estado){
        if (!EstadoAsistencia.esValido(estado)) {
            throw new IllegalArgumentException("El estado de asistencia no es válido.");
        }
        this.estado = estado;
    }
}