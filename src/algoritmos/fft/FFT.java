/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmos.fft;

import java.util.ArrayList;

/**
 *
 * @author depot
 */
public class FFT {
    
    //Definiendo los atributos de la clase
    private ArrayList<Double> muestras_x;//Aqui tendremos las muestras ingresadas por el usuario
    private ArrayList<Double> muestras_x_r;
    private final ArrayList<Double> transformada_X;//Aqui tendremos las transformadas de la muestra
    private final ArrayList<Double> transformadaR_X;
    private int N;

    public FFT(){
        this.muestras_x = new ArrayList<>();
        this.transformada_X = new ArrayList<>();
        this.transformadaR_X = new ArrayList<>();
        this.muestras_x_r = new ArrayList<>();
        this.N = 0;
    }
    
    public void operacion_TransformadaFourierD(){
      double parteImaginaria = 0;
      double parteReal = 0;
      double modulo_k = 0;
      double tam_Muestra = muestras_x.size();
      
        for(int valorK=0;valorK<tam_Muestra;valorK++){//k = 0,1,2,3...N-1 
         parteImaginaria = 0;
         parteReal = 0;
           for(int valorN = 0;valorN<tam_Muestra;valorN++){//Aqui es el ciclo de la sumatoria
               parteReal += (Math.cos((double)((2*Math.PI*valorK*valorN)/tam_Muestra)))*muestras_x.get(valorN);
               parteImaginaria += (Math.sin((double)(2*Math.PI*valorK*valorN)/tam_Muestra))*muestras_x.get(valorN);
           } 
           modulo_k = Math.sqrt((Math.pow(parteReal, 2))+Math.pow(parteImaginaria,2));//Obtenemos el modulo del numero imaginario
             if(modulo_k < 0.000001 && modulo_k > -0.000001){
                 modulo_k = 0;
             }
           this.transformada_X.add(valorK, modulo_k);
        }
    }
    
