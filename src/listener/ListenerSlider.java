/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import componentes.JFrameModificarImagenes;
import imagenes.ManipulacionImagen;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author depot
 */
public class ListenerSlider implements ChangeListener{
    
    private final ManipulacionImagen manipulador;
    private String cambio_imagen;

    public ListenerSlider(ManipulacionImagen manipulador, String change) throws IOException {
        this.manipulador = manipulador;
        this.cambio_imagen = change;
    }
    
    
    @Override
    public void stateChanged(ChangeEvent e) {
        
       JSlider slider = (JSlider) e.getSource();
       if(!slider.getValueIsAdjusting()){
           
           JFrameModificarImagenes frame = this.manipulador.getFrame();
           frame.getContentPane().removeAll();
           frame.repaint();
           if(this.cambio_imagen.equals("Binarizar")){
                frame.add(this.manipulador.getImage());
           
                this.manipulador.setEscalaGrisesImagen();
                this.manipulador.setBinarizacionImagen(slider.getValue());
           }else if(this.cambio_imagen.equals("Iluminar")){
                frame.add(this.manipulador.getImage());
           
                this.manipulador.modificarIluminacion(slider.getValue());
           }else if(this.cambio_imagen.equals("ExpansionLinealR1")){
                frame.add(this.manipulador.getImage());
                this.manipulador.setR1(slider.getValue());
                this.manipulador.setExpansionLineal();
           }else if(this.cambio_imagen.equals("ExpansionLinealR2")){
                frame.add(this.manipulador.getImage());
                this.manipulador.setR2(slider.getValue());
                this.manipulador.setExpansionLineal();
           }else if(this.cambio_imagen.equals("ExpansionLog")){
               frame.add(this.manipulador.getImage());
               this.manipulador.setValorExpansiones(slider.getValue());
               this.manipulador.setExpansionLogaritmica();
           }else if(this.cambio_imagen.equals("ExpansionExp")){
               frame.add(this.manipulador.getImage());
               this.manipulador.setValorExpansiones(slider.getValue());
               this.manipulador.setExpansionExponencial();
           }  
           
           frame.revalidate();
           frame.repaint();
       }
    
    }
    
}
