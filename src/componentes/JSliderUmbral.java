/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import imagenes.ManipulacionImagen;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import listener.ListenerSlider;

/**
 *
 * @author depot
 */
public class JSliderUmbral extends JSlider{
    
    private final ManipulacionImagen manipulador;
    
    public JSliderUmbral(ManipulacionImagen manipulador,int min, int max, int espacio,String change) throws IOException {
        this.manipulador = manipulador;
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.setVisible(true);
        this.setMajorTickSpacing(espacio);
        this.setMaximum(max);
        this.setMinimum(min);
        this.setFont(new Font(Font.SANS_SERIF, 1, 16));
        this.addChangeListener(new ListenerSlider(this.manipulador,change));
    }
    
    
}
