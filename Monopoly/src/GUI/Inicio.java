/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel de Inicio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton btnComenzar = new JButton("Comenzar");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnComenzar);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        frame.add(panelPrincipal);

        frame.setVisible(true);

        btnComenzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] opcionesJugadores = {"2 jugadores", "3 jugadores", "4 jugadores", "5 jugadores", "6 jugadores"};

                JComboBox<String> comboBox = new JComboBox<>(opcionesJugadores);

                int result = JOptionPane.showConfirmDialog(frame, comboBox, "Selecciona la cantidad de jugadores", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    int cantidadJugadores = (int) comboBox.getSelectedItem();
                    JOptionPane.showMessageDialog(frame, "Has seleccionado " + cantidadJugadores + " jugadores.");
                    String seleccionJugadores = null;

                    switch (seleccionJugadores) {
                        case "2 jugadores":
                            int jug = 2;
                            break;

                        case "3 jugadores":
                            jug = 3;
                            break;
                        case "4 jugadores":
                            jug = 4;
                            break;

                        case "5 jugadores":
                            jug = 5;
                            break;

                        case "6 jugadores":
                            jug = 6;
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
