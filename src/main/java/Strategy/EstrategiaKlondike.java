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
 * Estrategia concreta para Klondike clásico
 * Implementa reglas estándar del Solitario Klondike
 */
public class EstrategiaKlondike implements EstrategiaJuego {
    @Override
    public boolean puedeColocarCarta(Carta carta, List<Carta> destino) {
        if (destino.isEmpty()) {
            return carta.getValor() == 12; // Rey
        }
        
        Carta superior = destino.get(destino.size() - 1);
        return carta.getColorPalo() != superior.getColorPalo() && 
               carta.getValor() == superior.getValor() - 1;
    }
    
    // Klondike estándar pasa 3 cartas
    @Override
    public int getCartasAPasar() {
        return 3;
    }
    
    // Nombre del modo clásico
    @Override
    public String getNombre() {
        return "Klondike";
    }
    
    // Klondike permite mover secuencias
    @Override
    public boolean permiteMoverMultiplesCartas() {
        return true;
    }
}
