/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.GammaDistributionImpl;
import util.GaussJordan;

/**
 * <p>
 * Teste da Posto da Matriz Binária</p>
 * <p>
 * Classe responsável por verificar se há dependência linear entre o comprimento
 * fixo de subsequências de bits da sequência original</p>
 *
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 30/06/2010
 */
public class Teste5 {

    private int n;
    private int N;
    private double valor_p;
    private List<Matriz> matrizes = new ArrayList<Matriz>();
    private GaussJordan gj = new GaussJordan();
    private int fm;
    private int fm_1;
    private double quiQuadrado;
    private GammaDistributionImpl gama;
    private static final double ALFA = 0.01;

    public boolean run(Integer[][] e, int M, int Q) throws MathException {
        valor_p = 0;

        for (int i = 0; i < e.length; i++) {
            
            n = e[i].length;
            N = (int) Math.floor(n / (M * Q));
            matrizes.clear();
            fm = 0;
            fm_1 = 0;

            for (int j = 0, x = 0; j < N; j++) {

                double[][] matriz = new double[M][Q];

                for (int k = 0; k < M; k++) {
                    for (int l = 0; l < Q; l++, x++) {

                        matriz[k][l] = e[i][x];

                    }
                }

                matrizes.add(new Matriz(matriz, M, Q));
            } //for k

            for (int j = 0; j < matrizes.size(); j++) {

                int rank = gj.run(matrizes.get(j).getMatriz(), matrizes.get(j).getLinha(), matrizes.get(j).getColuna());
                matrizes.get(j).setRank(rank);

                if (rank == M) {
                    fm++;
                }

                if (rank == (M - 1)) {
                    fm_1++;
                }

            }

            quiQuadrado = (Math.pow(fm - (0.2888 * N), 2) / (0.2888 * N))
                    + (Math.pow(fm_1 - (0.5776 * N), 2) / (0.5776 * N))
                    + (Math.pow(N - fm - fm_1 - (0.1336 * N), 2) / (0.1336 * N));

            gama = new GammaDistributionImpl(N / 2d, 1);
            valor_p += 1 - gama.cumulativeProbability(quiQuadrado / 2d);
        }
        valor_p = valor_p / e.length;
        return valor_p > ALFA;
    }

    public double getValor_p() {
        return valor_p;
    }

    private class Matriz {

        private int linha;
        private int coluna;
        private double[][] matriz;
        private int rank;

        public Matriz(double[][] matriz, int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
            this.matriz = matriz;
        }

        public int getColuna() {
            return coluna;
        }

        public int getLinha() {
            return linha;
        }

        public double[][] getMatriz() {
            return matriz;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getRank() {
            return rank;
        }

    }

}
