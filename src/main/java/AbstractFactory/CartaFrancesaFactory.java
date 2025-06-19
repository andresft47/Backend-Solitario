/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AbstractFactory;

import java.awt.Color;

// ================================================================================================
// FACTORY CONCRETO
// ================================================================================================

// Implementa la creación de la familia de productos "Cartas Francesas"
public class CartaFrancesaFactory implements CartaAbstractFactory {
    
    //Constantes para la familia de cartas francesas
    private static final String[] SIMBOLOS_PALOS = {"♠", "♥", "♦", "♣"};
    private static final String[] NOMBRES_VALORES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    
    //Crea productos específicos (CartaFrancesa)
    @Override
    public Carta crearCarta(int valor, int palo) {
        return new CartaFrancesa(valor, palo);// RETORNA PRODUCTO CONCRETO
    }
    
    @Override
    public String[] getSimbolosPalos() {
        return SIMBOLOS_PALOS.clone();
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
        return 13; // ESPECÍFICO DE CARTAS FRANCESAS: 13 valores (A-K)
    }
    
    @Override
    public int getNumeroPalos() {
        return 4;// ESPECÍFICO DE CARTAS FRANCESAS: 4 palos
    }
}
