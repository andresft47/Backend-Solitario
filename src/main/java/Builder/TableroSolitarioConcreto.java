/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Builder;

import AbstractFactory.Carta;
import AbstractFactory.CartaAbstractFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// =============================================================================
// BUILDER CONCRETO
// =============================================================================
/**
 * Implementación concreta del Builder para construir un TableroSolitario.
 * Encapsula toda la lógica compleja de construcción paso a paso.
 */
public class TableroSolitarioConcreto implements Builder {

    private List<Carta> mazo;
    private List<Carta> descarte;
    private List<List<Carta>> fundaciones;
    private List<List<Carta>> tableau;
    private CartaAbstractFactory factory;

    /**
     * Constructor del builder. Recibe la factory para crear cartas.
     *
     * @param factory Factory específica para el tipo de baraja deseado
     */
    public TableroSolitarioConcreto(CartaAbstractFactory factory) {
        this.factory = factory;
        reset(); // Inicializa todas las estructuras
    }

    /**
     * Reinicia el builder limpiando todas las estructuras. Permite reutilizar
     * el mismo builder para múltiples construcciones.
     *
     * @return
     */
    @Override
    public Builder reset() {
        mazo = new ArrayList<>();
        descarte = new ArrayList<>();
        fundaciones = new ArrayList<>();
        tableau = new ArrayList<>();
        return this;
    }

    /**
     * Crea una baraja completa usando la factory. Genera todas las
     * combinaciones de valores y palos, luego las mezcla.
     *
     * @return
     */
    @Override
    public Builder crearBaraja() {
        List<Carta> todasLasCartas = new ArrayList<>();

        for (int palo = 0; palo < factory.getNumeroPalos(); palo++) {
            for (int valor = 0; valor < factory.getNumeroValores(); valor++) {
                todasLasCartas.add(factory.crearCarta(valor, palo));
            }
        }

        Collections.shuffle(todasLasCartas);
        mazo.addAll(todasLasCartas);

        return this;
    }

    /**
     * Inicializa las 4 pilas de fundaciones (una por cada palo). Las
     * fundaciones empiezan vacías y se van llenando durante el juego.
     *
     * @return
     */
    @Override
    public Builder inicializarFundaciones() {
        for (int i = 0; i < factory.getNumeroPalos(); i++) {
            fundaciones.add(new ArrayList<>());
        }
        return this;
    }

    /**
     * Inicializa las 7 columnas del tableau (área principal de juego). Cada
     * columna empezará vacía y se llenará en el siguiente paso.
     *
     * @return
     */
    @Override
    public Builder inicializarTableau() {
        for (int i = 0; i < 7; i++) {
            tableau.add(new ArrayList<>());
        }
        return this;
    }

    /**
     * Reparte las cartas del mazo al tableau
     *
     * @return
     */
    @Override
    public Builder repartirCartasTableau() {
        for (int col = 0; col < 7; col++) {
            for (int fila = 0; fila <= col; fila++) {
                if (!mazo.isEmpty()) {
                    Carta carta = mazo.remove(mazo.size() - 1);
                    if (fila == col) {
                        carta.voltear();
                    }
                    tableau.get(col).add(carta);
                }
            }
        }
        return this;
    }

    /**
     * Construye y retorna el producto final. Crea la instancia de
     * TableroSolitario con todos los componentes preparados.
     * @return 
     */
    @Override
    public TableroSolitario build() {
        return new TableroSolitario(mazo, descarte, fundaciones, tableau, factory);
    }

}
