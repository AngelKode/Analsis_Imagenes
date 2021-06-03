/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import imagenes.ManipulacionImagen;
import java.awt.Font;
import javax.swing.JSlider;

/**
 *
 * @author depot
 */
public class JSliderCantidadRuido extends JSlider{
    
    public JSliderCantidadRuido() {
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.setMaximum(100);
        this.setMinimum(0);
        this.setMajorTickSpacing(10);
        this.setFont(new Font(Font.SANS_SERIF, 1, 16));
    }   
    
}
