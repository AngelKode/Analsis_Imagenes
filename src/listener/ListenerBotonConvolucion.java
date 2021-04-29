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
import javax.swing.JTextField;
import org.jfree.ui.Spinner;

/**
 *
 * @author depot
 */
public class ListenerBotonConvolucion implements ActionListener{
    
    ManipulacionImagen manipulador;
    Spinner[][] valores;
    Spinner divisor, offset;
    JRadioButton canales[];

    public ListenerBotonConvolucion(ManipulacionImagen manipulador, Spinner[][] valores, Spinner divisor, Spinner offset, JRadioButton canales[]) {
        this.manipulador = manipulador;
        this.valores = valores;
        this.divisor = divisor;
        this.offset = offset;
        this.canales = canales;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("btnOK")){
            
            int valoresMatriz[][] = new int[5][5];
            //Obtenemos los valores de la matriz que ingresó el usuario
            for(int renglon = 0; renglon < 5; renglon++){
                for(int columna = 0; columna < 5; columna++){
                    valoresMatriz[renglon][columna] = this.valores[renglon][columna].getValue();
                }
            }
            //JRadioButton de los canales
            boolean red = this.canales[0].isSelected();
            boolean green = this.canales[1].isSelected();
            boolean blue = this.canales[2].isSelected();
            
            //Hacemos la convolucion de la imagen
            this.manipulador.setConvolucion(valoresMatriz,this.divisor.getValue(),this.offset.getValue(), red, green, blue);
            //Actualizamos la imagen
            this.manipulador.getFrame().revalidate();
            this.manipulador.getFrame().repaint();
        }
    }
    
}
