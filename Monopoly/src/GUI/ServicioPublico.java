/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

public class ServicioPublico extends EspacioTablero {

    private int costo;
    private int alquiler;
    private Jugador propietario;

    public ServicioPublico(String nombre) {
        super(nombre);
        this.costo = 150;
        this.alquiler = 10;
        this.propietario = null;
    }

    public int getCosto() {
        return costo;
    }

    public int getAlquiler() {
        return alquiler;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }
}
