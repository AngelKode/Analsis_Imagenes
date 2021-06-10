/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import componentes.JFrameFFT;
import componentes.JFrameMatriz;
import imagenes.ManipulacionImagen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import componentes.JFrameModificarImagenes;
import componentes.JFrameRuido;
import componentes.JSliderUmbral;
import graficas.Graficador;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author depot
 */
public class MenuOptionImage extends JMenuBar implements ActionListener, ItemListener{
    
    private final JFrameModificarImagenes frame;
    private JLabel label;
    private final ArrayList<JMenu> items_menu_principal;
    private final ArrayList<JMenuItem[]> item_menu;
    private final ManipulacionImagen manipulador;
    
    
    public MenuOptionImage(ArrayList<String> menuPrincipalNombres, ArrayList<String[]> itemsNombres,
                           ArrayList<String[]> actionCommands,JFrameModificarImagenes frame) throws IOException {
        //Inicializamos los componentes
        this.frame = frame;
        this.label = null;
        this.items_menu_principal = new ArrayList<>();
        this.item_menu = new ArrayList<>();
        this.manipulador = new ManipulacionImagen(this.frame);
        
        for(int cantidadMenus = 0; cantidadMenus < menuPrincipalNombres.size(); cantidadMenus++){
            JMenu menuAux = new JMenu(menuPrincipalNombres.get(cantidadMenus));
            this.items_menu_principal.add(menuAux);
            int cantidadItems = itemsNombres.get(cantidadMenus).length;
            JMenuItem[] items = new JMenuItem[cantidadItems];
            String[] nombres_items = itemsNombres.get(cantidadMenus);
            
            for(int item = 0; item < cantidadItems; item++){
                JMenuItem itemAuxObjecto = new JMenuItem(nombres_items[item]);
                itemAuxObjecto.addActionListener(this);
                itemAuxObjecto.setActionCommand(actionCommands.get(cantidadMenus)[item]);
                items[item] = itemAuxObjecto;
            }
            this.item_menu.add(items);
        }
        
        //Agregamos los submenus
        for(int i = 0; i < this.item_menu.size();i++){
            for (JMenuItem get : this.item_menu.get(i)) {
                this.items_menu_principal.get(i).add(get);
            }
        }
        //Agregamos los menus
        for (JMenu items_menu_principal1 : this.items_menu_principal) {
            this.add(items_menu_principal1);
        }
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch(e.getActionCommand()){
            case "AbrirImagen":{
                //Eliminamos todo el contenido que tenga el JFrame
                this.frame.getContentPane().removeAll();
                this.frame.repaint();

                try{
                    this.label = this.manipulador.obtenerImagen();//Obtenemos una imagen nueva
                    //this.manipulador.setEscalaGrisesImagen();//Primero obtenemos su escala de grises
                    //this.manipulador.setBinarizacionImagen(120);
                    this.frame.add(this.label);//Agregamos el jlabel al frame
                    this.frame.revalidate();//Revalidamos
                    this.frame.repaint();//Actualizamos
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "GuardarImagen":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    try {
                        this.manipulador.guardarImagen();
                    } catch (IOException ex) {
                        Logger.getLogger(MenuOptionImage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            case "EscalaGrises":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    this.manipulador.setEscalaGrisesImagen();
                    this.frame.revalidate();//Revalidamos
                    this.frame.repaint();//Actualizamos
                }
                break;
            }
            case "Negativo":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    this.manipulador.setNegativoImagen();
                    this.frame.revalidate();//Revalidamos
                    this.frame.repaint();//Actualizamos
                }
                break;
            }
            case "Binarizar":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                   //Primero obtenemos la escala de grises
                    this.manipulador.setEscalaGrisesImagen();
                    this.manipulador.setBinarizacionImagen(120);
                    this.frame.revalidate();//Revalidamos
                    this.frame.repaint();//Actualizamos
                    //Creamos un slider que modificará el umbral de la imagen
                    //Y lo agregamos a un nuevo JFrame
                    JSliderUmbral slider = null;
                    try {
                        slider = new JSliderUmbral(this.manipulador,0, 255, 15,"Binarizar");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                        JFrame frame_nuevo = new JFrame();
                        frame_nuevo.setVisible(true);
                        frame_nuevo.setTitle("Umbral para la binarización");
                        frame_nuevo.setSize(400, 120);
                        frame_nuevo.setLocationRelativeTo(null);
                        frame_nuevo.add(slider);
                }
                break;  
            }
            case "Iluminacion":{
                 if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                 JSliderUmbral slider = null;
                    try {
                        slider = new JSliderUmbral(this.manipulador,0, 255, 15,"Iluminar");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                        JFrame frame_nuevo = new JFrame();
                        frame_nuevo.setVisible(true);
                        frame_nuevo.setTitle("Umbral para la iluminación");
                        frame_nuevo.setSize(400, 120);
                        frame_nuevo.setLocationRelativeTo(null);
                        frame_nuevo.add(slider);
                }
                break;
            }
            case "AutoBinarizar":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    this.manipulador.setBinarizacionAutomatica();
                    this.frame.revalidate();//Revalidamos
                    this.frame.repaint();//Actualizamos
                }
                break;  
            }
            
            case "ExLineal":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    JSliderUmbral slider = null;
                    JSliderUmbral slider2 = null;
                    try {
                        slider = new JSliderUmbral(this.manipulador,0, 255, 15,"ExpansionLinealR1");
                        slider2 = new JSliderUmbral(this.manipulador,0,255,15,"ExpansionLinealR2");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                        JFrame frame_nuevo = new JFrame();
                        frame_nuevo.setLayout(new GridLayout(4, 1));
                        frame_nuevo.setVisible(true);
                        frame_nuevo.setTitle("Umbral para expansion lineal");
                        frame_nuevo.setSize(400, 120);
                        frame_nuevo.setLocationRelativeTo(null);
                        frame_nuevo.add(new JLabel("Valor R1"));
                        frame_nuevo.add(slider);
                        frame_nuevo.add(new JLabel("Valor R2"));
                        frame_nuevo.add(slider2);
                }
                break;
            }
            
            case "ExLog":{
                 if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    JSliderUmbral slider = null;
                    try {
                        slider = new JSliderUmbral(this.manipulador,0, 255, 15,"ExpansionLog");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                        JFrame frame_nuevo = new JFrame();
                        frame_nuevo.setVisible(true);
                        frame_nuevo.setTitle("Umbral para la expansion logarítmica");
                        frame_nuevo.setSize(400, 120);
                        frame_nuevo.setLocationRelativeTo(null);
                        frame_nuevo.add(slider);
                }
                break;
            }
            
            case "ExExponencial":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    JSliderUmbral slider = null;
                    try {
                        slider = new JSliderUmbral(this.manipulador,0, 255, 15,"ExpansionExp");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                        JFrame frame_nuevo = new JFrame();
                        frame_nuevo.setVisible(true);
                        frame_nuevo.setTitle("Umbral para la expansion exponencial");
                        frame_nuevo.setSize(400, 120);
                        frame_nuevo.setLocationRelativeTo(null);
                        frame_nuevo.add(slider);
                }
                break;
            }
            
            case "Ecualizar":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    this.manipulador.setEcualizacion();
                    this.frame.revalidate();//Revalidamos
                    this.frame.repaint();//Actualizamos
                }
                break;
            }
            
            case "MatrizConv":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    //Abrimos una nueva ventana para manipular la matriz
                    JFrameMatriz matrizConvolucion = new JFrameMatriz(this.manipulador);
                }
                break;
            }
            case "Ruido":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    JFrameRuido frame = new JFrameRuido(this.manipulador);
                }
                break;
            }
            case "FFT":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    JFrameFFT fft = new JFrameFFT(this.manipulador);
                    this.manipulador.getFrame().revalidate();
                    this.manipulador.getFrame().repaint();
                }
                break; 
            }
            case "FiltroBajaIdeal":{
                if(isLabelNull()){
                    JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE);
                }else{
                    this.manipulador.obtenerFiltroGaussiano_PasaAltas(25);
                    this.manipulador.getFrame().revalidate();
                    this.manipulador.getFrame().repaint();
                }
                break;
            }
            default:{
                if(!isLabelNull()){
                    this.manipulador.obtenerHistograma();
                    Graficador grafica = new Graficador(this.manipulador.getRed(), 
                                                        this.manipulador.getGreen(), 
                                                        this.manipulador.getBlue());
                    JFrame frameNuevo = new JFrame("Histograma");
                    frameNuevo.setVisible(true);
                    frameNuevo.setLocationRelativeTo(null);
                    frameNuevo.setSize(900, 400);
                    ChartPanel panel = grafica.getChartPanel();
                    frameNuevo.add(panel);
                }else{
                   JOptionPane.showMessageDialog(null, "Seleccione una imagen!","Cuidado",JOptionPane.WARNING_MESSAGE); 
                }
                
                break;
            }
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
       
    }
    private boolean isLabelNull(){
        return this.label == null;
    }
    //TODO
    /*
    -Histograma
    -Binarizacion(con jslider y poder actualizar al cambiarse el jslider)
    -Quitar fondo
    */
}
