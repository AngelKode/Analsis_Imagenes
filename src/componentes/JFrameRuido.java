/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import imagenes.ManipulacionImagen;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import listener.ListenerBotonRuido;

/**
 *
 * @author depot
 */
public class JFrameRuido extends JFrame{
    
    //Componentes del JFrame
    JLabel lblSlider, lblRadioBtn;
    JSliderCantidadRuido slider;
    JRadioButton radioBtnSal, radioBtnPimienta;
    JPanel panelPrincipal, panelSlider, panelRadioPrincipal,panelBotones;
    JButton btnAplicarCambios;
    
    //Manipulador de la imagen
    ManipulacionImagen manipulador;
    
    public JFrameRuido(ManipulacionImagen manipulador){
        this.manipulador = manipulador;
        initComponents();
        initListeners();
    }
    
    private void initListeners(){
        ListenerBotonRuido listener = new ListenerBotonRuido(this.manipulador,this.radioBtnSal,this.radioBtnPimienta,this.slider);
        this.btnAplicarCambios.addActionListener(listener);
        this.btnAplicarCambios.setActionCommand("AplicarRuido");
    }
    
    private void initComponents(){
        //Inicializamos el label
        this.lblSlider = new JLabel("Porcentaje de Ruido");
            this.lblSlider.setFont(new Font("Sans Serif", Font.BOLD, 18));
        this.lblRadioBtn = new JLabel("Tipo de Ruido");
            this.lblRadioBtn.setFont(new Font("Sans Serif", Font.BOLD, 18));
        //Inicializamos los JRadioButton
        this.radioBtnSal = new JRadioButton("Sal");
        this.radioBtnPimienta = new JRadioButton("Pimienta");
        this.btnAplicarCambios = new JButton("Aplicar");
        
        //Inicializamos el slider
        this.slider = new JSliderCantidadRuido();
        
        //Configuramos los paneles
        this.panelSlider = new JPanel();
            this.panelSlider.setLayout(new BoxLayout(this.panelSlider,BoxLayout.Y_AXIS));
            //-----------Centramos el JLabel--------------
            JPanel panelCentrarLblSlider = new  JPanel();
                panelCentrarLblSlider.add(this.lblSlider);
            this.panelSlider.add(panelCentrarLblSlider);
            //--------------------------------------------
            this.panelSlider.add(this.slider);
        
        this.panelRadioPrincipal = new JPanel();
            this.panelRadioPrincipal.setLayout(new BoxLayout(this.panelRadioPrincipal,BoxLayout.Y_AXIS));
            //-------Centramos el titulo de los radioButton----
            JPanel panelCentrarLblRadio = new JPanel();
                panelCentrarLblRadio.add(this.lblRadioBtn);
            //-------------------------------------------------
            this.panelRadioPrincipal.add(panelCentrarLblRadio);
            //-----------Centramos los radioButton-------------
            JPanel panelRadioButton = new JPanel();
            panelRadioButton.setLayout(new BoxLayout(panelRadioButton, BoxLayout.X_AXIS));
                JPanel panelCentrarRadioBtn = new JPanel();
                panelCentrarRadioBtn.add(this.radioBtnSal);
                panelCentrarRadioBtn.add(this.radioBtnPimienta);
            panelRadioButton.add(panelCentrarRadioBtn);
            //-------------------------------------------------
            this.panelRadioPrincipal.add(panelRadioButton);
        
        this.panelBotones = new JPanel();
            this.panelBotones.setLayout(new BoxLayout(this.panelBotones, BoxLayout.X_AXIS));
            //---------Centramos los botones----------------
            JPanel panelCentrarBotones = new JPanel();
            panelCentrarBotones.add(this.btnAplicarCambios);
            //----------------------------------------------
        this.panelBotones.add(panelCentrarBotones);
        
        this.panelPrincipal = new JPanel();
            this.panelPrincipal.setLayout(new BoxLayout(this.panelPrincipal,BoxLayout.Y_AXIS));
            this.panelPrincipal.add(this.panelSlider);
            this.panelPrincipal.add(this.panelRadioPrincipal);
            this.panelPrincipal.add(this.panelBotones);
            

        this.setVisible(true);
        this.add(this.panelPrincipal);
    }
    
}
