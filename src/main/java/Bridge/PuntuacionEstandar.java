/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bridge;

// ============================================================================
// ABSTRACCION REFINADA
// ============================================================================
/**
 * Sistema de puntuación básico que simplemente toma el resultado 
 * del algoritmo de cálculo y se asegura de que nunca sea negativo 
 * (mínimo 0 puntos)
 */
public class PuntuacionEstandar extends SistemaPuntuacion {
 
    // Constructor que pasa la implementación a la clase padre
    // Establece la conexión entre esta abstracción refinada y la implementación
    public PuntuacionEstandar(PuntuacionImpl implementacion) {
        super(implementacion);
    }
    
    // Implementa el método abstracto agregando comportamiento específico
    @Override
    public int calcularPuntuacionFinal(int movimientos, long tiempo, int cartasEnFundacion) {
        return Math.max(0, implementacion.calcularPuntos(movimientos, tiempo, cartasEnFundacion));
    }
    
    // Combina información de la abstracción con la implementación
    @Override
    public String mostrarEstadisticas() {
        return "Puntuación Estándar - " + implementacion.getDescripcion();
    }
}
