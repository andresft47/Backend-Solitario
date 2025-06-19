/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Builder;

import Iterator.CartaCollection;
import AbstractFactory.Carta;
import AbstractFactory.CartaAbstractFactory;
import Iterator.CartaIterator;
import Iterator.CartasVolteadasIterator;
import Iterator.TableauIterator;
import java.util.List;

// =============================================================================
// CLASE TABLEROSOLITARIO (PRODUCTO)
// =============================================================================
/**
 * Producto final del patrón Builder. Representa el estado completo de un juego
 * de Solitario. Implementa CartaCollection para proporcionar capacidades de
 * iteración. Es inmutable una vez construido (solo getters, no setters).
 */
public class TableroSolitario implements CartaCollection {

    private List<Carta> mazo;
    private List<Carta> descarte;
    private List<List<Carta>> fundaciones;
    private List<List<Carta>> tableau;
    private CartaAbstractFactory factory;

    /**
     * Constructor del producto. Solo el Builder puede crear instancias. Recibe
     * todos los componentes ya inicializados.
     */
    public TableroSolitario(List<Carta> mazo, List<Carta> descarte,
            List<List<Carta>> fundaciones, List<List<Carta>> tableau,
            CartaAbstractFactory factory) {
        this.mazo = mazo;
        this.descarte = descarte;
        this.fundaciones = fundaciones;
        this.tableau = tableau;
        this.factory = factory;
    }

    @Override
    public CartaIterator createIterator() {
        return new TableauIterator(tableau);
    }

    public CartaIterator createCartasVolteadasIterator() {
        return new CartasVolteadasIterator(createIterator());
    }

    @Override
    public void addCarta(Carta carta) {
        if (!tableau.isEmpty()) {
            tableau.get(0).add(carta);
        }
    }

    /**
     * Implementación de CartaCollection: obtiene carta por índice Utiliza el
     * iterador para recorrer hasta la posición deseada
     */
    @Override
    public Carta getCarta(int index) {
        // Usa el iterador para encontrar la carta en la posición especificada
        CartaIterator it = createIterator();
        int i = 0;
        while (it.hasNext() && i < index) {
            it.next();
            i++;
        }
        return it.hasNext() ? it.next() : null;
    }

    @Override
    public int size() {
        int total = 0;
        for (List<Carta> columna : tableau) {
            total += columna.size();
        }
        return total;
    }

    // En la clase TableroSolitario, agregar:
    public CartaIterator getTableauIterator() {
        return new TableauIterator(this.tableau);
    }

    public CartaIterator getCartasVolteadasIterator() {
        return new CartasVolteadasIterator(new TableauIterator(this.tableau));
    }

    public List<Carta> getMazo() {
        return mazo;
    }

    public List<Carta> getDescarte() {
        return descarte;
    }

    public List<List<Carta>> getFundaciones() {
        return fundaciones;
    }

    public List<List<Carta>> getTableau() {
        return tableau;
    }

    public CartaAbstractFactory getFactory() {
        return factory;
    }

}
