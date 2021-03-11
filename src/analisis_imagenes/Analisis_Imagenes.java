/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis_imagenes;

import imagenes.ManipulacionImagen;
import java.io.IOException;

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
        // TODO code application logic here
        ManipulacionImagen aux = new ManipulacionImagen();
        aux.obtenerImagen();
        aux.cambiarPixeles();
    }
    
}
