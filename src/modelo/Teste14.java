/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.GammaDistributionImpl;

/**
 *
 * @author renatohidaka
 */
public class Teste14 {

    private int n;
    private double valor_p;
    private int[] X;
    private int[] S;
    private int J;
    private int[] S_linha;
    private double quiQuadrado;
    private List<List<Integer>> ciclos = new ArrayList<List<Integer>>();
    private static final double ALFA = 0.01;
    private int[] ciclos5 = new int[]{0, 0, 0, 0, 0, 0};
    private HashMap<Integer, int[]> map = new HashMap<Integer, int[]>();
    private GammaDistributionImpl gama;
    private double[][] pi = new double[][]{
        {0.0000000000, 0.0000000000, 0.0000000000, 0.0000000000, 0.0000000000, 0.0000000000},//só para o indice ficar ok
        {0.5000000000, 0.25000000000, 0.12500000000, 0.06250000000, 0.03125000000, 0.0312500000},
        {0.7500000000, 0.06250000000, 0.04687500000, 0.03515625000, 0.02636718750, 0.0791015625},
        {0.8333333333, 0.02777777778, 0.02314814815, 0.01929012346, 0.01607510288, 0.0803755143},
        {0.8750000000, 0.01562500000, 0.01367187500, 0.01196289063, 0.01046752930, 0.0732727051}
    };

    public boolean run(Integer[] e) throws MathException {

        n = e.length;
        J = 0;
        map.clear();
        ciclos.clear();
        X = new int[n];
        S = new int[n];
        S_linha = new int[n + 2];

        for (int i = 0; i < X.length; i++) {

            X[i] = (2 * e[i]) - 1;
        }
        S_linha[0] = 0;
        S[0] = X[0];
        S_linha[1] = S[0];

        for (int i = 1, j = 2; i < X.length; i++, j++) {

            S[i] = S[i - 1] + X[i];
            S_linha[j] = S[i];
        }

        S_linha[S_linha.length - 1] = 0;

        int aux = 0;
        aux = S_linha[0];
        List<Integer> subCiclo = new ArrayList<Integer>();
        for (int i = 1; i < S_linha.length; i++) {


            if (S_linha[i] == 0 && subCiclo.size() > 0) {
                J++;
                ciclos.add(subCiclo);
                subCiclo = new ArrayList<Integer>();

            } else {
                subCiclo.add(S_linha[i]);
            }

        }

        int cont = 0;

        for (int j = -4; j <= 4; j++) {

            if (j != 0) {
                ciclos5 = new int[]{0, 0, 0, 0, 0, 0};
                for (int i = 0; i < J; i++) {

                    cont = Collections.frequency(ciclos.get(i), j);
                    cont = cont > 5 ? 5 : cont;
                    ciclos5[cont]++;

                }

                map.put(j, ciclos5);

            }

        }

        for (int j = -4; j <= 4; j++) {

            if (j != 0) {
                quiQuadrado = 0;

                for (int i = 0; i < 6; i++) {

                    quiQuadrado += Math.pow(map.get(j)[i] - (J * pi[(int) Math.abs(j)][i]), 2) / (J * pi[(int) Math.abs(j)][i]);
                }

                gama = new GammaDistributionImpl(5 / 2d, 1);
                valor_p = 1 - gama.cumulativeProbability(quiQuadrado / 2d);

                if (valor_p < ALFA) {
                    return false;
                }
            }
        }

        return true;

    }

    public double getValor_p() {
        return valor_p;
    }
    
}
