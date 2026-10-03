package siaproyecto;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un estudiante dentro del sistema.
 * Almacena su información personal y el historial completo de sus registros de asistencia.
 */
public class Alumno {
    private int rut; 
    private String nombre; 
    private String curso; 
    private List<RegistroAsistencia> historial;

    /**
     * Constructor principal para inicializar un alumno con todos sus datos obligatorios.
     * 
     * @param rut Identificador numérico único del alumno.
     * @param nombre Nombre completo del alumno.
     * @param curso Curso al que pertenece el alumno.
     */
    public Alumno(int rut, String nombre, String curso) {
        this.rut = rut; 
        this.nombre = nombre; 
        this.curso = curso;
        this.historial = new ArrayList<>();
    }

    /**
     * Constructor sobrecargado para registrar alumnos que aún no tienen un curso asignado.
     * Asigna por defecto el valor "SIN ASIGNAR" al curso.
     * 
     * @param rut Identificador numérico único del alumno.
     * @param nombre Nombre completo del alumno.
     */
    public Alumno(int rut, String nombre) {
        this(rut, nombre, "SIN ASIGNAR"); 
    }
    
    /**
     * @return El RUT del alumno.
     */
    public int getRut() { return rut; }

    /**
     * @param rut El nuevo RUT a asignar al alumno.
     */
    public void setRut(int rut) { this.rut = rut; }

    /**
     * @return El nombre completo del alumno.
     */
    public String getNombre() { return nombre; }

    /**
     * @param nombre El nuevo nombre a asignar al alumno.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * @return El curso actual del alumno.
     */
    public String getCurso() { return curso; }

    /**
     * @param curso El nuevo curso a asignar al alumno.
     */
    public void setCurso(String curso) { this.curso = curso; }
    
    /**
     * Retorna el historial de asistencia del alumno como una lista de solo lectura,
     * previniendo modificaciones externas no autorizadas a la colección.
     * 
     * @return Lista inmodificable de registros de asistencia.
     */
    public List<RegistroAsistencia> getHistorial() { 
        return java.util.Collections.unmodifiableList(historial); 
    }
    
    /**
     * Añade un nuevo registro al historial de asistencia del alumno.
     * 
     * @param registro El objeto RegistroAsistencia a incorporar.
     * @throws RegistroDupException Si ya existe un registro con la misma fecha en el historial.
     */
    public void agregarRegistro(RegistroAsistencia registro) throws RegistroDupException {
        for (RegistroAsistencia r : historial){
            if (r.getFecha().equals(registro.getFecha())){
                throw new RegistroDupException("Registro duplicado en la fecha " + registro.getFecha());
            }
        }
        historial.add(registro);
    }
    
    /**
     * Sobrecarga del método agregarRegistro que crea e inserta la asistencia 
     * a partir de los datos primitivos de fecha y estado.
     * 
     * @param dia Día de la asistencia.
     * @param mes Mes de la asistencia.
     * @param estado Código representativo del estado de la asistencia.
     * @throws RegistroDupException Si la fecha indicada ya se encuentra registrada.
     */
    public void agregarRegistro(int dia, int mes, int estado) throws RegistroDupException {
        Fecha nuevaFecha = new Fecha(dia, mes);
        RegistroAsistencia nuevoRegistro = new RegistroAsistencia(nuevaFecha, estado);
        this.agregarRegistro(nuevoRegistro); 
    } 
    
    /**
     * Realiza el cálculo del porcentaje de asistencia del alumno, 
     * aplicando descuentos por faltas extraordinarias y penalizaciones por salidas tempranas.
     * 
     * @return El porcentaje de asistencia efectiva (valor decimal entre 0.0 y 100.0).
     */
    public double calcularPorcentajeAsistencia() {
        if (historial.isEmpty()) {
            return 100.0; 
        }

        int presentes = 0;
        int faltasExt = 0;
        int salidasTemp = 0;

        for (RegistroAsistencia r : historial) {
            switch(r.getEstado()) {
                case 1: presentes++; break;
                case 3: faltasExt++; break;
                case 4: salidasTemp++; break;
            }
        }
        int diasEvaluables = historial.size() - faltasExt;
        
        if (diasEvaluables == 0) {
            return 100.0; 
        }

        int diasAsistidos = presentes + salidasTemp;
        int penalizacion = salidasTemp / 3;
        int asistenciaEfectiva = diasAsistidos - penalizacion;

        return ((double) asistenciaEfectiva / diasEvaluables) * 100.0;
    }
    
    @Override
    public String toString() {
        return "RUT: " + rut + " - Nombre: " + nombre + " - Curso: " + curso;
    }

    /**
     * Busca en el historial un registro de asistencia que coincida con la fecha indicada.
     * 
     * @param dia Día a buscar.
     * @param mes Mes a buscar.
     * @return El RegistroAsistencia encontrado, o null si no existe registro en esa fecha.
     */
    public RegistroAsistencia buscarRegistro(int dia, int mes) {
        for (RegistroAsistencia r : historial) {
            if (r.getFecha().getDia() == dia && r.getFecha().getMes() == mes) return r;
        }
        return null;
    }

    /**
     * Elimina del historial el registro de asistencia correspondiente a la fecha dada.
     * 
     * @param dia Día del registro a eliminar.
     * @param mes Mes del registro a eliminar.
     * @return true si el registro fue encontrado y eliminado, false en caso contrario.
     */
    public boolean eliminarRegistro(int dia, int mes) {
        RegistroAsistencia r = buscarRegistro(dia, mes);
        if (r != null) {
            historial.remove(r);
            return true;
        }
        return false;
    }

    /**
     * Modifica el estado de un registro de asistencia ya existente en el historial.
     * 
     * @param dia Día del registro a modificar.
     * @param mes Mes del registro a modificar.
     * @param nuevoEstado El nuevo código de estado que se asignará.
     * @return true si la modificación fue exitosa, false si el registro no existe.
     */
    public boolean modificarRegistro(int dia, int mes, int nuevoEstado) {
        RegistroAsistencia r = buscarRegistro(dia, mes);
        if (r != null) {
            r.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }
}