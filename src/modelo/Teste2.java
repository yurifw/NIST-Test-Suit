/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.apache.commons.math.MathException;
import org.apache.commons.math.MathRuntimeException;
import org.apache.commons.math.distribution.GammaDistributionImpl;

/**
 * <p>Teste de Frequência em Bloco</p>
 * <p>Classe responsável por verificar se a frequência de “1”s em um Bloco com M-bits é, aproximadamente, M/2</p>
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 10/06/2010
 */
public class Teste2 {

    private int n;
    private double quiQuadrado;
    private int N;
    private double[] phi;
    private double valor_p;
    private GammaDistributionImpl gama;
    private static final double ALFA = 0.01;

    /**
     * <p>Método que recebe um conjunto de zeros e uns e o número de blocos para verifica a aleatoiedade</p>
     * @author Renato Hidaka (renatohidaka@gmail.com)
     * @version 1.0
     * @created 10/06/2010
     * @param int[] conjunto de bit
     * @return se valor_p maior que 0,01 conjuto de bit é aleatório
     */
    public boolean run(Integer[] e, int M) throws MathException {

        n = e.length;
        N = (int) Math.floor(n / M);
        phi = new double[N];

        for (int i = 0; i < phi.length; i++) {

            for (int j = 0; j < M; j++) {

                phi[i] += e[(i) * M + j];

            }

            phi[i] /= M;

        }

        double temp = 0.0;

        for (int i = 0; i < N; i++) {

            temp += Math.pow(phi[i] - 0.5, 2);

        }

        quiQuadrado = 4 * M * temp;

        gama = new GammaDistributionImpl(N / 2d, 1);
        valor_p = 1 - gama.cumulativeProbability(quiQuadrado / 2d);

        return valor_p > ALFA;

    }

    public double getValor_p() {
        return valor_p;
    }
    
}
