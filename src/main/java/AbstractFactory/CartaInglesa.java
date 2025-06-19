/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AbstractFactory;

import java.awt.Color;

// ================================================================================================
// PRODUCTO CONCRETO 2
// ================================================================================================

// Implementación específica para cartas inglesas (similar pero podría tener diferencias)
public class CartaInglesa extends Carta {
    public CartaInglesa(int valor, int palo) {
        super(valor, palo, null);
    }
    
    @Override
    public String getSimboloPalo() {
        String[] simbolos = {"♠", "♥", "♦", "♣"};
        return simbolos[palo];
    }
    
    @Override
    public String getNombreValor() {
        String[] nombres = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        return nombres[valor];
    }
    
    @Override
    public Color getColorPalo() {
        return (palo == 1 || palo == 2) ? Color.RED : Color.BLACK;
    }
    
    @Override
    public boolean puedeColocarseEn(Carta otraCarta) {
        if (otraCarta == null) return valor == 12;
        return getColorPalo() != otraCarta.getColorPalo() && valor == otraCarta.getValor() - 1;
    }
    
    @Override
    public boolean esSiguienteEnSecuencia(Carta otraCarta) {
        return palo == otraCarta.getPalo() && valor == otraCarta.getValor() + 1;
    }
}
