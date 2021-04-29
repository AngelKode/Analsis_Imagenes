/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis_imagenes;

//import componentes.JFrameMatriz;
import java.awt.Dimension;
import java.io.IOException;
import componentes.JFrameModificarImagenes;

/**
 *
 * @author depot
 */
public class Analisis_Imagenes {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
       JFrameModificarImagenes aux = new JFrameModificarImagenes(new Dimension(500, 400));
    }

}
