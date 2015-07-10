/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.GammaDistributionImpl;

/**
 *
 * @author renatohidaka
 */
public class Teste12 {

    private int n;
    private HashMap<String, Integer> Lbit;
    private double[] freqRelativa;
    private double sigma;
    private double valor_p;
    private double quiQuadrado;
    private GammaDistributionImpl gama;
    private static final double ALFA = 0.01;

    public boolean run(Integer[] temp, int m) throws MathException {

        double sigma1 = passo1_4(temp, m);
        double sigma2 = passo1_4(temp, m+1);

        quiQuadrado = (Math.log(2) - (sigma1 - sigma2));//verificar o 2*n --->2*n*(Math.log(2) - (sigma1 - sigma2));

        gama = new GammaDistributionImpl(Math.pow(2, m-1), 1);
        valor_p = 1 - gama.cumulativeProbability(quiQuadrado/2d);        

        return valor_p > ALFA;
    }

    public double passo1_4(Integer[] temp, int m) {

        Integer[] e = new Integer[temp.length + m - 1];
        n = temp.length;
        sigma = 0;
        Arrays.fill(e, 0);
        System.arraycopy(temp, 0, e, 0, temp.length);
        Lbit = new HashMap<String, Integer>();
        freqRelativa = new double[(int) Math.pow(2, m)];

        for (int i = 0, j = temp.length; i < m-1; i++, j++) {

            e[j] = temp[i];
        }

        montaTabela(m);
        String txt = "";
        for (int i = 0; i < e.length - (m - 1); i++) {

            txt = "";
            for (int j = i; j < m+i; j++) {

                txt = txt.concat(String.valueOf(e[j]));
            }

            Lbit.put(txt, Lbit.get(txt)+1);
        }

        TreeSet<String> tree = new TreeSet<String>(Lbit.keySet());
        int cont = 0;
        
        for (String string : tree) {
            
            freqRelativa[cont] = (double) Lbit.get(string) / n;
            cont++;
        }

        for (int i = 0; i < freqRelativa.length; i++) {

            if(freqRelativa[i] != 0){
            sigma += (freqRelativa[i]*Math.log(freqRelativa[i]));
            }
        }
        
        return sigma;
    }
    
    private void montaTabela(int n) {

        int[][] tabela = new int[(int) Math.pow(2, n)][n];
        for (int i = n - 1, j = 0, coluna = 0; i >= 0; i--, coluna++) {

            if (j < 2) {
                j++;
            } else {
                j *= 2;
            }

            tabela = tablaVerdade((int) (Math.pow(2, i)), n, j, tabela, coluna);

        }

        String temp = null;
        for (int i = 0; i < Math.pow(2, n); i++) {

            temp = "";
            for (int j = 0; j < n; j++) {

                temp = temp.concat(String.valueOf(tabela[i][j]));

            }

            Lbit.put(temp, 0);
        }
    }

    private int[][] tablaVerdade(int pontoDetroca, int n, int iteracao, int[][] tabela, int coluna) {

        for (int i = 0, aux = 0, j = 0, linha = 0; j < iteracao; j++, i = 0, aux = 0) {

            //zero
            while (aux != pontoDetroca) {
                tabela[linha][coluna] = 0;
                i++;
                aux++;
                linha++;
            }
            aux = 0;
            //um
            while (aux != pontoDetroca) {

                tabela[linha][coluna] = 1;
                i++;
                aux++;
                linha++;

            }
        }

        return tabela;

    }


     public double getValor_p() {
        return valor_p;
    }

}
