/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Iterator;

import AbstractFactory.Carta;
// =============================================================================
// INTERFAZ ITERADOR
// =============================================================================

/**
 * Interfaz que define el comportamiento básico de un Iterator para cartas
 * Implementa el patrón Iterator GoF para proporcionar acceso secuencial a
 * elementos de una colección sin exponer su representación interna
 */
public interface CartaIterator {

    // Verifica si hay más elementos para iterar
    public boolean hasNext();

    // Devuelve el siguiente elemento en la secuencia
    public Carta next();

    // Reinicia el iterador al principio de la colección
    public void reset();

}
