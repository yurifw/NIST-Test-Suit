/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.GammaDistributionImpl;

/**
 *
 * @author renatohidaka
 */
public class Teste11 {

    private int n;
    private HashMap<String, Integer> Lbit;
    private List<HashMap<String, Integer>> tabelas = new ArrayList<HashMap<String, Integer>>();
    private double[] phi;
    private double dfp; //Diferença Finitade de Primeira Ordem
    private double dfs; //Diferença Finita de Segunda Ordem
    private GammaDistributionImpl gama;
    private double valor_p1;
    private double valor_p2;
    private static final double ALFA = 0.01;

    public boolean run(Integer[][] temp, int m) throws MathException {
        valor_p1 = 0;
        valor_p2 = 0;
        for (int l = 0; l < temp.length; l++) {

            Integer[] e = new Integer[temp[l].length + m - 1];
            phi = new double[m];
            n = temp[l].length;
            Arrays.fill(e, 0);
            System.arraycopy(temp[l], 0, e, 0, temp[l].length);
            tabelas.clear();

            for (int i = m; i > 0; i--) {

                Lbit = new HashMap<String, Integer>();
                montaTabela(i);

                for (int j = 0; j <= e.length - m; j++) {//se for para considerar somente até a sequencia verdadeira usamos o (-m) caso contrário (-i)

                    String str = "";
                    for (int k = j; k < i + j; k++) {

                        str = str.concat(String.valueOf(e[k]));
                    }

                    Lbit.put(str, Lbit.get(str) + 1);

                }

                tabelas.add(Lbit);

            }

            int k = m;
            int soma = 0;
            int pont = 0;

            for (HashMap<String, Integer> valor : tabelas) {

                TreeSet<String> tree = new TreeSet<String>(valor.keySet());
                soma = 0;
                for (String string : tree) {

                    soma += Math.pow(valor.get(string), 2);
                }

                phi[pont] = ((Math.pow(2, k) / n) * soma) - n;
                k--;
                pont++;
            }

            dfp = phi[phi.length - m] - phi[phi.length - m + 1];
            dfs = phi[phi.length - m] - (2 * phi[phi.length - m + 1]) + phi[phi.length - m + 2];

            gama = new GammaDistributionImpl(Math.abs(dfp / 2d), 1);
            valor_p1 += 1 - gama.cumulativeProbability(Math.pow(2, m - 2));

            gama = new GammaDistributionImpl(Math.abs(dfp / 2d), 1);
            valor_p2 += 1 - gama.cumulativeProbability(Math.pow(2, m - 3));
        }
        valor_p1 = valor_p1 / temp.length;
        valor_p2 = valor_p2 / temp.length;

        return valor_p1 > ALFA && valor_p2 > ALFA;
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

    public double getValor_p1() {
        return valor_p1;
    }

    public double getValor_p2() {
        return valor_p2;
    }

}
