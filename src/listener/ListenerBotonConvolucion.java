/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import imagenes.ManipulacionImagen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 *
 * @author depot
 */
public class ListenerBotonConvolucion implements ActionListener{
    
    ManipulacionImagen manipulador;
    JTextField[][] valores;

    public ListenerBotonConvolucion(ManipulacionImagen manipulador, JTextField[][] valores) {
        this.manipulador = manipulador;
        this.valores = valores;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("btnOK")){
            int valoresMatriz[][] = new int[5][5];
            for(int renglon = 0; renglon < 5; renglon++){
                for(int columna = 0; columna < 5; columna++){
                    valoresMatriz[renglon][columna] = Integer.parseInt(this.valores[renglon][columna].getText());
                }
            }
            this.manipulador.setConvolucion(valoresMatriz);
            this.manipulador.getFrame().revalidate();
            this.manipulador.getFrame().repaint();
        }
    }
    
}
