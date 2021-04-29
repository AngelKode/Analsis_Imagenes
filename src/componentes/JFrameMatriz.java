/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import imagenes.ManipulacionImagen;;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import listener.ListenerBotonConvolucion;
import org.jfree.ui.Spinner;

/**
 *
 * @author depot
 */
public final class JFrameMatriz extends JFrame{
    
    private static final int tamanioMatriz = 5;
    private final Spinner[][] matriz;
    private final Spinner divisor, offset;
    private final JButton btnOK;
    private final JRadioButton rgb[];
    private final JPanel panelPrincipal,panelMatrizPrincipal, panelMatriz ,panelBotones, panelConfiguraciones, panelCanales, panelOpcionesCanales;
    private final ManipulacionImagen manipulador;
    
    public JFrameMatriz(ManipulacionImagen manipulador){
        this.matriz = new Spinner[tamanioMatriz][tamanioMatriz];
        this.btnOK = new JButton("Aceptar");
        
        this.rgb = new JRadioButton[3];
        this.rgb[0] = new JRadioButton("Red");
            this.rgb[0].setSelected(true);
        this.rgb[1] = new JRadioButton("Green");
            this.rgb[1].setSelected(true);
        this.rgb[2] = new JRadioButton("Blue");
            this.rgb[2].setSelected(true);
        
        this.panelPrincipal = new JPanel();
        this.panelPrincipal.setLayout(new BoxLayout(this.panelPrincipal, BoxLayout.Y_AXIS));
        this.panelMatrizPrincipal = new JPanel();
        
        this.panelMatriz = new JPanel(new GridLayout(tamanioMatriz, tamanioMatriz,15,15));
        this.panelMatriz.setBorder(BorderFactory.createEmptyBorder(15,15,0,15));
        
        this.panelBotones = new JPanel();
        this.panelBotones.setLayout(new BoxLayout(this.panelBotones, BoxLayout.X_AXIS));
        
        this.panelConfiguraciones = new JPanel();
        
        this.panelCanales = new JPanel(new GridLayout(2, 1));
        this.panelOpcionesCanales = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        this.manipulador = manipulador;
        this.divisor = new Spinner(0);
        this.offset = new Spinner(0);

        initListeners();
        initComponents();
    }
    
    public void initListeners(){
        ListenerBotonConvolucion listener = new ListenerBotonConvolucion(this.manipulador, this.matriz, this.divisor,this.offset, this.rgb);
        this.btnOK.addActionListener(listener);
        this.btnOK.setActionCommand("btnOK");
    }
    public void initComponents(){
        
        //Creamos los Spinner para ingresar los valores de la matriz
        for(int fila = 0; fila < tamanioMatriz; fila++){
            for(int columna = 0; columna < tamanioMatriz; columna++){
                Spinner spinnerAux = new Spinner(0);
                this.matriz[fila][columna] = spinnerAux;//Lo agregamos a la matriz
                this.panelMatriz.add(spinnerAux);//Agregamos al panel
            }
        }
        
        //Lo agregamos al principal
        JLabel tituloMatriz = new JLabel("Matriz");
            tituloMatriz.setFont(new Font("Arial", Font.BOLD, 16));
            tituloMatriz.setHorizontalAlignment(JLabel.CENTER);
            tituloMatriz.setVerticalAlignment(JLabel.CENTER);
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.X_AXIS));
        panelTexto.add(tituloMatriz);
        this.panelMatrizPrincipal.setLayout(new BoxLayout(this.panelMatrizPrincipal, BoxLayout.Y_AXIS));
        this.panelMatrizPrincipal.add(panelTexto);
        this.panelMatrizPrincipal.add(this.panelMatriz);
        
        //Agregamos las configuraciones del divisor y el offset
        //Primero agregamos los jlabels
        this.panelConfiguraciones.setLayout(new BoxLayout(this.panelConfiguraciones, BoxLayout.X_AXIS));
        
        JPanel panelTituloConfiguraciones = new JPanel();
        panelTituloConfiguraciones.setLayout(new GridLayout(1, 2));
        
        //Paneles que contendrán el JLabel y el Spinner del divisor y el offset
        
        //-----------------------------DIVISOR------------------------------
        JPanel panelDivisor = new JPanel();
            panelDivisor.setLayout(new BoxLayout(panelDivisor, BoxLayout.Y_AXIS));
        JPanel panelLabelDivisor = new JPanel();
        JLabel labelDivisor = new JLabel("Divisor");
            labelDivisor.setHorizontalAlignment(JLabel.CENTER);
            panelLabelDivisor.add(labelDivisor);
            //Agregamos al panelDivisor el titulo y el Spinner
            panelDivisor.add(panelLabelDivisor);
            panelDivisor.add(this.divisor);
            panelDivisor.setBorder(BorderFactory.createEmptyBorder(15, 15,15,15));
        //-----------------------------Offset------------------------------    
        JPanel panelOffset = new JPanel();
            panelOffset.setLayout(new BoxLayout(panelOffset, BoxLayout.Y_AXIS));
        JPanel panelLabelOffset = new JPanel();
        JLabel labelOffset = new JLabel("Offset");
            labelOffset.setHorizontalAlignment(JLabel.CENTER);
            panelLabelOffset.add(labelOffset);
            //Agregamos al panelOffset el titulo y el Spinner
            panelOffset.add(panelLabelOffset);
            panelOffset.add(this.offset);
            panelOffset.setBorder(BorderFactory.createEmptyBorder(15, 15,15,15));
        
        //Agregamos al panel principal ambos paneles
        this.panelConfiguraciones.add(panelDivisor);
        this.panelConfiguraciones.add(panelOffset);
        
        //Agregamos la configuracion de los canales
        //Configuramos el JLabel
        JLabel tituloCanales = new JLabel("Canales");
            tituloCanales.setHorizontalAlignment(JLabel.CENTER);
            tituloCanales.setFont(new Font("Arial", Font.BOLD, 16));
        //Agregamos el JLabel al panel
        this.panelCanales.add(new JPanel().add(tituloCanales));
        //Agegamos las opciones al panel que contiene las opciones
        this.panelOpcionesCanales.add(this.rgb[0]);
        this.panelOpcionesCanales.add(this.rgb[1]);
        this.panelOpcionesCanales.add(this.rgb[2]);
        //Agregamos el panel de las opciones al panel principal
        this.panelCanales.add(this.panelOpcionesCanales);
        
        //Agregamos el boton al panel de botones
        this.panelBotones.add(this.btnOK);
        this.panelBotones.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        //Agregamos al panel principal todos los paneles
        this.panelPrincipal.add(this.panelMatrizPrincipal);
        this.panelPrincipal.add(this.panelConfiguraciones);
        this.panelPrincipal.add(this.panelCanales);
        this.panelPrincipal.add(this.panelBotones);
        
        //Agregamos al JFrame el panel que contiene todos los paneles
        this.add(this.panelPrincipal);
        
        //Configuramos la ventana principal
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setSize(400, 420);
        this.setResizable(false);
        this.setTitle("Matriz de Convolución");
    }
    
}
