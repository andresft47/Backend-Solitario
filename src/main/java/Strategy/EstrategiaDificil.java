/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Strategy;

import AbstractFactory.Carta;
import java.util.List;

// =============================================================================
// CONCRETE STRATEGY
// =============================================================================

/**
 * Estrategia concreta para modo difícil
 * Implementa reglas más restrictivas
 */
public class EstrategiaDificil implements EstrategiaJuego {
    
    @Override
    public boolean puedeColocarCarta(Carta carta, List<Carta> destino) {
        // Si columna vacía, solo acepta Rey (valor 12)
        if (destino.isEmpty()) {
            return carta.getValor() == 12; // Solo Rey
        }
        
        // Obtiene carta superior de la columna destino
        Carta superior = destino.get(destino.size() - 1);
        // Valida: colores diferentes Y valor descendente consecutivo
        return carta.getColorPalo() != superior.getColorPalo() && 
               carta.getValor() == superior.getValor() - 1;
    }
    
    // Pasa 3 cartas del mazo (más difícil)
    @Override
    public int getCartasAPasar() {
        return 3;
    }
    
    // Identificador
    @Override
    public String getNombre() {
        return "Difícil";
    }
    
    // Solo una carta a la vez (restricción adicional)
    @Override
    public boolean permiteMoverMultiplesCartas() {
        return false;
    }
}
