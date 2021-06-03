/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmos.fft;

/**
 *
 * @author depot
 */
public class NumeroComplejo {
    
    private final double imaginario;
    private final double real;

    public NumeroComplejo(double parteR, double parteI) {
        this.imaginario = parteI;
        this.real = parteR;
    }
    
    public double getImaginario() {
        return this.imaginario;
    }

    public double getReal() {
        return this.real;
    }
}
