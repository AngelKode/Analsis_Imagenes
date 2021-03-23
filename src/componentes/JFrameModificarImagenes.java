/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

import listener.MenuOptionImage;

/**
 *
 * @author depot
 */
public class JFrameModificarImagenes extends JFrame{

    public JFrameModificarImagenes(Dimension d) throws HeadlessException, IOException {
        
        super.setVisible(true);
        initComponents(d);
        
        ArrayList<String> menus = new ArrayList<>();
        menus.add("Archivo");
        menus.add("Editar Imagen");
        ArrayList<String[]> items = new ArrayList<>();
        ArrayList<String[]> actionCommandsItems = new ArrayList<>();
        items.add(new String[]{"Abrir imagen"});
        actionCommandsItems.add(new String[]{"AbrirImagen"});//Accion que detectará el item listener
        items.add(new String[]{"Escala de Grises", "Negativo","Binarizar","Histograma de la imagen"});
        actionCommandsItems.add(new String[]{"EscalaGrises","Negativo","Binarizar","Histograma"});//Accion que detectará el item listener
        
        MenuOptionImage menu_object = new MenuOptionImage(menus, items,actionCommandsItems,this);
        this.setJMenuBar(menu_object);
        //Validamos lo que se agregó y refrescamos el jframe
        this.validate();
        this.repaint();
    }

    private void initComponents(Dimension d){
       this.setVisible(true);
       this.setTitle("Modificar imagen");
       this.setVisible(true);
       this.setBounds(0,0,d.width,d.height);
       this.setLocationRelativeTo(null);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
}
