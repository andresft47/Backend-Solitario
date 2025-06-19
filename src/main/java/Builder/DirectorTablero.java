/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Builder;

// =============================================================================
// DIRECTOR
// =============================================================================
/**
 * Director del patrón Builder. Conoce la secuencia correcta de pasos para
 * construir diferentes tipos de tableros de Solitario.
 */
public class DirectorTablero {

    // Referencia al builder concreto que realizará la construcción
    private Builder builder;

    /**
     * Constructor que recibe el builder a utilizar
     * @param builder Builder concreto para construir el tablero
     */
    public DirectorTablero(Builder builder) {
        this.builder = builder;
    }

    /**
     * Permite cambiar el builder en tiempo de ejecución
     * @param builder Nuevo builder a utilizar
     */
    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    /**
     * Método que define el algoritmo estándar para construir un tablero de Solitario. 
     * Ejecuta los pasos en el orden correcto usando method chaining.
     *
     * @return TableroSolitario completamente construido y listo para jugar
     */
    public TableroSolitario construirTableroEstandar() {
        return builder
                .reset()
                .crearBaraja()
                .inicializarFundaciones()
                .inicializarTableau()
                .repartirCartasTableau()
                .build();
    }

}
