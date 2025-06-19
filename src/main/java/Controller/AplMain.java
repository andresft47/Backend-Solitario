/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


/**
 *
 * @author Andres Felipe Tovar
 */
public class AplMain {
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            SolitarioController juego = new SolitarioController();
            System.out.println(juego.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
