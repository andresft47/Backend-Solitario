/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Builder;

// =============================================================================
// INTERFAZ BUILDER
// =============================================================================
/**
 * Interfaz del patrón Builder que define los pasos para construir un
 * TableroSolitario. Cada método retorna Builder para permitir method chaining
 * (fluent interface).
 */
public interface Builder {

    // Reinicia el builder a su estado inicial, limpia todas las estructuras
    public Builder reset();

    // Crea una baraja completa mezclada con todas las cartas
    public Builder crearBaraja();

    // Inicializa las 4 pilas de fundaciones (una por palo)
    public Builder inicializarFundaciones();

    // Inicializa las 7 columnas del tableau (área de juego principal) 
    public Builder inicializarTableau();

    // Reparte las cartas del mazo al tableau según las reglas del Solitario
    public Builder repartirCartasTableau();

    // Construye y retorna el objeto TableroSolitario final
    public TableroSolitario build();

}
