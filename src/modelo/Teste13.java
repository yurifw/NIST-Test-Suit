/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;

/**
 *
 * @author renatohidaka
 */
public class Teste13 {

    private int n;
    private double valor_p;
    private int[] X;
    private int[] S;
    private NormalDistributionImpl normal = new NormalDistributionImpl();
    private static final double ALFA = 0.01;

    public boolean run(Integer[][] e, int mode) throws MathException {
        valor_p = 0;
        for (int l = 0; l < e.length; l++) {
            n = e[l].length;
            X = new int[n];
            S = new int[n];

            for (int i = 0; i < e[l].length; i++) {

                X[i] = (2 * e[l][i]) - 1;
            }

            int z = 0;

            if (mode == 0) {

                S[0] = X[0];
                z = S[0];

                for (int i = 1; i < X.length; i++) {

                    S[i] = S[i - 1] + X[i];

                    if (S[i] > z) {
                        z = S[i];
                    }
                }

            } else { //mode = 1

                S[0] = X[X.length - 1];
                z = S[0];

                for (int i = X.length - 2, j = 1; i >= 0; i--, j++) {

                    S[j] = S[j - 1] + X[i];

                    if (S[j] > z) {
                        z = S[j];
                    }
                }
            }

            double aux1 = 0;
            double aux2 = 0;
            double var1 = 0;
            double var2 = 0;
            if (z == 0) {
                z = 1;
            }
            for (int k = ((-n / z) + 1) / 4; k <= ((n / z) - 1) / 4; k++) {

                aux1 = normal.cumulativeProbability(((4 * k + 1) * z) / Math.sqrt(n));
                aux2 = normal.cumulativeProbability(((4 * k - 1) * z) / Math.sqrt(n));
                var1 += aux1 - aux2;
            }

            for (int k = ((-n / z) - 3) / 4; k <= ((n / z) - 1) / 4; k++) {

                aux1 = normal.cumulativeProbability(((4 * k + 3) * z) / Math.sqrt(n));
                aux2 = normal.cumulativeProbability(((4 * k + 1) * z) / Math.sqrt(n));
                var2 += aux1 - aux2;
            }

            valor_p += 1 - var1 + var2;
        }
        valor_p = valor_p/e.length;
        return valor_p > ALFA;
    }

    public double getValor_p() {
        return valor_p;
    }
}
