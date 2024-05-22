/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MonopolyJuego extends JFrame {

    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private int indiceJugadorActual;
    private JTextArea registroJuego;
    private JButton botonLanzarDado;
    private JButton botonTerminarTurno;
    private JPanel panelTablero;
    private JPanel[][] panelesCasillas;

    public MonopolyJuego() {
        setTitle("Juego de Monopoly");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tablero = new Tablero();
        jugadores = new ArrayList<>();
        indiceJugadorActual = 0;

        panelTablero = new JPanel(new GridLayout(11, 11));
        panelesCasillas = new JPanel[11][11];
        crearTableroVisual();

        JScrollPane scrollPane = new JScrollPane(panelTablero);
        scrollPane.setPreferredSize(new Dimension(800, 800));
        add(scrollPane, BorderLayout.CENTER);

        inicializarJugadores(4);

        registroJuego = new JTextArea();
        registroJuego.setEditable(false);
        add(new JScrollPane(registroJuego), BorderLayout.EAST);

        JPanel panelControl = new JPanel();
        botonLanzarDado = new JButton("Lanzar Dado");
        botonTerminarTurno = new JButton("Terminar Turno");

        panelControl.add(botonLanzarDado);
        panelControl.add(botonTerminarTurno);
        add(panelControl, BorderLayout.SOUTH);

        botonLanzarDado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lanzarDado();
            }
        });

        botonTerminarTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                terminarTurno();
            }
        });

        actualizarRegistroJuego("Juego iniciado con " + jugadores.size() + " jugadores.");
    }

    private void inicializarJugadores(int numeroDeJugadores) {
        Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA};
        for (int i = 0; i < numeroDeJugadores; i++) {
            Jugador jugador = new Jugador("Jugador " + (i + 1), 1500, colores[i]);
            jugadores.add(jugador);
            colocarFichaEnCasilla(jugador, 0);
        }
    }

    private void lanzarDado() {
        int dado = (int) (Math.random() * 6) + 1;
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);

        if (jugadorActual.isEnCarcel()) {
            actualizarRegistroJuego(jugadorActual.getNombre() + " está en la cárcel y ha sacado " + dado + ".");
            jugadorActual.incrementarTurnoEnCarcel();
            if (jugadorActual.getTurnosEnCarcel() >= 3 || dado == 6) {
                jugadorActual.setEnCarcel(false);
                actualizarRegistroJuego(jugadorActual.getNombre() + " ha salido de la cárcel.");
            } else {
                return;
            }
        }

        moverFicha(jugadorActual, dado);
        verificarPosicion(jugadorActual);
    }

    private void moverFicha(Jugador jugador, int espacios) {
        int posicionAnterior = jugador.getPosicion();
        jugador.mover(espacios, tablero);
        int nuevaPosicion = jugador.getPosicion();
        actualizarRegistroJuego(jugador.getNombre() + " se movió de la posición " + posicionAnterior + " a la posición " + nuevaPosicion);
        colocarFichaEnCasilla(jugador, nuevaPosicion);
    }

    private void colocarFichaEnCasilla(Jugador jugador, int posicion) {
        for (int i = 0; i < panelesCasillas.length; i++) {
            for (int j = 0; j < panelesCasillas[i].length; j++) {
                panelesCasillas[i][j].remove(jugador.getFicha());
                panelesCasillas[i][j].revalidate();
                panelesCasillas[i][j].repaint();
            }
        }

        Point coordenadas = obtenerCoordenadas(posicion);
        JPanel panelCasilla = panelesCasillas[coordenadas.x][coordenadas.y];
        panelCasilla.add(jugador.getFicha());
        panelCasilla.revalidate();
        panelCasilla.repaint();
    }

    private Point obtenerCoordenadas(int posicion) {
        if (posicion >= 0 && posicion < 40) {
            if (posicion < 11) {
                return new Point(0, posicion);
            } else if (posicion < 21) {
                return new Point(posicion - 10, 10);
            } else if (posicion < 31) {
                return new Point(10, 30 - posicion);
            } else {
                return new Point(40 - posicion, 0);
            }
        }
        return null;
    }

    private void verificarPosicion(Jugador jugador) {
        EspacioTablero espacio = tablero.getEspacio(jugador.getPosicion());
        if (espacio instanceof Propiedad) {
            Propiedad propiedad = (Propiedad) espacio;
            if (propiedad.getPropietario() == null) {
                int opcion = JOptionPane.showConfirmDialog(this, jugador.getNombre() + ", ¿quieres comprar " + propiedad.getNombre() + " por $" + propiedad.getCosto() + "?", "Comprar Propiedad", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    jugador.comprarPropiedad(propiedad);
                    actualizarRegistroJuego(jugador.getNombre() + " compró " + propiedad.getNombre());
                }
            } else if (propiedad.getPropietario() != jugador) {
                jugador.pagarAlquiler(propiedad);
                actualizarRegistroJuego(jugador.getNombre() + " pagó alquiler a " + propiedad.getPropietario().getNombre() + " por " + propiedad.getNombre());
            }
        } else if (espacio instanceof Carcel) {
            Carcel carcel = (Carcel) espacio;
            carcel.encarcelarJugador(jugador);
            actualizarRegistroJuego(jugador.getNombre() + " ha sido enviado a la cárcel.");
        } else if (espacio instanceof Impuesto) {
            Impuesto impuesto = (Impuesto) espacio;
            jugador.pagarAlquiler(new Propiedad("Impuesto", impuesto.getMonto(), 0)); // Simulación de pago de impuesto
            actualizarRegistroJuego(jugador.getNombre() + " ha pagado $" + impuesto.getMonto() + " de impuestos.");
        } else {
        }
    }

    private void terminarTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
        actualizarRegistroJuego("Es el turno de " + jugadores.get(indiceJugadorActual).getNombre() + ".");
    }

    private void actualizarRegistroJuego(String mensaje) {
        registroJuego.append(mensaje + "\n");
    }

    private void crearTableroVisual() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                int index = calcularIndex(i, j);
                JPanel panelCasilla = new JPanel();
                panelCasilla.setPreferredSize(new Dimension(70, 70));
                panelesCasillas[i][j] = panelCasilla;

                if (index >= 0 && index < 40) {
                    JButton botonCasilla = new JButton(tablero.getEspacio(index).getNombre());
                    panelCasilla.add(botonCasilla);
                }

                panelTablero.add(panelCasilla);
            }
        }
    }

    private int calcularIndex(int i, int j) {
        if (i == 0) {
            return j;
        } else if (i == 10) {
            return 30 + (10 - j);
        } else if (j == 0) {
            return 30 - i;
        } else if (j == 10) {
            return 10 + i;
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MonopolyJuego().setVisible(true);
            }
        });
    }
}
