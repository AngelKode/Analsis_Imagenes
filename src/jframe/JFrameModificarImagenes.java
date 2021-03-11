/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jframe;

import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author depot
 */
public class JFrameModificarImagenes extends JFrame{
    
    private final JMenuBar menu_bar;
    private final JMenu menu_item;
    private final JMenuItem item_of_menu;

    public JFrameModificarImagenes(JLabel label, Dimension d) throws HeadlessException {
        super.setVisible(true);
        initComponents(label, d);
        
        //Instanciamos el menu principal
        this.menu_bar = new JMenuBar();
        //Instanciamos los items de la barra del menu
        this.menu_item = new JMenu("Archivo");
        //Sub items
        this.item_of_menu = new JMenuItem("Abrir imagen");
        
        this.menu_item.add(this.item_of_menu);
        this.menu_bar.add(this.menu_item);
        
        //Agregamos el menu al JFrame
        this.setJMenuBar(this.menu_bar);
    }

    private void initComponents(JLabel label, Dimension d){
       this.setVisible(true);
       this.add(label);
       this.setTitle("Modificar imagen");
       this.setVisible(true);
       this.setBounds(0,0,d.width,d.height);
       this.setLocationRelativeTo(null);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    
}
