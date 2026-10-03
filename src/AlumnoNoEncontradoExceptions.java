package siaproyecto;

/**
 * Excepción personalizada orientada a notificar fallos en las búsquedas
 * cuando un RUT solicitado no figura en la colección principal del sistema.
 */
public class AlumnoNoEncontradoExceptions extends Exception {
    
    /**
     * Constructor de la excepción.
     * @param mensaje Descripción específica del error ocurrido.
     */
    public AlumnoNoEncontradoExceptions(String mensaje) {
        super(mensaje);
    }
}