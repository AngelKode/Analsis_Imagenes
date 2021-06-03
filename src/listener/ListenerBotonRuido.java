/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import imagenes.ManipulacionImagen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

/**
 *
 * @author depot
 */
public class ListenerBotonRuido implements ActionListener{
    
    ManipulacionImagen manipulador;
    JRadioButton btnSal, btnPimienta;
    JSlider sliderPorcentajeRuido;

    public ListenerBotonRuido(ManipulacionImagen manipulacion, JRadioButton btnSal, JRadioButton btnPimienta, JSlider porcentajeRuido) {
        this.manipulador = manipulacion;
        this.btnSal = btnSal;
        this.btnPimienta = btnPimienta;
        this.sliderPorcentajeRuido = porcentajeRuido;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("AplicarRuido")){
            this.manipulador.setEscalaGrisesImagen();
            this.manipulador.setRuidoImagen(this.sliderPorcentajeRuido.getValue(), this.btnSal.isSelected(), this.btnPimienta.isSelected());
            this.manipulador.getFrame().revalidate();
            this.manipulador.getFrame().repaint();
        }
    }
       
}
