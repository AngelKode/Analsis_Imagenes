/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagenes;

import componentes.JFrameModificarImagenes;
import filtros.MascarasBordes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import org.opencv.core.Core;

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
    private int valorMinimoR1, valorMaximoR2;
    private int valorExpansiones;

    public ManipulacionImagen(JFrameModificarImagenes frame) throws IOException {
        this.frame = frame;
        this.buffer_original = null;
        this.buffer_cambiada = null;
        this.imagen = null;
        this.width = 0;
        this.height = 0;
        this.red = new int[256];
        this.green = new int[256];
        this.blue = new int[256];
        this.valorMinimoR1 = 0;
        this.valorMaximoR2 = 0;
        this.valorExpansiones = 0;
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
    
    public JLabel getImage(){
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
    
    public void modificarIluminacion(int valor){
        Color color;
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h < this.height;h++){
                color = new Color(this.buffer_original.getRGB(w,h));
                int r = checarColor(color.getRed() + valor);
                int g = checarColor(color.getGreen() + valor);
                int b = checarColor(color.getBlue() + valor);
                color = new Color(r,g,b);
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
    
    public void setBinarizacionAutomatica(){
        //Primero obtenemos un promedio usando el histograma
        int umbral_actual = obtenerHistogramaPromedio(), umbral_anterior = 0;
        
        do{
            umbral_anterior = umbral_actual;
            umbral_actual = obtenerNuevoUmbral(umbral_actual);
        }while(umbral_actual != umbral_anterior);
        
        //Obtenemos la binarizacion
        setEscalaGrisesImagen();
        setBinarizacionImagen(umbral_actual);
    }
    
    private int obtenerHistogramaPromedio(){
        //Obtenemos el hisotgrama actual
        obtenerHistograma();
        
        int resultado = obtenerPromedioHistograma(0, 255);
        
        return resultado;
    }
    
    public void setExpansionLineal(){
        Color color;
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h< this.height;h++){
                color = new Color(this.buffer_original.getRGB(w,h));
                int valorRed = checarColor((color.getRed() - this.valorMinimoR1) * ((int)((double)255 / (double)this.valorMaximoR2 - this.valorMinimoR1)));
                int valorGreen = checarColor((color.getGreen() - this.valorMinimoR1) * ((int)((double)255 / (double)this.valorMaximoR2 - this.valorMinimoR1)));
                int valorBlue = checarColor((color.getBlue() - this.valorMinimoR1) * ((int)((double)255 / (double)this.valorMaximoR2 - this.valorMinimoR1)));
                
                Color colorNuevo = new Color(valorRed, valorGreen, valorBlue);
                this.buffer_cambiada.setRGB(w, h, colorNuevo.getRGB());
            }
        } 
        
    }
    
    public void setR1(int valor){
        this.valorMinimoR1 = valor;
    }
    
    public void setR2(int valor){
        this.valorMaximoR2 = valor;
    }
    
    public void setValorExpansiones(int valor){
        this.valorExpansiones = valor;
    }
    
    public void setExpansionLogaritmica(){
        Color color;
        
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h< this.height;h++){
                color = new Color(this.buffer_original.getRGB(w,h));
                int valorRed = checarColor((int)((double)(255*Math.log(1+color.getRed()))/(double)(Math.log(1+this.valorExpansiones))));
                int valorGreen = checarColor((int)((double)(255*Math.log(1+color.getGreen()))/(double)(Math.log(1+this.valorExpansiones))));
                int valorBlue = checarColor((int)((double)(255*Math.log(1+color.getBlue()))/(double)(Math.log(1+this.valorExpansiones))));
                
                Color colorNuevo = new Color(valorRed, valorGreen, valorBlue);
                this.buffer_cambiada.setRGB(w, h, colorNuevo.getRGB());
            }
        } 
    }
    
    public void setExpansionExponencial(){
        Color color;
        
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h< this.height;h++){
                color = new Color(this.buffer_original.getRGB(w,h));
                int valorRed = checarColor((int) Math.pow(1+this.valorExpansiones, color.getRed() / this.valorExpansiones));
                int valorGreen = checarColor((int) Math.pow(1+this.valorExpansiones, color.getGreen() / this.valorExpansiones));
                int valorBlue = checarColor((int) Math.pow(1+this.valorExpansiones, color.getBlue() / this.valorExpansiones));
                
                Color colorNuevo = new Color(valorRed, valorGreen, valorBlue);
                this.buffer_cambiada.setRGB(w, h, colorNuevo.getRGB());
            }
        } 
    }
    
    public void setEcualizacion(){
        double acumulado[] = new double[256];
        int suma = 0;
        Color color;
        
        setEscalaGrisesImagen();
        obtenerHistograma();
        
        //Primero hacemos la suma acumulativa de el histograma
        for(int posicionHistograma = 0; posicionHistograma < 255 ; posicionHistograma++){
            suma += (this.red[posicionHistograma] + this.green[posicionHistograma] + this.blue[posicionHistograma]);
            acumulado[posicionHistograma] = suma;
        }
        
        double constante = ( (double) 255 / (double) (this.width * this.height) );
        
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h< this.height;h++){
                color = new Color(this.buffer_original.getRGB(w,h));
                int posicionArreglo = color.getRed();
                
                //El nuevo color será el valor que de de multiplicar la constante por el valor de el acumulado del
                //histograma en la posicion que de del promecio de los 3 colores del pixel
                int nuevoColor = checarColor((int)Math.round((acumulado[posicionArreglo] * constante)));
                Color nuevoColorPixel = new Color(nuevoColor,nuevoColor,nuevoColor);
                
                this.buffer_cambiada.setRGB(w, h, nuevoColorPixel.getRGB());
                
            }
        }
    }
    
    public void setConvolucion(int valores[][], int divisor, int offset, boolean red, boolean green, boolean blue){
        for(int w = 0; w < this.width; w++){
            for(int h = 0; h < this.height; h++){
                //Obtenemos el pixel actual
                Color colorActual = new Color(this.buffer_original.getRGB(w, h));
                
                //Creamos una variable para guardar la sumatorias de cada canal, pero si no esta habilitado
                //algun canal, su valor será el valor del pixel actual
                int sumatoriaOperacionRed = (red) ? 0 : colorActual.getRed();
                int sumatoriaOperacionGreen =  (green) ? 0 : colorActual.getGreen();
                int sumatoriaOperacionBlue =  (blue) ? 0 : colorActual.getBlue();
                
                //Obtenemos la posicion inicial de la matriz en la imagen para poder hacer
                //las operaciones
                int posInicialWMatriz = w - 2;
                int posInicialHMatriz = h - 2;
                //Hacemos la operacion de la matriz
                for(int posicionWidth = w - 2; posicionWidth < (w-2) + 5;  posicionWidth++){
                    for(int posicionHeight = h - 2; posicionHeight < (h-2) + 5; posicionHeight++){
                        //Evaluamos si la posicion es valida dentro de la imagen
                        if(isValidValue(posicionWidth, posicionHeight)){
                            //Obtenemos el valor de el pixel
                            Color pixelActual = new Color(this.buffer_original.getRGB(posicionWidth,posicionHeight));
                            int _red = pixelActual.getRed();
                            int _green = pixelActual.getGreen();
                            int _blue = pixelActual.getBlue();
                            
                            //Hacemos la multiplicacion del tono con el valor del kernel dependiendo de los canales seleccionados
                            int multiplicacionRed = 0, multiplicacionGreen = 0, multiplicacionBlue = 0;
                            int renglon = posicionHeight - posInicialHMatriz;
                            int columna = posicionWidth - posInicialWMatriz;
                            
                            if(red){
                                multiplicacionRed = (_red * valores[renglon][columna]);
                                sumatoriaOperacionRed += multiplicacionRed;//Sumamos el resultado
                            }
                            if(green){
                                multiplicacionGreen = (_green * valores[renglon][columna]);
                                sumatoriaOperacionGreen += multiplicacionGreen;//Sumamos el resultado
                            }
                            if(blue){
                                multiplicacionBlue = (_blue * valores[renglon][columna]);
                                sumatoriaOperacionBlue += multiplicacionBlue;//Sumamos el resultado
                            }
                            
                        }
                    }    
                }
                
                //Checamos si hay valores en divisor diferentes de 0
                divisor = (divisor != 0) ? divisor : 1;
                
                //Hacemos la division y el offset, dependiendo las selecciones de canales
                if(red){
                    sumatoriaOperacionRed /= divisor;
                    //Agregamos el offset
                    sumatoriaOperacionRed += offset;
                    
                    //Verificamos que las sumatorias entren en un rango de 0 a 255
                    sumatoriaOperacionRed = checarValorColor(sumatoriaOperacionRed);
                }
                if(green){
                    sumatoriaOperacionGreen /= divisor;
                    //Agregamos el offset
                    sumatoriaOperacionGreen += offset;
                    
                    //Verificamos que las sumatorias entren en un rango de 0 a 255
                    sumatoriaOperacionGreen = checarValorColor(sumatoriaOperacionGreen);
                }
                if(blue){
                    sumatoriaOperacionBlue /= divisor;
                    //Agregamos el offset
                    sumatoriaOperacionBlue += offset;
                    
                    //Verificamos que las sumatorias entren en un rango de 0 a 255
                    sumatoriaOperacionBlue = checarValorColor(sumatoriaOperacionBlue);
                }

                //Seteamos el nuevo color
                Color colorNuevo = new Color(sumatoriaOperacionRed, sumatoriaOperacionGreen, sumatoriaOperacionBlue);
                this.buffer_cambiada.setRGB(w, h, colorNuevo.getRGB());
            }
        }
    }
    
    
    public void setFiltrosAcumulativosKirsch(int divisor, int offset, boolean red, boolean green, boolean blue){
        //Clase para obtener los filtros
        MascarasBordes mascaras = new MascarasBordes();
        
        //Hacemos el filtro iterativo con cada mascara
        for(double[][] mascara : mascaras.getMascaras()){
            for(int w = 0; w < this.width; w++){
                for(int h = 0; h < this.height; h++){
                    //Obtenemos el pixel actual
                    Color colorActual = new Color(this.buffer_original.getRGB(w, h));
                    
                    //Creamos una variable para guardar la sumatorias de cada canal, pero si no esta habilitado
                    //algun canal, su valor será el valor del pixel actual
                    int sumatoriaOperacionRed = (red) ? 0 : colorActual.getRed();
                    int sumatoriaOperacionGreen =  (green) ? 0 : colorActual.getGreen();
                    int sumatoriaOperacionBlue =  (blue) ? 0 : colorActual.getBlue();
                    
                    //Obtenemos la posicion inicial de la matriz en la imagen para poder hacer
                    //las operaciones
                    int posInicialWMatriz = w - 2;
                    int posInicialHMatriz = h - 2;
                    //Hacemos la operacion de la matriz
                    for(int posicionWidth = w - 2; posicionWidth < (w-2) + 5;  posicionWidth++){
                        for(int posicionHeight = h - 2; posicionHeight < (h-2) + 5; posicionHeight++){
                        //Evaluamos si la posicion es valida dentro de la imagen
                            if(isValidValue(posicionWidth, posicionHeight)){
                                //Obtenemos el valor de el pixel
                                Color pixelActual = new Color(this.buffer_original.getRGB(posicionWidth,posicionHeight));
                                int _red = pixelActual.getRed();
                                int _green = pixelActual.getGreen();
                                int _blue = pixelActual.getBlue();
                            
                                //Hacemos la multiplicacion del tono con el valor del kernel dependiendo de los canales seleccionados
                                int multiplicacionRed = 0, multiplicacionGreen = 0, multiplicacionBlue = 0;
                                int renglon = posicionHeight - posInicialHMatriz;
                                int columna = posicionWidth - posInicialWMatriz;

                                if(red){
                                    multiplicacionRed = (_red * (int)mascara[renglon][columna]);
                                    sumatoriaOperacionRed += multiplicacionRed;//Sumamos el resultado
                                }
                                if(green){
                                    multiplicacionGreen = (_green * (int)mascara[renglon][columna]);
                                    sumatoriaOperacionGreen += multiplicacionGreen;//Sumamos el resultado
                                }
                                if(blue){
                                    multiplicacionBlue = (_blue * (int)mascara[renglon][columna]);
                                    sumatoriaOperacionBlue += multiplicacionBlue;//Sumamos el resultado
                                }
                            
                            }
                        }    
                    }
                
                    //Checamos si hay valores en divisor diferentes de 0
                    divisor = (divisor != 0) ? divisor : 1;
                
                    //Hacemos la division y el offset, dependiendo las selecciones de canales
                    if(red){
                        sumatoriaOperacionRed /= divisor;
                        //Agregamos el offset
                        sumatoriaOperacionRed += offset;

                        //Verificamos que las sumatorias entren en un rango de 0 a 255
                        sumatoriaOperacionRed = checarValorColor(sumatoriaOperacionRed);
                    }
                    if(green){
                        sumatoriaOperacionGreen /= divisor;
                        //Agregamos el offset
                        sumatoriaOperacionGreen += offset;

                        //Verificamos que las sumatorias entren en un rango de 0 a 255
                        sumatoriaOperacionGreen = checarValorColor(sumatoriaOperacionGreen);
                    }
                    if(blue){
                        sumatoriaOperacionBlue /= divisor;
                        //Agregamos el offset
                        sumatoriaOperacionBlue += offset;

                        //Verificamos que las sumatorias entren en un rango de 0 a 255
                        sumatoriaOperacionBlue = checarValorColor(sumatoriaOperacionBlue);
                    }

                    //Seteamos el nuevo color
                    Color colorNuevo = new Color(sumatoriaOperacionRed, sumatoriaOperacionGreen, sumatoriaOperacionBlue);
                    this.buffer_cambiada.setRGB(w, h, colorNuevo.getRGB());
                    
                }
            }
            //Ahora actualizamos lo que tiene buffer original
            this.buffer_original = this.buffer_cambiada;
        }
    }
    
    
    private int obtenerNuevoUmbral(int umbral_actual){
        /*Ahora comparamos ambos espectros del umbral actual
         Obtenemos el resultado de el promedio de cada uno de los espectros, y retornamos el promedio 
         de los 2 promedios
        */
        int resultado_espectro_izquierdo = 0, resultado_espectro_derecho = 0;
        
        try {
            resultado_espectro_izquierdo = obtenerPromedioHistograma(0, umbral_actual);
            resultado_espectro_derecho = 0;
            if(umbral_actual < 253){
                resultado_espectro_derecho = obtenerPromedioHistograma(umbral_actual + 1, 255);
            }
        } catch (ArithmeticException e) {
            //Si se da una excepcion, la cantidad de pixeles del lado derecho o izquierdo
            //es 0
            return 0;
        }
        
        return (int)((resultado_espectro_izquierdo + resultado_espectro_derecho) / 2 );
    }
    
    private int obtenerPromedioHistograma(int inicio, int fin){
        int acumulado = 0, cantidad_pixeles = 0, cantidad_rgb = 0;
        //Recorremos el histograma
        
        for(int posicionHistograma = inicio; posicionHistograma <= fin; posicionHistograma++){
            cantidad_rgb = this.red[posicionHistograma] + this.green[posicionHistograma] + this.red[posicionHistograma];
            cantidad_pixeles += cantidad_rgb;
            acumulado += cantidad_rgb * posicionHistograma;
        }
        return (int)(acumulado / cantidad_pixeles);
    }
    
    private BufferedImage cloneBuffer(BufferedImage source){
        BufferedImage newBuffer = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = newBuffer.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return newBuffer;
    }
    
    public void guardarImagen() throws IOException{
        File archivo = new File("C:\\Users\\depot\\Desktop\\Imagen.jpg");
        ImageIO.write(this.buffer_cambiada,"jpg", archivo);
    }
    
    private int checarColor(int valorColor){
        if(valorColor > 255){
            return 255;
        }else if(valorColor < 0){
            return 0;
        }
        return valorColor;
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
    
    private boolean isValidValue(int valorWidth, int valorHeight){
        return (valorWidth >= 0 && valorWidth < this.width) && (valorHeight >= 0 && valorHeight < this.height);
    }
    
    private int checarValorColor(int valor){
        if(valor < 0){
            return 0;
        }else if(valor > 255){
            return 255;
        }
        
        return valor;
    }
    
}
