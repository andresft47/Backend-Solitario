/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AbstractFactory;

import java.awt.Color;

// ================================================================================================
// FACTORY CONCRETO 2
// ================================================================================================

// Implementa la creación de la familia de productos "Cartas Inglesas"
public class CartaInglesaFactory implements CartaAbstractFactory {
    private static final String[] SIMBOLOS_PALOS = {"♠", "♥", "♦", "♣"};
    private static final String[] NOMBRES_VALORES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    
    @Override
    public Carta crearCarta(int valor, int palo) {
        return new CartaInglesa(valor, palo);// RETORNA PRODUCTO CONCRETO INGLÉS
    }
    
    @Override
    public String[] getSimbolosPalos() {
        return SIMBOLOS_PALOS.clone();// Devuelve copia para evitar modificaciones
    }
    
    @Override
    public String[] getNombresValores() {
        return NOMBRES_VALORES.clone();
    }
    
    @Override
    public Color getColorPalo(int palo) {
        return (palo == 1 || palo == 2) ? Color.RED : Color.BLACK;
    }
    
    @Override
    public int getNumeroValores() {
        return 13;
    }
    
    @Override
    public int getNumeroPalos() {
        return 4;
    }
}
