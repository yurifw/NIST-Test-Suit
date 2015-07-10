/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.distribution.TDistributionImpl;

/**
 * <p>Teste Estatístico Universal de Maurer</p>
 * <p>Classe responsável por verificar se uma sequência de bits pode ser longa sem haver perda de informação</p>
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 05/07/2010
 */
public class Teste9 {

    private int n;
    private int K;
    private double fn;
    private double z;
    private double valor_p;
    private double phi;
    private HashMap<Integer, String> inicializacao;
    private HashMap<Integer, String> teste;
    private HashMap<String, Integer> Lbit;
    private HashMap<Integer, TabelaL> tabelaL = new HashMap<Integer, TabelaL>();
    private static final double ALFA = 0.01;
    private NormalDistributionImpl normal = new NormalDistributionImpl();
    private TDistributionImpl distT;

    public boolean runNormal(Integer[] e, int L, int Q) throws MathException {

        n = e.length;
        K = (int) Math.floor(n / L) - Q;
        Lbit = new HashMap<String, Integer>();
        inicializacao = new HashMap<Integer, String>();
        teste = new HashMap<Integer, String>();
        String temp = null;
        int i = 0;
        for (int j = 0; j < Q; j++, i += L) {

            temp = "";
            for (int k = i; k < L + i; k++) {

                temp = temp.concat(String.valueOf(e[k]));
            }

            inicializacao.put(j + 1, temp);
        }

        for (int j = Q; j < (K + Q); j++, i += L) {

            temp = "";
            for (int k = i; k < L + i; k++) {

                temp = temp.concat(String.valueOf(e[k]));
            }

            teste.put(j + 1, temp);
        }

        montaTabela(L);

        for (int j = 0; j < inicializacao.size(); j++) {

            temp = inicializacao.get(j + 1);
            Lbit.put(temp, j + 1);

        }

        double soma = 0;
        for (int j = Q; j < (K + Q); j++) {

            temp = teste.get(j + 1);

            soma += Math.log((j + 1) - Lbit.get(temp)) / Math.log(2);
            Lbit.put(temp, j + 1);

        }

        fn = soma / K;

        montaTabelaL();

        z = Math.abs((fn - tabelaL.get(L).getMedia()) / Math.sqrt(tabelaL.get(L).getVariancia()));
        phi = normal.cumulativeProbability(z);
        valor_p = (1 - phi) * 2;


        return valor_p > ALFA;
    }

    public boolean runT(Integer[] e, int L, int Q) throws MathException {

        n = e.length;
        K = (int) Math.floor(n / L) - Q;
        Lbit = new HashMap<String, Integer>();
        inicializacao = new HashMap<Integer, String>();
        teste = new HashMap<Integer, String>();
        String temp = null;
        int i = 0;
        for (int j = 0; j < Q; j++, i += L) {

            temp = "";
            for (int k = i; k < L + i; k++) {

                temp = temp.concat(String.valueOf(e[k]));
            }

            inicializacao.put(j + 1, temp);
        }

        for (int j = Q; j < (K + Q); j++, i += L) {

            temp = "";
            for (int k = i; k < L + i; k++) {

                temp = temp.concat(String.valueOf(e[k]));
            }

            teste.put(j + 1, temp);
        }

        montaTabela(L);

        for (int j = 0; j < inicializacao.size(); j++) {

            temp = inicializacao.get(j + 1);
            Lbit.put(temp, j + 1);

        }

        double soma = 0;
        for (int j = Q; j < (K + Q); j++) {

            temp = teste.get(j + 1);

            soma += Math.log((j + 1) - Lbit.get(temp)) / Math.log(2);
            Lbit.put(temp, j + 1);

        }

        fn = soma / K;

        montaTabelaL();

        z = Math.abs((fn - tabelaL.get(L).getMedia()) / Math.sqrt(tabelaL.get(L).getVariancia()));
        distT = new TDistributionImpl(e.length - 1);
        phi = distT.cumulativeProbability(z);   
        valor_p = (1 - phi) * 2;
        
        return valor_p > ALFA;
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

    private void montaTabelaL() {

        tabelaL.put(1, new TabelaL(0.7326495, 0.690));
        tabelaL.put(2, new TabelaL(1.5374383, 1.338));
        tabelaL.put(3, new TabelaL(2.4016068, 1.901));
        tabelaL.put(4, new TabelaL(3.3112247, 2.358));
        tabelaL.put(5, new TabelaL(4.2534266, 2.705));
        tabelaL.put(6, new TabelaL(5.2177052, 2.954));
        tabelaL.put(7, new TabelaL(6.1962507, 3.125));
        tabelaL.put(8, new TabelaL(7.1836656, 3.238));
        tabelaL.put(9, new TabelaL(8.1764248, 3.311));
        tabelaL.put(10, new TabelaL(9.1723243, 3.356));
        tabelaL.put(11, new TabelaL(10.170032, 3.384));
        tabelaL.put(12, new TabelaL(11.168765, 3.401));
        tabelaL.put(13, new TabelaL(12.168070, 3.410));
        tabelaL.put(14, new TabelaL(13.167693, 3.416));
        tabelaL.put(15, new TabelaL(14.167488, 3.419));
        tabelaL.put(16, new TabelaL(15.167379, 3.421));

    }

    private class TabelaL {

        double media;
        double variancia;

        public TabelaL(double media, double variancia) {

            this.media = media;
            this.variancia = variancia;
        }

        public double getMedia() {
            return media;
        }

        public double getVariancia() {
            return variancia;
        }
    }

    public double getValor_p() {
        return valor_p;
    }

}
