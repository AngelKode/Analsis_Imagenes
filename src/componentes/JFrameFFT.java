/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import imagenes.ManipulacionImagen;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import listener.ListenerAplicarFFT;

/**
 *
 * @author depot
 */
public class JFrameFFT extends JFrame{
    
    JLabel lblCanales;
    JRadioButton radioBtnCanalRed, radioBtnCanalGreen, radioBtnCanalBlue;
    JButton btnAplicarFFT;
    JPanel panelCanales, panelBotones;
    ManipulacionImagen manipulador;
    
    public JFrameFFT(ManipulacionImagen manipulador){
        this.radioBtnCanalRed = new JRadioButton("Red");
        this.radioBtnCanalGreen = new JRadioButton("Green");
        this.radioBtnCanalBlue = new JRadioButton("Blue");
        this.btnAplicarFFT = new JButton("Aplicar Transformada");
        this.lblCanales = new JLabel("Canales");
        this.panelCanales = new JPanel();
        this.panelBotones = new JPanel();
        this.manipulador = manipulador;
        
        initComponents();
    }
    
    private void initComponents(){
        
        //Acomodamos el titulo y los radio button de los canales
        this.panelCanales.setLayout(new BorderLayout());
            JPanel contenedorLabelCanales = new JPanel();
                this.lblCanales.setFont(new Font("Arial",Font.BOLD, 18));
                contenedorLabelCanales.add(this.lblCanales);
            JPanel contenedorCanales = new JPanel();
                this.radioBtnCanalRed.setSelected(true);
                contenedorCanales.add(this.radioBtnCanalRed);
                this.radioBtnCanalGreen.setSelected(true);
                contenedorCanales.add(this.radioBtnCanalGreen);
                this.radioBtnCanalBlue.setSelected(true);
                contenedorCanales.add(this.radioBtnCanalBlue);
        this.panelCanales.add(contenedorLabelCanales, BorderLayout.NORTH);
        this.panelCanales.add(contenedorCanales, BorderLayout.CENTER);
        
        this.panelBotones.setLayout(new BoxLayout(this.panelBotones, BoxLayout.X_AXIS));
            JPanel contenedorBotones = new JPanel();
                this.btnAplicarFFT.addActionListener(new ListenerAplicarFFT(this.manipulador, this.radioBtnCanalRed, this.radioBtnCanalGreen, this.radioBtnCanalRed));
                this.btnAplicarFFT.setActionCommand("AplicarFFT");
                contenedorBotones.add(this.btnAplicarFFT);
        this.panelBotones.add(contenedorBotones);
        
        //Seteamos el acomodo del JFrame principal
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Fast Fourier Transform en 2D");
        this.setSize(450, 150);
        
        //Agregamos los componentes
        this.add(this.panelCanales, BorderLayout.NORTH);
        this.add(this.panelBotones, BorderLayout.CENTER);
    }
    
}
