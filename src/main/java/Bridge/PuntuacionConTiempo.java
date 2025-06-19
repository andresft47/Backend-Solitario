/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bridge;

// ============================================================================
// ABSTRACCION REFINADA
// ============================================================================
/**
 * Sistema de puntuación avanzado que además del cálculo base, 
 * otorga bonificaciones por velocidad: +1000 puntos si terminas 
 * en menos de 5 minutos, +500 si terminas en menos de 10 minutos.
 */
public class PuntuacionConTiempo extends SistemaPuntuacion {

    // Estado adicional específico de esta abstracción refinada
    private long tiempoInicio;

    //Constructor que establece la implementación y inicializa estado propio
    public PuntuacionConTiempo(PuntuacionImpl implementacion) {
        super(implementacion);
        this.tiempoInicio = System.currentTimeMillis();
    }

    // Extiende la funcionalidad base agregando lógica de bonificación por tiempo
    @Override
    public int calcularPuntuacionFinal(int movimientos, long tiempo, int cartasEnFundacion) {
        int puntuacionBase = implementacion.calcularPuntos(movimientos, tiempo, cartasEnFundacion);

        // Bonus por velocidad
        if (tiempo < 300000) { // Menos de 5 minutos
            puntuacionBase += 1000;
        } else if (tiempo < 600000) { // Menos de 10 minutos
            puntuacionBase += 500;
        }

        return Math.max(0, puntuacionBase);
    }

    //Combina información propia con la de la implementación
    @Override
    public String mostrarEstadisticas() {
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
        return "Puntuación con Tiempo - " + implementacion.getDescripcion()
                + " | Tiempo: " + (tiempoTranscurrido / 1000) + "s";
    }

    // Método específico de esta abstracción refinada
    public void reiniciarTiempo() {
        this.tiempoInicio = System.currentTimeMillis();
    }
}
