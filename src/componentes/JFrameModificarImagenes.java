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
        
        //Menus principales
        ArrayList<String> menus = new ArrayList<>();
        menus.add("Archivo");
        menus.add("Editar Imagen");
        
        //SubMenus con sus respectivos listeners
        ArrayList<String[]> items = new ArrayList<>();
        ArrayList<String[]> actionCommandsItems = new ArrayList<>();
        
        //Creamos los items del primer menu
        items.add(new String[]{"Abrir imagen"});
        //Accion que detectará el item listener
        actionCommandsItems.add(new String[]{"AbrirImagen"});
        
        //Creamos los items del segundo menu
        items.add(new String[]{"Escala de Grises", "Negativo","Binarizar","Binarizacion Automática","Modificar Iluminación",
                               "Histograma de la imagen","Expansion Lineal","Expansion Logarítmica","Expansion Exponencial","Ecualizacion",
                               "Matriz de Convolución"});
        //Accion que detectará el item listener del segundo menú
        actionCommandsItems.add(new String[]{"EscalaGrises","Negativo","Binarizar","AutoBinarizar","Iluminacion",
                                "Histograma","ExLineal","ExLog","ExExponencial","Ecualizar","MatrizConv"});
        
        //Creamos un objeto con los menus y submenus anteriores
        MenuOptionImage menu_object = new MenuOptionImage(menus, items,actionCommandsItems,this);
        //Seteamos el JMenuBar
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
