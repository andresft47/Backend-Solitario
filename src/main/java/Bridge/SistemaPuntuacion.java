/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bridge;

// ============================================================================
// ABSTRACION
// ============================================================================

/**
 * Clase base que define cómo se presenta y maneja la puntuación en el juego, 
 * delegando los cálculos matemáticos a las implementaciones.
 */
public abstract class SistemaPuntuacion {
    
    // Permite que la abstracción delegue trabajo a la implementación
    protected PuntuacionImpl implementacion;
    
    //Constructor que establece la conexión entre abstracción e implementación
    //Permite inyección de dependencias y intercambio dinámico de implementaciones
    public SistemaPuntuacion(PuntuacionImpl implementacion) {
        this.implementacion = implementacion;
    }
    
    //Métodos abstractos que definen la interfaz de la abstracción
    //Estas operaciones pueden usar la implementación pero agregan funcionalidad adicional
    public abstract int calcularPuntuacionFinal(int movimientos, long tiempo, int cartasEnFundacion);
    public abstract String mostrarEstadisticas();
    
    //Permite cambiar la implementación en tiempo de ejecución
    public void setImplementacion(PuntuacionImpl implementacion) {
        this.implementacion = implementacion;
    }
}
