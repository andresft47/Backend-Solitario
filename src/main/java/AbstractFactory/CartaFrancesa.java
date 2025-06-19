/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AbstractFactory;

import java.awt.Color;

// ================================================================================================
// PRODUCTO CONCRETO
// ================================================================================================
// Implementación específica para cartas francesas
public class CartaFrancesa extends Carta {
    
    
    public CartaFrancesa(int valor, int palo) {
        super(valor, palo, null);// PRODUCTO CONCRETO: No necesita referencia al factory
    }
    
    //Símbolos de cartas francesas
    @Override
    public String getSimboloPalo() {
        String[] simbolos = {"♠", "♥", "♦", "♣"};// Picas, Corazones, Diamantes, Tréboles
        return simbolos[palo];
    }
    
    //Valores de cartas francesas
    @Override
    public String getNombreValor() {
        String[] nombres = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        return nombres[valor];
    }
    
    //Colores para cartas francesas
    @Override
    public Color getColorPalo() {
        return (palo == 1 || palo == 2) ? Color.RED : Color.BLACK;
    }
    
    //Reglas del solitario para cartas francesas
    @Override
    public boolean puedeColocarseEn(Carta otraCarta) {
        if (otraCarta == null) return valor == 12; // Solo Rey puede ir en espacio vacío
        return getColorPalo() != otraCarta.getColorPalo() && valor == otraCarta.getValor() - 1;
    }
    
    @Override
    public boolean esSiguienteEnSecuencia(Carta otraCarta) {
        return palo == otraCarta.getPalo() && valor == otraCarta.getValor() + 1;
    }
}
