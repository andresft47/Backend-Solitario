/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Strategy;

import AbstractFactory.Carta;
import java.util.List;
// =============================================================================
// INTERFACE STRATEGY
// =============================================================================

/**
 * Interfaz Strategy que define el comportamiento variable del juego de
 * Solitario Implementa el patrón Strategy GoF para encapsular diferentes
 * algoritmos de juego y hacer que sean intercambiables en tiempo de ejecución
 */
public interface EstrategiaJuego {

    // Define si una carta puede colocarse en una columna destino
    public boolean puedeColocarCarta(Carta carta, List<Carta> destino);

    // Especifica cuántas cartas pasar del mazo al descarte
    public int getCartasAPasar();

    // Retorna nombre descriptivo de la estrategia
    public String getNombre();

    // Define si permite mover múltiples cartas en secuencia
    public boolean permiteMoverMultiplesCartas();

}
