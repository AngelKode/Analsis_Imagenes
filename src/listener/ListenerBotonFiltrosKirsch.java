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
import org.jfree.ui.Spinner;

/**
 *
 * @author depot
 */
public class ListenerBotonFiltrosKirsch implements ActionListener{
    
    ManipulacionImagen manipulador;
    Spinner divisor, offset;
    JRadioButton canales[];
    
    public ListenerBotonFiltrosKirsch(ManipulacionImagen manipulador, Spinner divisor, Spinner offset, JRadioButton canales[]) {
        this.manipulador = manipulador;
        this.divisor = divisor;
        this.offset = offset;
        this.canales = canales;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("btnAplicarFiltrosKirsch")){
            //JRadioButton de los canales
            boolean red = this.canales[0].isSelected();
            boolean green = this.canales[1].isSelected();
            boolean blue = this.canales[2].isSelected();
            
            //Aplicamos todos los filtros
            this.manipulador.setFiltrosAcumulativosKirsch(this.divisor.getValue(),this.offset.getValue(), red, green, blue);
            //Actualizamos la imagen
            this.manipulador.getFrame().revalidate();
            this.manipulador.getFrame().repaint();
        }
    }
    
}
