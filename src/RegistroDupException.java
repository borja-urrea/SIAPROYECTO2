package siaproyecto;

/**
 * Excepción personalizada que se lanza cuando se intenta ingresar
 * un registro de asistencia en una fecha que ya fue registrada previamente para ese estudiante.
 */
public class RegistroDupException extends Exception {
    
    /**
     * Constructor de la excepción.
     * @param mensaje Descripción específica del error ocurrido.
     */
    public RegistroDupException(String mensaje) {
        super(mensaje);
    }
}