    public void operacion_TransformadaFourierR(){
        
        ArrayList<Double> aux = obtenerNuevasPosiciones(this.N);
        
        ArrayList<NumeroComplejo> auxiliarNuevosDatos = new ArrayList<>();
        for(int i=0;i<aux.size();i++){
            auxiliarNuevosDatos.add(new NumeroComplejo(aux.get(i), 0));
        }
         //En este ciclo primero agarramos de 2 en 2, luego de 4 en 4 y asi hasta agarrar la misma cantidad
         //que la cantidad de muestras totales, para saber cuantas muestras estamos agarrando, ese dato lo
         //tiene la variable cantidad_Muestras
         for(int cantidad_Muestras=2;cantidad_Muestras <= (int)Math.pow(2, this.N);cantidad_Muestras *= 2){
             ArrayList<NumeroComplejo> factores_Giro = new ArrayList<>();//Aqui tendremos los factores de giro para hacer las operaciones
             //Obteniendo factores de giro
             for(int numeroFactores_Giro = 0;numeroFactores_Giro<cantidad_Muestras/2;numeroFactores_Giro++){

                 double parteR = Math.cos((double)2*Math.PI*numeroFactores_Giro/cantidad_Muestras);
                 double parteI = Math.sin((double)2*Math.PI*numeroFactores_Giro/cantidad_Muestras);

                 NumeroComplejo numeroComplejo = new NumeroComplejo(parteR, parteI);
                 //agregamos ese factor de giro al ArrayList para poder hacer las operaciones
                 factores_Giro.add(numeroComplejo);
             }
             
             //Ahora hacemos las multiplicaciones, y los valores que obtengamos se reemplazaran en el ArrayList aux
             
             ArrayList<NumeroComplejo> listaTemporalNumeros = new ArrayList<>();
             //cantidad de diferentes bloques por operacion
             //un bloque sería Xo + (W_2 * X_4) y Xo - (W_2 + X_4), y como es de tamaño 2, y la muestra de 4, hara 4 operaciones y asi sucesivamente
             //si le toca hacer operaciones de tamaño 4, haría 2 operaciones y asi, si es del mismo tamaño que la muestra, solo 1 operacion
             int cantidadOperaciones = 0;
             
             for(int numeroBloque = 0;numeroBloque<aux.size()/cantidad_Muestras;numeroBloque++){
             int cantidadMuestrasOperadas = 0;
            //Obtenemos la cantidad de muestras del arreglo ya ordenado para hacer las operaciones, de 2 en 2, o de 4 en 4..etc
            //Suma 
             //[2,1,0,3] 0,1  2
               for(int lugarMuestra = numeroBloque*cantidad_Muestras;lugarMuestra<(numeroBloque*cantidad_Muestras)+cantidad_Muestras;lugarMuestra++){   
                    //Suma 
                 if(cantidadMuestrasOperadas<cantidad_Muestras/2){
                    double muestraReal = auxiliarNuevosDatos.get(lugarMuestra).getReal();
                    double muestraIm = auxiliarNuevosDatos.get(lugarMuestra).getImaginario();
                     
                    double giroRe = (factores_Giro.get(cantidadOperaciones).getReal());
                    double giroIm = (factores_Giro.get(cantidadOperaciones).getImaginario());
                     
                    double multiplicadorParteReal = auxiliarNuevosDatos.get((cantidad_Muestras/2)+cantidadOperaciones+(numeroBloque*cantidad_Muestras)).getReal()*giroRe;
                    double multiplicadorParteIm = auxiliarNuevosDatos.get((cantidad_Muestras/2)+cantidadOperaciones+(numeroBloque*cantidad_Muestras)).getImaginario()*giroIm*-1;
                    double multiplicadorReal_Im = auxiliarNuevosDatos.get((cantidad_Muestras/2)+cantidadOperaciones+(numeroBloque*cantidad_Muestras)).getReal()*giroIm;
                    double multiplicadorIm_Real = auxiliarNuevosDatos.get((cantidad_Muestras/2)+cantidadOperaciones+(numeroBloque*cantidad_Muestras)).getImaginario()*giroRe;
                    
                    double parteReal = muestraReal + (multiplicadorParteReal+multiplicadorParteIm);
                    double parteImag = muestraIm + (multiplicadorIm_Real + multiplicadorReal_Im);
                    
                    NumeroComplejo numComplejo = new NumeroComplejo(parteReal, parteImag);
                    listaTemporalNumeros.add(numComplejo);
                    cantidadOperaciones++;
                 }
                 
                  if(cantidadOperaciones == cantidad_Muestras/2){
                      cantidadOperaciones = 0;
                  }
                 cantidadMuestrasOperadas++;
                 //Cada ciclo es la operacion en mariposa de el arreglo del tamaño que estamos evaluando
               }
               //Resta 
                cantidadOperaciones = 0;
                cantidadMuestrasOperadas = 0;
                for(int lugarMuestra = (numeroBloque*cantidad_Muestras);lugarMuestra<(numeroBloque*cantidad_Muestras)+cantidad_Muestras;lugarMuestra++){  
                             //Suma 
                 if(cantidadMuestrasOperadas<cantidad_Muestras/2){
                    double muestraReal = auxiliarNuevosDatos.get(lugarMuestra).getReal();
                    double muestraIm = auxiliarNuevosDatos.get(lugarMuestra).getImaginario();

                    double giroRe = (factores_Giro.get(cantidadOperaciones).getReal());
                    double giroIm = (factores_Giro.get(cantidadOperaciones).getImaginario());

                    double multiplicadorParteReal = auxiliarNuevosDatos.get((cantidad_Muestras/2)+cantidadOperaciones+(numeroBloque*cantidad_Muestras)).getReal()*giroRe;
                    double multiplicadorParteIm = auxiliarNuevosDatos.get((cantidad_Muestras/2)+cantidadOperaciones+(numeroBloque*cantidad_Muestras)).getImaginario()*giroIm*-1;
                    double multiplicadorReal_Im = auxiliarNuevosDatos.get((cantidad_Muestras/2)+cantidadOperaciones+(numeroBloque*cantidad_Muestras)).getReal()*giroIm;
                    double multiplicadorIm_Real = auxiliarNuevosDatos.get((cantidad_Muestras/2)+cantidadOperaciones+(numeroBloque*cantidad_Muestras)).getImaginario()*giroRe;
                    
                    double parteReal = muestraReal - (multiplicadorParteReal+multiplicadorParteIm);
                    double parteImag = muestraIm - (multiplicadorIm_Real + multiplicadorReal_Im);
                     
                    NumeroComplejo numComplejo = new NumeroComplejo(parteReal, parteImag);
                    listaTemporalNumeros.add(numComplejo);   
                    cantidadOperaciones++;
                 }
                  if(cantidadOperaciones == cantidad_Muestras/2){
                      cantidadOperaciones = 0;
                  }
                 cantidadMuestrasOperadas++;
                 //Cada ciclo es la operacion en mariposa de el arreglo del tamaño que estamos evaluando
               } 
             }
           auxiliarNuevosDatos = listaTemporalNumeros;
         }
         
         //Obteniendo el modulo de el número complejo final
         for(int posicionArreglo = 0;posicionArreglo<auxiliarNuevosDatos.size();posicionArreglo++){
             this.transformadaR_X.add(Math.sqrt((Math.pow(auxiliarNuevosDatos.get(posicionArreglo).getReal(),2))+(Math.pow(auxiliarNuevosDatos.get(posicionArreglo).getImaginario(),2))));
         }  
    }
    public ArrayList<Double> getTransformadaDiscreta_X() {
        return this.transformada_X;
    }
    public ArrayList<Double> getTransformadaDiscretaR_X(){
        return this.transformadaR_X;
    }
    
