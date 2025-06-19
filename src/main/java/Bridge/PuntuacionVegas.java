/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bridge;

// ============================================================================
// IMPLEMENTACION CONCRETA
// ============================================================================

/**
 * Implementa el sistema de puntuación estilo casino de 
 * Las Vegas - pagas $52 por jugar y ganas 
 * $5 por cada carta que logres colocar en las fundaciones.
 */
public class PuntuacionVegas implements PuntuacionImpl {
    
    @Override
    public int calcularPuntos(int movimientos, long tiempo, int cartasEnFundacion) {
        return cartasEnFundacion * 5 - 52; // $5 por carta, $52 de costo inicial
    }
    
    @Override
    public String getDescripcion() {
        return "Vegas: $5 por carta en fundación, -$52 inicial";
    }
}
