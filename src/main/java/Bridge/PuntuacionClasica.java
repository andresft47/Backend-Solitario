/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bridge;

// ============================================================================
// IMPLEMENTACION CONCRETA
// ============================================================================
/**
 * El algoritmo de puntuación tradicional del Solitario das 100 puntos por cada carta en las 
 * fundaciones pero pierdes 2 puntos por cada movimiento que hagas.
 */
public class PuntuacionClasica implements PuntuacionImpl {
    
    // Implementación concreta con algoritmo de puntuación clásico
    @Override
    public int calcularPuntos(int movimientos, long tiempo, int cartasEnFundacion) {
        return cartasEnFundacion * 100 - movimientos * 2;
    }
    
    @Override
    public String getDescripcion() {
        return "Clásica: +100 por carta en fundación, -2 por movimiento";
    }
}
