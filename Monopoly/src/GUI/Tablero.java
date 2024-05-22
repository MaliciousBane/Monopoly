/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.util.ArrayList;

public class Tablero {

    private ArrayList<EspacioTablero> espacios;

    public Tablero() {
        espacios = new ArrayList<>(40);
        inicializarEspacios();
    }

    private void inicializarEspacios() {
        espacios.add(new EspacioTablero("Salida"));
        espacios.add(new Propiedad("Avenida Mediterránea", 60, 2));
        espacios.add(new EspacioTablero("Arca Comunal"));
        espacios.add(new Propiedad("Avenida Báltica", 60, 4));
        espacios.add(new Impuesto("Impuesto sobre la Renta", 200));
        espacios.add(new EstacionTren("Ferrocarril Reading"));
        espacios.add(new Propiedad("Avenida Oriental", 100, 6));
        espacios.add(new EspacioTablero("Casualidad"));
        espacios.add(new Propiedad("Avenida Vermont", 100, 6));
        espacios.add(new Propiedad("Avenida Connecticut", 120, 8));
        espacios.add(new Carcel("Cárcel"));

        for (int i = 10; i < 40; i++) {
            espacios.add(new EspacioTablero("Casilla " + i));
        }
    }

    public EspacioTablero getEspacio(int posicion) {
        return espacios.get(posicion);
    }
}
