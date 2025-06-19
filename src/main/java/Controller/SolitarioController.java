/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Bridge.*;
import AbstractFactory.*;
import Builder.*;
import Strategy.*;
import Iterator.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Controlador principal del juego de Solitario
 * Implementa múltiples patrones de diseño:
 * - Abstract Factory (CartaAbstractFactory)
 * - Strategy (EstrategiaJuego)
 * - Decorator (SistemaPuntuacion)
 * - Builder (DirectorTablero)
 * - Iterator (CartaIterator)
 */
public class SolitarioController {

    // ATRIBUTOS PRINCIPALES - Aplicación de patrones de diseño
    private TableroSolitario tablero;              // Modelo del tablero de juego
    private CartaAbstractFactory factory;          // PATRÓN ABSTRACT FACTORY: Crea diferentes tipos de cartas
    private EstrategiaJuego estrategiaActual;      // PATRÓN STRATEGY: Define reglas del juego (Klondike, Spider, etc.)
    private SistemaPuntuacion sistemaPuntuacion;   // PATRÓN DECORATOR: Sistema de puntuación decorable
    private DirectorTablero director;              // PATRÓN BUILDER: Construye el tablero paso a paso
    private int movimientos = 0;                   // Contador de movimientos del jugador
    private long tiempoInicio;                     // Tiempo de inicio para calcular duración

    // VARIABLES PARA MANEJO DE SELECCIÓN MÚLTIPLE
    private List<Carta> cartasSeleccionadas;       // Lista de cartas actualmente seleccionadas
    private int columnaOrigen;                     // Columna de donde se seleccionaron las cartas
    private int indiceInicioSeleccion;             // Índice donde comenzó la selección

    /**
     * Constructor principal con inyección de dependencias
     * Aplica el principio de Inversión de Dependencias (SOLID)
     * @param factory
     * @param estrategia
     * @param sistema
     */
    public SolitarioController(CartaAbstractFactory factory, EstrategiaJuego estrategia, SistemaPuntuacion sistema) {
        this.factory = factory;                     // Inyecta la fábrica de cartas
        this.estrategiaActual = estrategia;         // Inyecta la estrategia de juego
        this.sistemaPuntuacion = sistema;           // Inyecta el sistema de puntuación
        this.director = new DirectorTablero(new TableroSolitarioConcreto(factory)); // PATRÓN BUILDER

        // Inicialización de variables de selección
        cartasSeleccionadas = new ArrayList<>();
        columnaOrigen = -1;                         // -1 indica que no hay selección
        indiceInicioSeleccion = -1;

        tiempoInicio = System.currentTimeMillis();  // Registra el tiempo de inicio
        inicializarJuego();                         // Configura el juego inicial

        // Validación de integridad del juego
        if (!validarEstadoJuego()) {
            throw new IllegalStateException("Error: El juego no se inicializó correctamente");
        }
    }

    /**
     * Constructor por defecto - Aplica configuración estándar
     * Usa valores por defecto para demostrar el patrón
     */
    public SolitarioController() {
        this(new CartaFrancesaFactory(),            // Factory por defecto: cartas francesas
                new EstrategiaKlondike(),           // Estrategia por defecto: Klondike
                new PuntuacionEstandar(new PuntuacionClasica())); // Decorator: puntuación estándar decorando clásica
    }

    /**
     * Inicializa o reinicia el estado del juego
     * Aplica el patrón Template Method implícitamente
     */
    private void inicializarJuego() {
        tablero = director.construirTableroEstandar(); // PATRÓN BUILDER: construye el tablero
        movimientos = 0;                            // Resetea contador de movimientos
        tiempoInicio = System.currentTimeMillis();  // Reinicia el cronómetro
        
        // Manejo polimórfico del sistema de puntuación
        if (sistemaPuntuacion instanceof PuntuacionConTiempo) {
            ((PuntuacionConTiempo) sistemaPuntuacion).reiniciarTiempo();
        }
    }

