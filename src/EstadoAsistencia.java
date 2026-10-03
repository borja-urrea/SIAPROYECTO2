package siaproyecto;

/**
 * Clase utilitaria que define las constantes y validaciones para los estados de asistencia.
 * Centraliza la codificación numérica de los estados para evitar el uso de "números mágicos"
 * en el resto del sistema.
 */
public class EstadoAsistencia {
    
    /** Representa a un alumno que asistió normalmente a clases. */
    public static final int PRESENTE = 1;
    
    /** Representa una inasistencia regular sin justificación extraordinaria. */
    public static final int INASISTENCIA_REGULAR = 2;
    
    /** Representa una inasistencia debidamente justificada (médica, fuerza mayor, etc.). */
    public static final int INASISTENCIA_EXTRAORDINARIA = 3;
    
    /** Representa a un alumno que asistió pero se retiró antes de finalizar la jornada. */
    public static final int SALIDA_TEMPRANA = 4;

    /**
     * Constructor privado para evitar la instanciación de esta clase,
     * ya que solo contiene constantes y métodos estáticos.
     */
    private EstadoAsistencia() {
    }

    /**
     * Valida si un código numérico corresponde a un estado de asistencia permitido.
     * 
     * @param estado Código numérico a evaluar.
     * @return true si el estado está dentro de los rangos definidos, false en caso contrario.
     */
    public static boolean esValido(int estado) {
        return estado == PRESENTE
                || estado == INASISTENCIA_REGULAR
                || estado == INASISTENCIA_EXTRAORDINARIA
                || estado == SALIDA_TEMPRANA;
    }

    /**
     * Convierte el código numérico de estado en una descripción de texto legible.
     * 
     * @param estado Código numérico del estado.
     * @return Cadena de texto descriptiva del estado correspondiente.
     */
    public static String describir(int estado) {
        switch (estado) {
            case PRESENTE:
                return "Presente";
            case INASISTENCIA_REGULAR:
                return "Falta";
            case INASISTENCIA_EXTRAORDINARIA:
                return "Falta extraordinaria";
            case SALIDA_TEMPRANA:
                return "Salida temprana";
            default:
                return "Desconocido";
        }
    }
}