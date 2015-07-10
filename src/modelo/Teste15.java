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
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.distribution.TDistributionImpl;

/**
 *
 * @author renatohidaka
 */
public class Teste15 {

    private int n;
    private double valor_p;
    private int[] X;
    private int[] S;
    private int J;
    private double phi;
    private int[] S_linha;
    private List<List<Integer>> ciclos = new ArrayList<List<Integer>>();
    private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    private NormalDistributionImpl normal = new NormalDistributionImpl();
    private TDistributionImpl distT;
    private static final double ALFA = 0.01;

    public boolean runNormal(Integer[] e) throws MathException {

        n = e.length;
        J = 0;
        X = new int[n];
        S = new int[n];
        ciclos.clear();
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

        for (int i = -4; i <= 4; i++) {
            map.put(i, 0);
        }

        for (int i = -4; i <= 4; i++) {

            for (List c : ciclos) {
                map.put(i, map.get(i) + Collections.frequency(c, i));
            }

        }

        double z = 0;

        for (int i = -4; i <= 4; i++, z = 0) {

            if (i == 0) {
                continue;
            }
            z += Math.abs(map.get(i) - J) / Math.sqrt((J * (4 * Math.abs(i)) - 2));
            phi = normal.cumulativeProbability(z);
            valor_p = (1 - phi) * 2;
                   
            if (valor_p < ALFA) {
                return false;
            }
        }

        return true;
    }

    public boolean runT(Integer[] e) throws MathException {

        n = e.length;
        J = 0;
        X = new int[n];
        S = new int[n];
        ciclos.clear();
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

        for (int i = -4; i <= 4; i++) {
            map.put(i, 0);
        }

        for (int i = -4; i <= 4; i++) {

            for (List c : ciclos) {
                map.put(i, map.get(i) + Collections.frequency(c, i));
            }

        }

        double z = 0;

        for (int i = -4; i <= 4; i++, z = 0) {

            if (i == 0) {
                continue;
            }
            z += Math.abs(map.get(i) - J) / Math.sqrt((J * (4 * Math.abs(i)) - 2));
            distT = new TDistributionImpl(e.length - 1);
            phi = distT.cumulativeProbability(z);
            valor_p = (1 - phi) * 2;
            
            if (valor_p < ALFA) {
                return false;
            }
        }

        return true;
    }

    public double getValor_p() {
        return valor_p;
    }
}
