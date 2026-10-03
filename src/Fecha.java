package siaproyecto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa una fecha en el calendario (día y mes) con reglas de validación
 * que garantizan la consistencia temporal de los registros.
 */
public class Fecha {
    private int dia;   
    private int mes;

    /**
     * Instancia una nueva fecha sometiéndola a comprobación de validez.
     * 
     * @param dia Día del mes.
     * @param mes Mes del año (1 a 12).
     */
    public Fecha(int dia, int mes){
        validar(dia, mes);
        this.dia = dia;
        this.mes = mes; 
    }

    /**
     * @return El día configurado en la fecha.
     */
    public int getDia() { return dia; }

    /**
     * Modifica el día, validando previamente que sea correcto respecto al mes actual.
     * 
     * @param dia El nuevo día a asignar.
     */
    public void setDia(int dia) { 
        validar(dia, mes);
        this.dia = dia; 
    }

    /**
     * @return El mes configurado en la fecha.
     */
    public int getMes() { return mes; }

    /**
     * Modifica el mes, validando que el día actualmente configurado siga siendo válido.
     * 
     * @param mes El nuevo mes a asignar.
     */
    public void setMes(int mes) { 
        validar(dia, mes);
        this.mes = mes; 
    }

    /**
     * Evalúa que el mes se encuentre en el rango permitido (1-12) y que 
     * el día corresponda al límite real de días de dicho mes.
     * 
     * @param dia Día a evaluar.
     * @param mes Mes a evaluar.
     * @throws IllegalArgumentException Si el mes o el día se encuentran fuera de rango.
     */
    private static void validar(int dia, int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }

        int diasDelMes;
        switch (mes) {
            case 2:
                diasDelMes = 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                diasDelMes = 30;
                break;
            default:
                diasDelMes = 31;
        }

        if (dia < 1 || dia > diasDelMes) {
            throw new IllegalArgumentException("El día no es válido para el mes indicado.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fecha fecha = (Fecha) obj;
        return dia == fecha.dia && mes == fecha.mes;
    }
    
    @Override
    public String toString(){ 
        return String.format("%02d/%02d", dia, mes); 
    }
}