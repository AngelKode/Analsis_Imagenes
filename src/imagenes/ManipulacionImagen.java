/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagenes;

import componentes.JFrameModificarImagenes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author depot
 */
public class ManipulacionImagen {
    
    private JFrameModificarImagenes frame;
    private BufferedImage buffer_original,buffer_cambiada;
    private JLabel imagen;
    private int height,width;
    private int[] red;
    private int[] green;
    private int[] blue;

    public ManipulacionImagen(JFrameModificarImagenes frame) throws IOException {
        this.frame = frame;
        this.buffer_original = null;
        this.buffer_cambiada = null;
        this.imagen = null;
        this.width = 0;
        this.height = 0;
        this.red = new int[255];
        this.green = new int[255];
        this.blue = new int[255];
    }
    
    public JLabel obtenerImagen() throws IOException{
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        
        this.buffer_original = ImageIO.read(chooser.getSelectedFile());//Obtenemos la imagen en un buffer
        this.buffer_cambiada = cloneBuffer(this.buffer_original);
        
        this.width = this.buffer_original.getWidth();
        this.height = this.buffer_original.getHeight();
        
        //Creamos un label con la imagen
        this.imagen = new JLabel(new ImageIcon(this.buffer_cambiada));
        
        return this.imagen;
    }
    
    public void setNegativoImagen(){
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h < this.height;h++){
                Color color = new Color(this.buffer_original.getRGB(w,h));
                int pixel_red = 255 - color.getRed();
                int pixel_green = 255 - color.getGreen();
                int pixel_blue = 255 - color.getBlue();
                Color color_nuevo = new Color(pixel_red,pixel_green,pixel_blue);
                this.buffer_cambiada.setRGB(w,h,color_nuevo.getRGB());
            }
        }
    }
    
    public void setEscalaGrisesImagen(){
        Color color;
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h < this.height;h++){
                color = new Color(this.buffer_original.getRGB(w,h));
                
                int promedio = (int)(color.getRed() + color.getGreen() + color.getBlue()) / 3;
                color = new Color(promedio,promedio,promedio);
                this.buffer_cambiada.setRGB(w,h, color.getRGB());
            }
        }
    }
    
    public void setBinarizacionImagen(int umbral){
        Color color;
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h < this.height;h++){
                color = new Color(this.buffer_cambiada.getRGB(w,h));
                
                int promedio = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                color = (promedio >= umbral) ? Color.white : Color.black;
                this.buffer_cambiada.setRGB(w,h, color.getRGB());
            }
        }
    }
    
    public void obtenerHistograma(){
        Color color;
        this.red = new int[256];
        this.green = new int[256];
        this.blue = new int[256];
        
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h< this.height;h++){
                color = new Color(this.buffer_cambiada.getRGB(w,h));
                this.red[color.getRed()]++;
                this.green[color.getGreen()]++;
                this.blue[color.getBlue()]++;
            }
        } 
    }
    
    private BufferedImage cloneBuffer(BufferedImage source){
        BufferedImage newBuffer = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = newBuffer.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return newBuffer;
    }
    
    public BufferedImage getBuffer() {
        return buffer_cambiada;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer_cambiada = buffer;
    }

    public JFrameModificarImagenes getFrame() {
        return frame;
    }

    public void setFrame(JFrameModificarImagenes frame) {
        this.frame = frame;
    }

    public int[] getRed() {
        return red;
    }

    public void setRed(int[] red) {
        this.red = red;
    }

    public int[] getGreen() {
        return green;
    }

    public void setGreen(int[] green) {
        this.green = green;
    }

    public int[] getBlue() {
        return blue;
    }

    public void setBlue(int[] blue) {
        this.blue = blue;
    }
    
    

    
    
}
