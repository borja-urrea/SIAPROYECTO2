package siaproyecto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fecha {
    private int dia;   
    private int mes;

    public Fecha(int dia, int mes){
        validar(dia, mes);
        this.dia = dia;
        this.mes = mes; 
    }

    public int getDia() { return dia; }
    public void setDia(int dia) { 
        validar(dia, mes);
        this.dia = dia; 
    }
    public int getMes() { return mes; }
    public void setMes(int mes) { 
        validar(dia, mes);
        this.mes = mes; 
    }

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
