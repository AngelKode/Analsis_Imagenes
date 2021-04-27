/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import imagenes.ManipulacionImagen;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import listener.ListenerBotonConvolucion;

/**
 *
 * @author depot
 */
public final class JFrameMatriz extends JFrame{
    
    private static final int tamanioMatriz = 5;
    private final JTextField[][] matriz;
    private final JButton btnOK;
    private final JPanel panelMatriz, panelBotones, panelTextoMatriz;
    private final ManipulacionImagen manipulador;
    
    public JFrameMatriz(ManipulacionImagen manipulador){
        this.matriz = new JTextField[tamanioMatriz][tamanioMatriz];
        this.btnOK = new JButton("Aceptar");
        this.panelMatriz = new JPanel(new GridLayout(tamanioMatriz, tamanioMatriz));
        this.panelBotones = new JPanel(new GridLayout(1, 1));
        this.panelTextoMatriz = new JPanel(new GridLayout(1, 1));
        this.manipulador = manipulador;
        initListeners();
        initComponents();
    }
    
    public void initListeners(){
        ListenerBotonConvolucion listener = new ListenerBotonConvolucion(this.manipulador, this.matriz);
        this.btnOK.addActionListener(listener);
        this.btnOK.setActionCommand("btnOK");
    }
    public void initComponents(){
        //Seteamos un layout de tipo grid para el panel de la matriz y de los botones
        this.setLayout(new BorderLayout(0, 10));
        
        //Creamos los JTextField para ingresar los valores de la matriz
        
        for(int fila = 0; fila < tamanioMatriz; fila++){
            for(int columna = 0; columna < tamanioMatriz; columna++){
                JTextField txtAux = new JTextField("0");
                this.matriz[fila][columna] = txtAux;//Lo agregamos a la matriz
                this.panelMatriz.add(txtAux);//Agregamos al panel
            }
        }
        
        //Agregamos los botones
        this.btnOK.setSize(this.getWidth(), 40);
        this.panelBotones.setSize(this.getWidth(), 40);
        this.panelBotones.add(this.btnOK);
        
        //Agregamos el texto
        this.panelTextoMatriz.add(new JLabel("Matriz"));
        this.panelTextoMatriz.setSize(this.getWidth(), 80);
        
        //Agregamos los paneles al JFrame
        this.add(this.panelTextoMatriz, BorderLayout.NORTH);
        this.add(this.panelMatriz, BorderLayout.CENTER);
        this.add(this.panelBotones, BorderLayout.SOUTH);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setSize(400, 500);
        this.setTitle("Matriz de Convolución");
    }
    
}
