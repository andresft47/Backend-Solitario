/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Iterator;

import AbstractFactory.Carta;
import java.util.List;
import java.util.NoSuchElementException;
// =============================================================================
// CONCRETE ITERADOR
// =============================================================================
/**
 * Iterator concreto que recorre todas las cartas del tableau Implementa el
 * patrón Iterator para navegar por una estructura bidimensional
 */
public class TableauIterator implements CartaIterator {

    private List<List<Carta>> tableau;
    private int columnaActual = 0;
    private int cartaActual = 0;

    /**
     * Constructor que recibe la estructura del tableau
     * @param tableau estructura bidimensional de cartas
     */
    public TableauIterator(List<List<Carta>> tableau) {
        this.tableau = tableau;
    }

    /**
     * Verifica si hay más cartas para iterar en toda la estructura
     * @return true si hay más cartas disponibles
     */
    @Override
    public boolean hasNext() {
        while (columnaActual < tableau.size()) {
            if (cartaActual < tableau.get(columnaActual).size()) {
                return true;
            }
            columnaActual++;
            cartaActual = 0;
        }
        return false;
    }

    /**
     * Devuelve la siguiente carta en la secuencia
     * @return la siguiente carta del tableau
     */
    @Override
    public Carta next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Carta carta = tableau.get(columnaActual).get(cartaActual);
        cartaActual++;
        return carta;
    }

    /**
     * Reinicia el iterator al principio del tableau
     */
    @Override
    public void reset() {
        columnaActual = 0; // Vuelve a la primera columna
        cartaActual = 0; // Vuelve a la primera carta
    }
}
