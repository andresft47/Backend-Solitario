/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AbstractFactory;

import java.awt.Color;

// ============================================================================
// FACTORY ABSTRACTA
// ============================================================================
// Define la interfaz para crear familias de productos relacionados
public interface CartaAbstractFactory {

    // MÉTODO FACTORY: Crear el producto principal (Carta)
    public abstract Carta crearCarta(int valor, int palo);

    // MÉTODOS AUXILIARES: Para obtener información específica de cada familia de cartas
    public abstract String[] getSimbolosPalos();

    public abstract String[] getNombresValores();

    public abstract Color getColorPalo(int palo);

    public abstract int getNumeroValores();

    public abstract int getNumeroPalos();

}
