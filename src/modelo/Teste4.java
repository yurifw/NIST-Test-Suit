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
 * Teste para Maior Sequênica de bits 1’s em um Bloco</p>
 * <p>
 * Classe responsável por verificar se o comprimento da maior sequência de bits
 * 1’s da sequência testada está coerente com o comprimento de maior sequência
 * de bits 1’s esperado em uma sequência aleatória</p>
 *
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 11/06/2010
 */
public class Teste4 {

    private int n;
    private double N;
    private int k;
    private int M;
    private double quiQuadrado;
    private double valor_p;
    private int[] v = new int[7];
    private GammaDistributionImpl gama;
    private double[] piM8 = new double[]{0.2148, 0.3672, 0.2305, 0.1875};
    private List<SubBloco> subBlocos = new ArrayList<SubBloco>();
    private static final double ALFA = 0.01;

    public boolean run(Integer[][] e) throws MathException {

        valor_p = 0;

        for (int i = 0; i < e.length; i++) {
            n = e[i].length;
            M = 0;
            if (n < 6272 && (n % 8) == 0) {
                M = 8;
            } else if (n < 750000 && (n % 128) == 0) {
                M = 128;
            } else if ((n % Math.pow(10, 4)) == 0) {
                M = (int) Math.pow(10, 4);
            }

            if (M == 0) {
                return false;
            }
            subBlocos.clear();
            Arrays.fill(v, 0);
            quiQuadrado = 0;

            for (int j = 0; j < e[i].length; j += M) {

                int[] temp = new int[M];
                int maior = 0;
                int comprimeto = 0;

                for (int k = j, x = 0; k < M + j; k++, x++) {

                    temp[x] = e[i][k];

                    if (temp[x] == 1) {

                        comprimeto += 1;

                    } else {
                        comprimeto = 0;
                    }

                    if (comprimeto > maior) {

                        maior = comprimeto;

                    }

                }

                subBlocos.add(new SubBloco(temp, maior));
            }

            V(M);

            // implementar os outros Ms
            gama = new GammaDistributionImpl(k / 2d, 1); // verificar os parametros
            valor_p += 1 - gama.cumulativeProbability(quiQuadrado / 2d);

        //System.out.println("qui "+quiQuadrado);
            // System.out.println("Valor P "+valor_p);
        }
        valor_p = valor_p / e.length;
        return valor_p > ALFA;
    }

    private void V(int M) {

        if (M == 8) {

            M8();
            k = 3;
            N = 16;

            for (int i = 0; i < k + 1; i++) {

                quiQuadrado += Math.pow(v[i] - (N * piM8[i]), 2) / (N * piM8[i]);

            }

        } else if (M == 128) {

            M128();

        } else {

            M10000();
        }

    }

    private void M8() {

        for (SubBloco bl : subBlocos) {

            if (bl.maiorSequencia <= 1) {

                v[0] += 1;

            } else if (bl.maiorSequencia == 2) {

                v[1] += 1;

            } else if (bl.maiorSequencia == 3) {

                v[2] += 1;

            } else if (bl.maiorSequencia >= 4) {

                v[3] += 1;
            }
        }
    }

    private void M128() {

        for (SubBloco bl : subBlocos) {

            if (bl.maiorSequencia <= 4) {

                v[0] += 1;

            } else if (bl.maiorSequencia == 5) {

                v[1] += 1;

            } else if (bl.maiorSequencia == 6) {

                v[2] += 1;

            } else if (bl.maiorSequencia == 7) {

                v[3] += 1;

            } else if (bl.maiorSequencia == 8) {

                v[4] += 1;

            } else if (bl.maiorSequencia >= 9) {

                v[5] += 1;
            }
        }
    }

    private void M10000() {

        for (SubBloco bl : subBlocos) {

            if (bl.maiorSequencia <= 10) {

                v[0] += 1;

            } else if (bl.maiorSequencia == 11) {

                v[1] += 1;

            } else if (bl.maiorSequencia == 12) {

                v[2] += 1;

            } else if (bl.maiorSequencia == 13) {

                v[3] += 1;

            } else if (bl.maiorSequencia == 14) {

                v[4] += 1;

            } else if (bl.maiorSequencia == 15) {

                v[5] += 1;

            } else if (bl.maiorSequencia >= 16) {

                v[6] += 1;
            }
        }
    }

    private class SubBloco {

        int[] subBloco;
        int maiorSequencia;

        public SubBloco(int[] subBloco, int maiorSequencia) {

            this.subBloco = subBloco;
            this.maiorSequencia = maiorSequencia;
        }

    }

    public double getValor_p() {
        return valor_p;
    }

}
