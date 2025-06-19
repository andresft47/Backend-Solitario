/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AbstractFactory;

import java.awt.Color;

// ============================================================================
// PRODUCTO ABSTRACTO
// ============================================================================

// Define la interfaz común para todos los productos (cartas)

public abstract class Carta {
    protected int valor;
    protected int palo;
    protected boolean volteada;
    
    //Cada carta conoce su factory creadora
    protected CartaAbstractFactory factory;
    
    public Carta(int valor, int palo, CartaAbstractFactory factory) {
        this.valor = valor;
        this.palo = palo;
        this.volteada = false;
        this.factory = factory;
    }
    
    public void voltear() {
        volteada = !volteada;
    }
    
    public boolean estaVolteada() {
        return volteada;
    }
    
    public int getValor() {
        return valor;
    }
    
    public int getPalo() {
        return palo;
    }
    
    // MÉTODOS ABSTRACTOS: Deben ser implementados por productos concretos
    public abstract String getSimboloPalo();
    public abstract String getNombreValor();
    public abstract Color getColorPalo();
    public abstract boolean puedeColocarseEn(Carta otraCarta);
    public abstract boolean esSiguienteEnSecuencia(Carta otraCarta);
}
