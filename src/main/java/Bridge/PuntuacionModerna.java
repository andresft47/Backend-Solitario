/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bridge;

// ============================================================================
// IMPLEMENTACION CONCRETA
// ============================================================================

/**
 * Sistema de puntuación contemporáneo que premia la velocidad y eficiencia 
 * da muchos puntos por carta (150), bonifica por rapidez, 
 * pero penaliza cada movimiento
 */

public class PuntuacionModerna implements PuntuacionImpl {
    @Override
    public int calcularPuntos(int movimientos, long tiempo, int cartasEnFundacion) {
        int puntosTiempo = Math.max(0, 10000 - (int)(tiempo / 1000));
        return cartasEnFundacion * 150 + puntosTiempo - movimientos;
    }
    
    @Override
    public String getDescripcion() {
        return "Moderna: +150 por carta, bonus por tiempo, -1 por movimiento";
    }
}
