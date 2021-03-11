/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import jframe.JFrameModificarImagenes;

/**
 *
 * @author depot
 */
public class ManipulacionImagen {
    
    private JFrameModificarImagenes frame_image;
    private BufferedImage buffer;
    private JLabel imagen;
    private int height;
    private int width;

    public ManipulacionImagen() throws IOException {
        this.buffer = null;
        this.frame_image = null;
        this.imagen = null;
        this.width = 0;
        this.height = 0;
    }
    
    public void obtenerImagen() throws IOException{
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        this.buffer = ImageIO.read(chooser.getSelectedFile());//Obtenemos la imagen en un buffer
        
        this.width = this.buffer.getWidth();
        this.height = this.buffer.getHeight();
        
        //Creamos un label con la imagen
        this.imagen = new JLabel(new ImageIcon(this.buffer));
        
        //Creamos el JFrame
        this.frame_image = new JFrameModificarImagenes(this.imagen, new Dimension(this.width, this.height));
    }
    
    public void cambiarPixeles(){
        for(int h = 0; h < this.height/2; h++){
            for(int w = 0; w < this.width/2; w++){
                this.buffer.setRGB(w,h, Color.yellow.getRGB());
                this.imagen.repaint();
            }
        }
    }
    
    
    
}