    /**
     * Verifica si un conjunto de cartas puede moverse en bloque
     * Implementa lógica específica para movimientos múltiples
     */
    private boolean puedenMoverseEnConjunto(List<Carta> columna, int indiceInicio) {
        // Si solo hay una carta o es la última, siempre se puede mover
        if (indiceInicio >= columna.size() - 1) {
            return true;
        }

        // Verifica secuencia descendente y colores alternados
        for (int i = indiceInicio; i < columna.size() - 1; i++) {
            Carta actual = columna.get(i);          // Carta actual en la secuencia
            Carta siguiente = columna.get(i + 1);   // Siguiente carta en la secuencia

            // Ambas cartas deben estar volteadas (visibles)
            if (!actual.estaVolteada() || !siguiente.estaVolteada()) {
                return false;
            }

            // Verifica secuencia descendente (K, Q, J, 10...) y colores alternados
            if (actual.getValor() != siguiente.getValor() + 1
                    || actual.getColorPalo() == siguiente.getColorPalo()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene las cartas que pueden moverse desde una posición específica
     * Aplica la estrategia actual para determinar movimientos válidos
     */
    private List<Carta> obtenerCartasMovibles(List<Carta> columna, int indiceClick) {
        List<Carta> cartasMovibles = new ArrayList<>();

        // PATRÓN STRATEGY: consulta si la estrategia permite mover múltiples cartas
        if (!estrategiaActual.permiteMoverMultiplesCartas()) {
            cartasMovibles.add(columna.get(indiceClick)); // Solo una carta
            return cartasMovibles;
        }

        // Si es la última carta, solo se puede mover esa
        if (indiceClick == columna.size() - 1) {
            cartasMovibles.add(columna.get(indiceClick));
            return cartasMovibles;
        }

        // Verifica si se puede mover el conjunto completo
        if (puedenMoverseEnConjunto(columna, indiceClick)) {
            // Agrega todas las cartas desde el índice hasta el final
            for (int i = indiceClick; i < columna.size(); i++) {
                cartasMovibles.add(columna.get(i));
            }
        } else {
            // Solo puede mover la carta clickeada
            cartasMovibles.add(columna.get(indiceClick));
        }

        return cartasMovibles;
    }

    /**
     * Pasa cartas del mazo al descarte según la estrategia actual
     * PATRÓN STRATEGY: usa la estrategia para determinar cuántas cartas pasar
     * @return 
     */
    public boolean pasarCartasDelMazo() {
        // Si tanto mazo como descarte están vacíos, no hay nada que hacer
        if (tablero.getMazo().isEmpty() && tablero.getDescarte().isEmpty()) {
            return false;
        }

        // Si el mazo está vacío, recicla el descarte
        if (tablero.getMazo().isEmpty()) {
            reciclarDescarte();
            return true;
        }

        // PATRÓN STRATEGY: obtiene cuántas cartas pasar según la estrategia
        int cartasAPasar = estrategiaActual.getCartasAPasar();
        
        // Pasa las cartas del mazo al descarte
        for (int i = 0; i < cartasAPasar && !tablero.getMazo().isEmpty(); i++) {
            Carta carta = tablero.getMazo().remove(tablero.getMazo().size() - 1); // Toma la última carta
            if (!carta.estaVolteada()) {
                carta.voltear();                    // Voltea la carta para que sea visible
            }
            tablero.getDescarte().add(carta);       // La agrega al descarte
        }

        incrementarMovimientos();                   // Cuenta como un movimiento
        return true;
    }

    /**
     * Recicla todas las cartas del descarte de vuelta al mazo
     * Las cartas se voltean boca abajo y se invierten en orden
     */
    private void reciclarDescarte() {
        // Mueve todas las cartas del descarte al mazo
        while (!tablero.getDescarte().isEmpty()) {
            Carta carta = tablero.getDescarte().remove(tablero.getDescarte().size() - 1); // Última carta del descarte
            if (carta.estaVolteada()) {
                carta.voltear();                    // La voltea boca abajo
            }
            tablero.getMazo().add(carta);           // La agrega al mazo
        }
        incrementarMovimientos();                   // Cuenta como un movimiento
    }

    /**
     * Maneja la selección de cartas en el tableau
     * Permite seleccionar múltiples cartas o voltear cartas boca abajo
     * @param columna
     * @param indiceClick
     * @return 
     */
    public boolean seleccionarCartasTableau(int columna, int indiceClick) {
        // Valida que la columna esté en rango
        if (columna < 0 || columna >= tablero.getTableau().size()) {
            return false;
        }

        List<Carta> columnaCartas = tablero.getTableau().get(columna);
        
        // Valida que el índice esté en rango
        if (indiceClick < 0 || indiceClick >= columnaCartas.size()) {
            return false;
        }

        Carta carta = columnaCartas.get(indiceClick);
        
        if (carta.estaVolteada()) {
            // Si la carta está volteada, la selecciona (y posiblemente más cartas)
            cartasSeleccionadas = obtenerCartasMovibles(columnaCartas, indiceClick);
            columnaOrigen = columna;                // Recuerda de qué columna viene
            indiceInicioSeleccion = indiceClick;    // Recuerda el índice de inicio
            return true;
        } else if (indiceClick == columnaCartas.size() - 1) {
            // Si es la última carta y está boca abajo, la voltea
            carta.voltear();
            incrementarMovimientos();               // Voltear cuenta como movimiento
            return true;
        }

        return false;                               // No se pudo realizar la acción
    }

    /**
     * Selecciona la carta superior del descarte
     * Solo se puede seleccionar una carta del descarte a la vez
     * @return 
     */
    public boolean seleccionarCartaDescarte() {
        // Verifica que haya cartas en el descarte
        if (tablero.getDescarte().isEmpty()) {
            return false;
        }

        // Selecciona la última carta (la visible)
        Carta carta = tablero.getDescarte().get(tablero.getDescarte().size() - 1);
        cartasSeleccionadas.clear();                // Limpia selección anterior
        cartasSeleccionadas.add(carta);             // Agrega solo esta carta
        return true;
    }

    /**
     * Mueve la carta seleccionada a una fundación específica
     * Solo se puede mover una carta a la vez a las fundaciones
     * @param indiceFundacion
     * @return 
     */
    public boolean moverAFundacion(int indiceFundacion) {
        // Solo se puede mover una carta a la fundación
        if (cartasSeleccionadas.isEmpty() || cartasSeleccionadas.size() > 1) {
            return false;
        }

        // Valida el índice de fundación (0-3)
        if (indiceFundacion < 0 || indiceFundacion >= 4) {
            return false;
        }

        Carta carta = cartasSeleccionadas.get(0);
        List<Carta> fundacion = tablero.getFundaciones().get(indiceFundacion);

        // Verifica si la carta puede colocarse en esta fundación
        if (puedeColocarEnFundacion(carta, fundacion)) {
            moverCartaAFundacion(carta, indiceFundacion);
            cartasSeleccionadas.clear();            // Limpia la selección
            incrementarMovimientos();               // Cuenta el movimiento
            return true;
        }

        return false;
    }

    /**
     * Mueve las cartas seleccionadas a una columna del tableau
     * Puede mover una o múltiples cartas según la estrategia
     * @param columnaDestino
     * @return 
     */
    public boolean moverATableau(int columnaDestino) {
        // Debe haber cartas seleccionadas
        if (cartasSeleccionadas.isEmpty()) {
            return false;
        }

        // Valida la columna de destino
        if (columnaDestino < 0 || columnaDestino >= tablero.getTableau().size()) {
            return false;
        }

        Carta primeraCarta = cartasSeleccionadas.get(0);
        List<Carta> columna = tablero.getTableau().get(columnaDestino);

        // PATRÓN STRATEGY: verifica si la estrategia permite mover múltiples cartas
        if (cartasSeleccionadas.size() > 1 && !estrategiaActual.permiteMoverMultiplesCartas()) {
            return false;
        }

        // PATRÓN STRATEGY: usa la estrategia para validar el movimiento
        if (estrategiaActual.puedeColocarCarta(primeraCarta, columna)) {
            moverCartasATableau(cartasSeleccionadas, columnaDestino);
            cartasSeleccionadas.clear();            // Limpia la selección
            incrementarMovimientos();               // Cuenta el movimiento
            return true;
        }

        return false;
    }

    /**
     * Limpia la selección actual de cartas
     * Resetea todas las variables de selección
     */
    public void limpiarSeleccion() {
        cartasSeleccionadas.clear();                // Vacía la lista de cartas seleccionadas
        columnaOrigen = -1;                         // Resetea la columna de origen
        indiceInicioSeleccion = -1;                 // Resetea el índice de inicio
    }

    /**
     * Verifica si una carta puede colocarse en una fundación específica
     * Las fundaciones siguen el orden As, 2, 3... K del mismo palo
     */
    private boolean puedeColocarEnFundacion(Carta carta, List<Carta> fundacion) {
        if (fundacion.isEmpty()) {
            return carta.getValor() == 0;           // Solo el As (valor 0) puede iniciar una fundación
        }

        Carta cartaSuperior = fundacion.get(fundacion.size() - 1);
        return carta.esSiguienteEnSecuencia(cartaSuperior); // Debe ser la siguiente en secuencia
    }

    /**
     * Ejecuta el movimiento de una carta a una fundación
     * Remueve la carta de su ubicación actual y la coloca en la fundación
     */
    private void moverCartaAFundacion(Carta carta, int indice) {
        // Asegura que la carta esté volteada
        if (!carta.estaVolteada()) {
            carta.voltear();
        }

        removerCartaDeUbicacionActual(carta);       // La remueve de donde estaba
        tablero.getFundaciones().get(indice).add(carta); // La agrega a la fundación
        verificarVictoria();                        // Verifica si el juego ha terminado
    }

    /**
     * Mueve múltiples cartas a una columna del tableau
     * Procesa cada carta individualmente para mantener el orden
     */
    private void moverCartasATableau(List<Carta> cartas, int columnaDestino) {
        // Voltea todas las cartas si es necesario
        for (Carta carta : cartas) {
            if (!carta.estaVolteada()) {
                carta.voltear();
            }
        }

        // Remueve todas las cartas de sus ubicaciones actuales
        for (Carta carta : cartas) {
            removerCartaDeUbicacionActual(carta);
        }

        // Agrega todas las cartas a la columna de destino
        List<Carta> columna = tablero.getTableau().get(columnaDestino);
        columna.addAll(cartas);
    }

    /**
     * Remueve una carta de su ubicación actual en el tablero
     * Busca la carta en descarte o tableau y la remueve
     */
    private void removerCartaDeUbicacionActual(Carta carta) {
        // Primero verifica si está en el descarte
        if (tablero.getDescarte().contains(carta)) {
            tablero.getDescarte().remove(carta);
            return;
        }

        // Si no está en descarte, busca en el tableau
        removerCartaDeTableau(carta);
    }

    /**
     * Remueve una carta específica del tableau
     * También maneja el volteo automático de cartas descubiertas
     */
    private boolean removerCartaDeTableau(Carta carta) {
        // Busca la carta en todas las columnas del tableau
        for (int col = 0; col < tablero.getTableau().size(); col++) {
            List<Carta> columna = tablero.getTableau().get(col);
            if (columna.contains(carta)) {
                columna.remove(carta);              // Remueve la carta

                // Si quedan cartas en la columna, verifica la carta superior
                if (!columna.isEmpty()) {
                    Carta cartaSuperior = columna.get(columna.size() - 1);
                    if (!cartaSuperior.estaVolteada()) {
                        cartaSuperior.voltear();    // Voltea automáticamente la nueva carta superior
                        // PATRÓN DECORATOR: calcula puntuación por voltear carta
                        sistemaPuntuacion.calcularPuntuacionFinal(movimientos,
                                System.currentTimeMillis() - tiempoInicio,
                                contarCartasEnFundaciones());
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Busca movimientos automáticos posibles hacia las fundaciones
     * PATRÓN ITERATOR: usa el iterador para recorrer cartas volteadas
     * @return 
     */
    public List<String> buscarMovimientosAutomaticos() {
        CartaIterator iterator = tablero.getCartasVolteadasIterator(); // PATRÓN ITERATOR
        List<String> movimientosSugeridos = new ArrayList<>();

        // Itera sobre todas las cartas volteadas
        while (iterator.hasNext()) {
            Carta carta = iterator.next();
            Point posicion = encontrarPosicionCarta(carta);

            // Verifica si la carta puede moverse a alguna fundación
            for (int i = 0; i < 4; i++) {
                if (puedeColocarEnFundacion(carta, tablero.getFundaciones().get(i))) {
                    String sugerencia = String.format("%s (Columna %d) → Fundación %d",
                            carta.toString(),
                            posicion != null ? posicion.x + 1 : 0,
                            i + 1);
                    movimientosSugeridos.add(sugerencia);
                }
            }
        }

        return movimientosSugeridos;
    }

    /**
     * Cuenta el total de cartas en todas las fundaciones
     * Usado para calcular progreso y verificar victoria
     */
    private int contarCartasEnFundaciones() {
        int total = 0;
        for (List<Carta> fundacion : tablero.getFundaciones()) {
            total += fundacion.size();              // Suma cartas de cada fundación
        }
        return total;
    }

    /**
     * Verifica si el jugador ha ganado el juego
     * Victoria = todas las cartas están en las fundaciones
     */
    private boolean verificarVictoria() {
        int cartasEnFundaciones = contarCartasEnFundaciones();
        // PATRÓN ABSTRACT FACTORY: usa la factory para obtener el total de cartas
        return cartasEnFundaciones == factory.getNumeroValores() * factory.getNumeroPalos();
    }

    /**
     * Incrementa el contador de movimientos
     * Método auxiliar para mantener consistencia
     */
    private void incrementarMovimientos() {
        movimientos++;
    }

    /**
     * Reinicia completamente el juego
     * Mantiene la misma configuración (factory, estrategia, puntuación)
     */
    public void reiniciarJuego() {
        inicializarJuego();                         // Reinicia el estado del juego
        
        // Valida que el reinicio fue exitoso
        if (!validarEstadoJuego()) {
            throw new IllegalStateException("Error al reiniciar el juego");
        }
    }

    /**
     * Cambia el tipo de baraja (francesa/inglesa)
     * PATRÓN ABSTRACT FACTORY: intercambia la fábrica de cartas
     */
    public void cambiarTipoBaraja() {
        // Intercambia entre tipos de factory
        if (factory instanceof CartaInglesaFactory) {
            factory = new CartaInglesaFactory();    // Mantiene inglesa (parece error en original)
        } else {
            factory = new CartaFrancesaFactory();   // Cambia a francesa
        }

        inicializarJuego();                         // Reinicia con la nueva factory

        // Valida que el cambio fue exitoso
        if (!validarEstadoJuego()) {
            throw new IllegalStateException("Error al cambiar tipo de baraja");
        }
    }

    /**
     * Cambia la estrategia de juego durante la partida
     * PATRÓN STRATEGY: permite intercambiar estrategias dinámicamente
     */
    public void cambiarEstrategia(EstrategiaJuego nuevaEstrategia) {
        estrategiaActual = nuevaEstrategia;
    }

    /**
     * Cambia el sistema de puntuación durante la partida
     * PATRÓN DECORATOR: permite intercambiar decoradores de puntuación
     */
    public void cambiarSistemaPuntuacion(SistemaPuntuacion nuevoSistema) {
        sistemaPuntuacion = nuevoSistema;
    }

    /**
     * Encuentra la posición (columna, fila) de una carta específica
     * PATRÓN ITERATOR: usa iterador para buscar en el tableau
     */
    private Point encontrarPosicionCarta(Carta cartaBuscada) {
        CartaIterator iterator = tablero.getTableauIterator(); // PATRÓN ITERATOR
        int columna = 0;
        int fila = 0;

        // Itera sobre todas las cartas del tableau
        while (iterator.hasNext()) {
            Carta carta = iterator.next();
            if (carta == cartaBuscada) {
                return new Point(columna, fila);    // Retorna la posición encontrada
            }

            fila++;
            // Si llegó al final de la columna, pasa a la siguiente
            if (fila >= tablero.getTableau().get(columna).size()) {
                columna++;
                fila = 0;
            }
        }
        return null;                                // No encontrada
    }

    /**
     * Valida la integridad del estado del juego
     * Verifica que todas las cartas estén contabilizadas correctamente
     */
    private boolean validarEstadoJuego() {
        CartaIterator iterator = tablero.getTableauIterator(); // PATRÓN ITERATOR
        int cartasContadas = 0;

        // Cuenta cartas en el tableau
        while (iterator.hasNext()) {
            iterator.next();
            cartasContadas++;
        }

        // PATRÓN ABSTRACT FACTORY: obtiene el total esperado de cartas
        int totalEsperado = factory.getNumeroValores() * factory.getNumeroPalos();
        int cartasEnMazo = tablero.getMazo().size();
        int cartasEnDescarte = tablero.getDescarte().size();
        int cartasEnFundaciones = contarCartasEnFundaciones();

        // Verifica que la suma de todas las ubicaciones sea igual al total esperado
        return (cartasContadas + cartasEnMazo + cartasEnDescarte + cartasEnFundaciones) == totalEsperado;
    }

    // MÉTODOS GETTER - Proporcionan acceso controlado al estado interno

    /**
     * Obtiene el tablero actual del juego
     */
    public TableroSolitario getTablero() {
        return tablero;
    }

    /**
     * Obtiene el número de movimientos realizados
     */
    public int getMovimientos() {
        return movimientos;
    }

    /**
     * Calcula el tiempo transcurrido desde el inicio del juego
     */
    public long getTiempoTranscurrido() {
        return System.currentTimeMillis() - tiempoInicio;
    }

    /**
     * Calcula la puntuación actual del juego
     * PATRÓN DECORATOR: utiliza el sistema de puntuación decorado
     */
    public int getPuntuacion() {
        return sistemaPuntuacion.calcularPuntuacionFinal(movimientos, getTiempoTranscurrido(), contarCartasEnFundaciones());
    }

    /**
     * Verifica si el jugador ha ganado
     */
    public boolean esVictoria() {
        return verificarVictoria();
    }

    /**
     * Obtiene la estrategia de juego actual
     * PATRÓN STRATEGY: expone la estrategia actual
     */
    public EstrategiaJuego getEstrategiaActual() {
        return estrategiaActual;
    }

    /**
     * Obtiene el sistema de puntuación actual
     * PATRÓN DECORATOR: expone el decorador actual
     */
    public SistemaPuntuacion getSistemaPuntuacion() {
        return sistemaPuntuacion;
    }

    /**
     * Obtiene una copia de las cartas actualmente seleccionadas
     * Retorna una copia para evitar modificaciones externas
     */
    public List<Carta> getCartasSeleccionadas() {
        return new ArrayList<>(cartasSeleccionadas);
    }

    /**
     * Obtiene la fábrica de cartas actual
     * PATRÓN ABSTRACT FACTORY: expone la factory actual
     */
    public CartaAbstractFactory getFactory() {
        return factory;
    }

    /**
     * Formatea el tiempo en formato MM:SS
     * Método utilitario para presentación de datos
     * @param milisegundos
     * @return 
     */
    public String formatearTiempo(long milisegundos) {
        long segundos = milisegundos / 1000;        // Convierte milisegundos a segundos
        long minutos = segundos / 60;               // Extrae los minutos
        segundos = segundos % 60;                   // Obtiene los segundos restantes
        return String.format("%02d:%02d", minutos, segundos); // Formato con ceros a la izquierda
    }

    /**
     * Metodo toString que imprime el juego en la consola, pero no es necesario
     * para el funcionamiento del codigo, se puede selimiar si se requiere
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADO DEL JUEGO SOLITARIO ===\n");
        sb.append("Estrategia: ").append(estrategiaActual.getNombre()).append("\n");
        sb.append("Sistema de Puntuación: ").append(sistemaPuntuacion.getClass().getSimpleName()).append("\n");
        sb.append("Movimientos: ").append(movimientos).append("\n");
        sb.append("Tiempo: ").append(formatearTiempo(getTiempoTranscurrido())).append("\n");
        sb.append("Puntuación: ").append(getPuntuacion()).append("\n");
        sb.append("Estado: ").append(esVictoria() ? "¡VICTORIA!" : "En juego").append("\n");
        sb.append("\n");

        // Estado del mazo y descarte
        sb.append("MAZO Y DESCARTE:\n");
        sb.append("Cartas en mazo: ").append(tablero.getMazo().size()).append("\n");
        sb.append("Cartas en descarte: ").append(tablero.getDescarte().size());
        if (!tablero.getDescarte().isEmpty()) {
            Carta cartaTope = tablero.getDescarte().get(tablero.getDescarte().size() - 1);
            sb.append(" (Tope: ").append(cartaTope.toString()).append(")");
        }
        sb.append("\n\n");

        // Estado de las fundaciones
        sb.append("FUNDACIONES:\n");
        for (int i = 0; i < tablero.getFundaciones().size(); i++) {
            List<Carta> fundacion = tablero.getFundaciones().get(i);
            sb.append("Fundación ").append(i + 1).append(": ");
            if (fundacion.isEmpty()) {
                sb.append("Vacía");
            } else {
                sb.append(fundacion.size()).append(" cartas (Tope: ")
                  .append(fundacion.get(fundacion.size() - 1).toString()).append(")");
            }
            sb.append("\n");
        }
        sb.append("\n");

        // Estado del tableau
        sb.append("TABLEAU:\n");
        for (int col = 0; col < tablero.getTableau().size(); col++) {
            List<Carta> columna = tablero.getTableau().get(col);
            sb.append("Columna ").append(col + 1).append(": ");
            
            if (columna.isEmpty()) {
                sb.append("Vacía");
            } else {
                sb.append(columna.size()).append(" cartas [");
                for (int i = 0; i < columna.size(); i++) {
                    Carta carta = columna.get(i);
                    if (i > 0) sb.append(", ");
                    
                    if (carta.estaVolteada()) {
                        sb.append(carta.toString());
                    } else {
                        sb.append("🂠"); // Dorso de carta
                    }
                }
                sb.append("]");
            }
            sb.append("\n");
        }

        // Cartas seleccionadas
        if (!cartasSeleccionadas.isEmpty()) {
            sb.append("\n");
            sb.append("CARTAS SELECCIONADAS: ");
            for (int i = 0; i < cartasSeleccionadas.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(cartasSeleccionadas.get(i).toString());
            }
            sb.append("\n");
        }

        // Estadísticas adicionales
        sb.append("\n");
        sb.append("ESTADÍSTICAS:\n");
        sb.append("Total cartas en fundaciones: ").append(contarCartasEnFundaciones()).append("/52\n");
        sb.append("Progreso: ").append(String.format("%.1f%%", (contarCartasEnFundaciones() / 52.0) * 100)).append("\n");
        
        // Validación del estado
        sb.append("Estado válido: ").append(validarEstadoJuego() ? "✓" : "✗").append("\n");

        return sb.toString();
    }
}
