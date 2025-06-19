/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Bridge;

// ============================================================================
// IMPLEMENTACION
// ============================================================================
/**
 * Define el contrato para todos los algoritmos de cálculo 
 * de puntuación del juego de Solitario.
 */
public interface PuntuacionImpl {
    
    // Esta es la jerarquía de implementación que será utilizada por la abstracción
    public int calcularPuntos(int movimientos, long tiempo, int cartasEnFundacion);
    
    // Método adicional para obtener información descriptiva de la implementación
    public String getDescripcion();

}
