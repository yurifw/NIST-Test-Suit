/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.GammaDistributionImpl;

/**
 * <p>
 * Teste do Correspondente Modelo Sobreposto</p>
 * <p>
 * Classe responsável por verificar se a sequência de bits produzida por
 * geradores é uma sequência padrão não-periódico</p>
 *
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 05/07/2010
 */
public class Teste8 {

    private int n;
    private int m;
    private int M;
    private int[] W;
    private double media;
    private double variancia;
    private double quiQuadrado;
    private double valor_p;
    private GammaDistributionImpl gama;
    private List<int[]> blocos = new ArrayList<int[]>();
    private static final double ALFA = 0.01;

    public boolean run(Integer[][] e, int N, String B) throws MathException {

        valor_p = 0;
        for (int l = 0; l < e.length; l++) {
            n = e[l].length;
            M = n / N;
            m = B.length();
            W = new int[N];
            Arrays.fill(W, 0);
            quiQuadrado = 0;
            blocos.clear();

            for (int i = 0, k = 0; i < N; i++) {

                int[] bl1 = new int[M];

                for (int j = 0; j < M; j++, k++) {

                    bl1[j] = e[l][k];

                }

                blocos.add(bl1);
            } // for i

            String temp = null;

            for (int i = 0; i < N; i++) {

                int[] bl2 = blocos.get(i);

                for (int j = 0; j < M - m + 1; j++) {

                    temp = "";
                    for (int k = j; k < m + j; k++) {

                        temp = temp.concat(String.valueOf(bl2[k]));
                    }

                    if (temp.equals(B)) {
                        W[i]++;
                    }
                }

            } //for i

            media = (M - m + 1) / Math.pow(2, m);
            variancia = M * ((1 / Math.pow(2, m)) - ((2 * m - 1) / Math.pow(2, 2 * m)));

            for (int i = 0; i < N; i++) {

                quiQuadrado += Math.pow(W[i] - media, 2) / variancia;
            }

            gama = new GammaDistributionImpl(N / 2d, 1);
            valor_p += 1 - gama.cumulativeProbability(quiQuadrado / 2d);

        }
        valor_p = valor_p/e.length;
        return valor_p > ALFA;

    }

    public double getValor_p() {
        return valor_p;
    }

}
