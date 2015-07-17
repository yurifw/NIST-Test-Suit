/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Arrays;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.GammaDistributionImpl;

/**
 *
 * @author renatohidaka
 */
public class Teste10 {

    private int n;
    private int N;
    private double media;
    private int[] L;
    private double[] T;
    private int[] v = new int[7];
    private double quiQuadrado;
    private double valor_p;
    private int k = 6;
    private double[] pi = new double[]{0.01047, 0.03125, 0.125, 0.5, 0.25, 0.0625, 0.020883};
    private GammaDistributionImpl gama;
    private static final double ALFA = 0.01;

    public boolean run(Integer[][] e, int M) throws MathException {
        valor_p = 0;
        for (int l = 0; l < e.length; l++) {
            n = e[l].length;
            N = n / M;
            L = new int[N];
            T = new double[N];
            Arrays.fill(v, 0);
            int[] vetor;

            for (int i = 0, bl = 0; i < L.length; i++, bl += M) {

                vetor = new int[M];
                for (int j = bl, r = 0; j < M + bl; j++, r++) {
                    vetor[r] = e[l][j];
                }

                L[i] = BerlekampMassey(vetor);
            }

            media = (M / 2d) + ((9 + Math.pow(-1, M + 1)) / 36d) - ((M / 3d - 2 / 9d) / Math.pow(2, M));

            for (int i = 0; i < T.length; i++) {

                T[i] = Math.pow(-1, M) * (L[i] - media) + (2 / 9d);

            }

            tabelaV(T);
            quiQuadrado = 0;
            for (int i = 0; i <= k; i++) {

                quiQuadrado += Math.pow(v[i] - (N * pi[i]), 2) / (N * pi[i]);
            }

            gama = new GammaDistributionImpl(k / 2d, 1);

            valor_p += 1 - gama.cumulativeProbability(quiQuadrado / 2d);

        }
        valor_p = valor_p / e.length;
        return valor_p > ALFA;
    }

    public int BerlekampMassey(int[] s) {

        int L_, N_, m, d;
        int n_ = s.length;
        int[] c = new int[n_];
        int[] b = new int[n_];
        int[] t = new int[n_];

        b[0] = c[0] = 1;
        N_ = L_ = 0;
        m = -1;

        //Algorithm core
        /*
         * INPUT: a binary sequence sn = s0 , s1 , s2 , . . . , sn−1 of length n.
         OUTPUT: the linear complexity L(sn ) of sn , 0 ≤ L(sn ) ≤ n.
         1. Initialization. C(D)←1, L←0, m← − 1, B(D)←1, N ←0.
         2. While (N < n) do the following:
         2.1 Compute the next discrepancy d. d←(sN + somatŕio ci sN −i ) mod 2.
         2.2 If d = 1 then do the following:
         T (D)←C(D), C(D)←C(D) + B(D) · DN −m .
         If L ≤ N/2 then L←N + 1 − L, m←N , B(D)←T (D).
         2.3 N ←N + 1.
         3. Return().
         */
        while (N_ < n_) {
            d = s[N_];
            for (int i = 1; i <= L_; i++) {
                d ^= c[i] & s[N_ - i];            //(d+=c[i]*s[N-i] mod 2)
            }
            if (d == 1) {
                t = c.clone();    //T(D)<-C(D)
                for (int i = 0; (i + N_ - m) < n_; i++) {
                    c[i + N_ - m] ^= b[i]; //C(D)←C(D) + B(D) · DN −m

                }
                if (L_ <= (N_ >> 1)) { //L ≤ N/2

                    L_ = N_ + 1 - L_;
                    m = N_;
                    b = c.clone();   //B(D)<-T(D)
                }
            }
            N_++;
        }
        return L_;
    }

    private void tabelaV(double[] T) {

        for (int i = 0; i < T.length; i++) {

            if (T[i] <= -2.5d) {
                v[0]++;

            } else if (-2.5d < T[i] && T[i] <= -1.5d) {
                v[1]++;

            } else if (-1.5d < T[i] && T[i] <= -0.5d) {
                v[2]++;

            } else if (-0.5d < T[i] && T[i] <= 0.5d) {
                v[3]++;

            } else if (0.5d < T[i] && T[i] <= 1.5d) {
                v[4]++;

            } else if (1.5d < T[i] && T[i] <= 2.5d) {
                v[5]++;

            } else if (T[i] > 2.5d) {
                v[6]++;
            }
        }
    }

    public double getValor_p() {
        return valor_p;
    }
}
