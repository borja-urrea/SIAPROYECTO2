package siaproyecto;

/**
 * Entidad que asocia una fecha específica con el estado de asistencia 
 * de un alumno durante esa jornada.
 */
public class RegistroAsistencia {
    private Fecha fecha;
    private int estado;

    /**
     * Crea un nuevo registro de asistencia validando sus datos iniciales.
     * 
     * @param fecha Objeto Fecha que representa el día del registro.
     * @param estado Código numérico que define el tipo de asistencia.
     * @throws IllegalArgumentException Si la fecha es nula o el estado es inválido.
     */
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
    
    /**
     * @return La fecha asociada a este registro.
     */
    public Fecha getFecha() { return fecha; }
    
    /**
     * @param fecha La nueva fecha a asignar al registro.
     * @throws IllegalArgumentException Si la fecha proporcionada es nula.
     */
    public void setFecha(Fecha fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        this.fecha = fecha;
    }
    
    /**
     * @return El código numérico del estado de asistencia.
     */
    public int getEstado() { return estado; }
    
    /**
     * @param estado El nuevo código numérico de estado a asignar.
     * @throws IllegalArgumentException Si el estado no corresponde a un valor válido del sistema.
     */
    public void setEstado(int estado){
        if (!EstadoAsistencia.esValido(estado)) {
            throw new IllegalArgumentException("El estado de asistencia no es válido.");
        }
        this.estado = estado;
    }
}