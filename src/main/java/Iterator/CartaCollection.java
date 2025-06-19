/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Iterator;

import AbstractFactory.Carta;
// =============================================================================
// INTERFAZ ITERABLE COLLECTION
// =============================================================================

/**
 * Interfaz que define las operaciones básicas para una colección de cartas.
 * Implementa el patrón Iterator para permitir recorrer las cartas.
 */
public interface CartaCollection {
    // Método factory para crear un iterador específico de esta colección
    public CartaIterator createIterator();

    // Añadir una carta a la colección
    public void addCarta(Carta carta);

    // Obtener una carta por su índice
    public Carta getCarta(int index);

    // Obtener el tamaño total de la colección
    public int size();

}
