/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import algoritmos.fft.FFT;
import imagenes.ManipulacionImagen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/**
 *
 * @author depot
 */
public class ListenerAplicarFFT implements ActionListener{
    
    ManipulacionImagen manipulador;
    JRadioButton red,green,blue;
    
    public ListenerAplicarFFT(ManipulacionImagen manipulador, JRadioButton red,JRadioButton green,JRadioButton blue) {
        this.manipulador = manipulador;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("AplicarFFT")){
            this.manipulador.setFFT(this.red.isSelected(), this.green.isSelected(), this.blue.isSelected());
        }
    }
    
}
