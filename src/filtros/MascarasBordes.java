/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import java.util.ArrayList;

/**
 *
 * @author depot
 */
public class MascarasBordes {
    // kirch
    private final double[][] kirsch1 = {{0,0,0,0,0},{0,-3, -3, 5,0}, {0,-3, 0, 5,0}, {0,-3, -3, 5,0},{0,0,0,0,0}};
    private final double[][] kirsch2 = {{0,0,0,0,0},{0,-3, 5, 5,0}, {0,-3, 0, 5,0}, {0,-3, -3, -3,0},{0,0,0,0,0}};
    private final double[][] kirsch3 = {{0,0,0,0,0},{0,5, 5, 5,0}, {0,-3, 0, -3,0}, {0,-3, -3, -3,0},{0,0,0,0,0}};
    private final double[][] kirsch4 = {{0,0,0,0,0},{0,5, 5, -3,0}, {0,5, 0, -3,0}, {0,-3, -3, -3,0},{0,0,0,0,0}};
    private final double[][] kirsch5 = {{0,0,0,0,0},{0,5, -3, -3,0}, {0,5, 0, -3,0}, {0,5, -3, -3,0},{0,0,0,0,0}};
    private final double[][] kirsch6 = {{0,0,0,0,0},{0,-3, -3, -3,0}, {0,5, 0, -3,0}, {0,5, 5, -3,0},{0,0,0,0,0}};
    private final double[][] kirsch7 = {{0,0,0,0,0},{0,-3, -3, -3,0}, {0,-3, 0, -3,0}, {0,5, 5, 5,0},{0,0,0,0,0}};
    private final double[][] kirsch8 = {{0,0,0,0,0},{0,-3, -3, -3,0}, {0,-3, 0, 5,0}, {0,-3, 5, 5,0},{0,0,0,0,0}};
    private final ArrayList<double[][]> arregloMascaras = new ArrayList<>();

    public MascarasBordes() {
        this.arregloMascaras.add(kirsch1);
        this.arregloMascaras.add(kirsch2);
        this.arregloMascaras.add(kirsch3);
        this.arregloMascaras.add(kirsch4);
        this.arregloMascaras.add(kirsch5);
        this.arregloMascaras.add(kirsch6);
        this.arregloMascaras.add(kirsch7);
        this.arregloMascaras.add(kirsch8);
    }
    
    public ArrayList<double[][]> getMascaras(){
        return this.arregloMascaras;
    }
   
}
