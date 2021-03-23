/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import componentes.JFrameModificarImagenes;
import imagenes.ManipulacionImagen;
import java.io.IOException;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author depot
 */
public class ListenerSlider implements ChangeListener{
    
    private final ManipulacionImagen manipulador;

    public ListenerSlider(ManipulacionImagen manipulador) throws IOException {
        this.manipulador = manipulador;
    }
    
    
    @Override
    public void stateChanged(ChangeEvent e) {
        
       JSlider slider = (JSlider) e.getSource();
       if(!slider.getValueIsAdjusting()){
           JFrameModificarImagenes frame = this.manipulador.getFrame();
           frame.getContentPane().removeAll();
           frame.repaint();
           
           try {
               frame.add(this.manipulador.obtenerImagen());
           } catch (IOException ex) {
           }
           
           this.manipulador.setEscalaGrisesImagen();
           this.manipulador.setBinarizacionImagen(slider.getValue());
           
           frame.revalidate();
           frame.repaint();
       }
    
    }
    
}
