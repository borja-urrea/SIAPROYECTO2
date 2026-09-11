package siaproyecto;
public class EstadoAsistencia {
    public static final int PRESENTE = 1;
    public static final int INASISTENCIA_REGULAR = 2;
    public static final int INASISTENCIA_EXTRAORDINARIA = 3;
    public static final int SALIDA_TEMPRANA = 4;

    private EstadoAsistencia() {
    }

    public static boolean esValido(int estado) {
        return estado == PRESENTE
                || estado == INASISTENCIA_REGULAR
                || estado == INASISTENCIA_EXTRAORDINARIA
                || estado == SALIDA_TEMPRANA;
    }

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
