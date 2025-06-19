/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Iterator;

import AbstractFactory.Carta;
import java.util.NoSuchElementException;
// =============================================================================
// CONCRETE ITERADOR
// =============================================================================
/**
 * Implementación concreta del Iterator que filtra solo cartas volteadas
 * Utiliza el patrón Decorator para agregar funcionalidad de filtrado
 * a un iterator base existente
 */
public class CartasVolteadasIterator implements CartaIterator {
    
    private CartaIterator baseIterator;
    private Carta siguienteCarta;
    
    /**
     * Constructor que recibe un iterator base y lo decora
     * @param baseIterator Iterator base a decorar
     */
    public CartasVolteadasIterator(CartaIterator baseIterator) {
        this.baseIterator = baseIterator;
        encontrarSiguiente();
    }
    
    /**
     * Método privado que busca la siguiente carta volteada
     * Implementa el algoritmo de filtrado específico
     */
    private void encontrarSiguiente() {
        siguienteCarta = null;
        while (baseIterator.hasNext()) {
            Carta carta = baseIterator.next();
            if (carta.estaVolteada()) {
                siguienteCarta = carta;
                break;
            }
        }
    }
    
    /**
     * Implementación de hasNext() verifica si hay una carta en cache
     * @return true si hay una siguiente carta volteada
     */
    @Override
    public boolean hasNext() {
        return siguienteCarta != null;
    }
    
    /**
     * Implementación de next() que devuelve la carta cacheada
     * @return la siguiente carta volteada
     */
    @Override
    public Carta next() {
        if (siguienteCarta == null) {
            throw new NoSuchElementException();
        }
        
        Carta actual = siguienteCarta;
        encontrarSiguiente();
        return actual;
    }
    
    /**
     * Reinicia tanto el iterator base como el cache
     */
    @Override
    public void reset() {
        baseIterator.reset(); // Reinicia el iterator base
        encontrarSiguiente(); // Busca la primera carta volteada desde el inicio
    }
}
