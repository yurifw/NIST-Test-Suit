/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import java.util.Arrays;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.distribution.TDistributionImpl;

/**
 * <p>TESTE DISCRETO DA TRANSFORMADA DE FOURIER</p>
 * <p>Classe responsável por verificar se nas sequências de bits de entrada há características periódicas,
 * ou seja, padrões repetitivos que são próximos uns dos outros, que indicam um desvio proveniente das
 * hipóteses de aleatoriedade.</p>
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 11/05/2011
 */
public class Teste6 {

    private int[] x;
    private double[] S;
    private int N_1;
    private int n;
    private double T;
    private double N_0;
    private double phi;
    private double valor_p;
    private double d;
    private DoubleFFT_1D fft;
    private static final double ALFA = 0.01;
    private NormalDistributionImpl normal = new NormalDistributionImpl();
    private TDistributionImpl distT;

    public boolean runNormal(Integer[] e) throws MathException {

        n = e.length;
        x = new int[n];
        S = new double[n * 2];
        N_1 = 0;
        Arrays.fill(S, 0);

        for (int i = 0; i < n; i++) {

            x[i] = (2 * e[i]) - 1;
            S[i] = (2 * e[i]) - 1;
        }

        fft = new DoubleFFT_1D(n);

        fft.realForwardFull(S);
        T = Math.sqrt(2.995732274 * n);
        for (int i = 0; i < S.length / 2; i += 2) {

            if (Math.abs(S[i]) <= T) {
                N_1++;
            }

        }


        N_0 = 0.95 * n / 2;

        d = (N_1 - N_0) / Math.sqrt((n * 0.95 * 0.05) / 2);

        phi = normal.cumulativeProbability(Math.abs(d));
        valor_p = (1 - phi) * 2;

        return getValor_p() > ALFA;
    }

    public boolean runT(Integer[] e) throws MathException {

        n = e.length;
        x = new int[n];
        S = new double[n * 2];
        Arrays.fill(S, 0);

        for (int i = 0; i < n; i++) {

            x[i] = (2 * e[i]) - 1;
            S[i] = (2 * e[i]) - 1;
        }

        fft = new DoubleFFT_1D(n);

        fft.realForwardFull(S);

        double temp = 0;
        for (int i = 0; i < S.length / 2; i += 2) {
            temp = S[i];

        }

        N_1 = (int) Math.abs(Math.ceil(temp));
        N_0 = 0.95 * n / 2;

        d = (N_1 - N_0) / Math.sqrt((n * 0.95 * 0.05) / 2);

        distT = new TDistributionImpl(e.length - 1);
        phi = distT.cumulativeProbability(Math.abs(d));
        valor_p = (1 - phi) * 2;

        return getValor_p() > ALFA;
    }

    public double getValor_p() {
        return valor_p;
    }

}