    public void setMuestrasTransformada(ArrayList<Double> muestras){
        this.muestras_x = muestras;
    }
    
    public void setMuestrasTransformada_R(ArrayList<Double> muestras){
        this.muestras_x_r = muestras;
        this.N = (int)((double)Math.log(muestras.size())/(double)Math.log(2));
    }

    public ArrayList<Double> obtenerNuevasPosiciones(int n) {
       ArrayList<Double> aux = new ArrayList<>();
       int nuevaPosicion;
       for(int posicionMuestras=0;posicionMuestras<Math.pow(2, n);posicionMuestras++){//Obtenemos el nuevo arreglo con las posiciones invertidas    
           ArrayList<Integer> numBinario = posicionInversa(posicionMuestras);
           nuevaPosicion = obtenerNuevaPosicion(numBinario);
           aux.add(this.muestras_x_r.get(nuevaPosicion));
       }
       return aux;
    }  

    public ArrayList<Integer> posicionInversa(int posicionMuestras) {
        ArrayList<Integer> numeroBinario = new ArrayList<>();
        double operacionDivision = 0;
        int aux = posicionMuestras;
        
     if(posicionMuestras >= 1){
        do{
           numeroBinario.add(aux%2);//Si tiene residuo o no, si tiene residuo vale 1, si no, vale 0 
           operacionDivision = aux/2;//Dividimos y el resultado será el nuevo número a dividir, y asi obtenemos el numero entero sin el decimal
           aux = (int)operacionDivision;//nuevo numero a dividir
        }while(operacionDivision > 0);
     }else{
         numeroBinario.add(0);
     }
     //0
     if(numeroBinario.size() < this.N){//Para poner los 0 que faltan, si son 8 muestras, es 2^3, y como maximo son 3 dígitos que es el 8, 111
                                 //pero si obtenemos el 1, solamente tendriamos 1, y por eso le agregamos los ceros que faltan y que
                                 //el resultado sea 001
       for(int i=this.N-numeroBinario.size();i>=1;i--){
           numeroBinario.add(0);
       }  
     }
     return numeroBinario;
    }

    private int obtenerNuevaPosicion(ArrayList<Integer> posicionInversa) {
      int valor = 0;
      ArrayList<Integer> aux = new ArrayList<>();
      
      for(int i=0;i<posicionInversa.size();i++){//Volteando el numero binario, si vale 001, valdrá 100
         aux.add(posicionInversa.get(posicionInversa.size()-(i+1)));
      }
      for(int i=posicionInversa.size()-1;i>=0;i--){//Obteniendo el valor en decimal del valor en binario al revés
         valor += Math.pow(2, aux.size()-(i+1)) * posicionInversa.get(i);
      }  
      return valor;  
    }
}
