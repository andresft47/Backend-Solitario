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
 * Estrategia concreta para modo fácil Implementa reglas más permisivas
 */
public class EstrategiaFacil implements EstrategiaJuego {

    @Override
    public boolean puedeColocarCarta(Carta carta, List<Carta> destino) {
        if (destino.isEmpty()) {
            return carta.getValor() == 12; // Solo Rey
        }
        Carta superior = destino.get(destino.size() - 1);
        return carta.getColorPalo() != superior.getColorPalo()
                && carta.getValor() == superior.getValor() - 1;
    }

    // Solo 1 carta del mazo (más fácil)
    @Override
    public int getCartasAPasar() {
        return 1;
    }

    // Identificador
    @Override
    public String getNombre() {
        return "Fácil";
    }

    // Permite mover secuencias de cartas
    @Override
    public boolean permiteMoverMultiplesCartas() {
        return true;
    }
}
