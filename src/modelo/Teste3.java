/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.distribution.TDistributionImpl;

/**
 * <p>Teste da Rodada</p>
 * <p>Classe responsável por verificar se ocorre uma oscilação rápida ou lenta de 0’s e 1’s
 * em uma sequência de bits</p>
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 11/06/2010
 */
public class Teste3 {

    private int n;
    private double Vobs;
    private double pi;
    private double phi;
    private double valor_p;
    private double z;
    private static final double ALFA = 0.01;
    private final NormalDistributionImpl normal = new NormalDistributionImpl();
    private TDistributionImpl distT;

    /**
     * <p>Método que recebe um conjunto de zeros e uns e verifica a aleatoiedade usando a distribuiao Normal </p>
     * @author Renato Hidaka (renatohidaka@gmail.com)
     * @version 1.0
     * @created 11/06/2010
     * @param int[] conjunto de bit
     * @return se valor_p maior que 0,01 conjuto de bit é aleatório
     */
    public boolean runNormal(Integer[] e) throws MathException {

        n = e.length;
        pi = 0;
        Vobs = 1;

        for (int i = 0; i < e.length; i++) {

            pi += e[i];
        }

        pi /= n;

        if(Math.abs(pi - 0.5) < 2/Math.pow(n, 0.5)){

            for (int i = 0; i < e.length-1; i++) {

                Vobs += Math.abs(e[i]-e[i+1]);
            }

            z = Math.sqrt(2) * Math.abs(Vobs - 2*n*pi*(1-pi)) / (2* Math.sqrt(2*n)*pi*(1-pi));
            phi = normal.cumulativeProbability(z);
            valor_p = (1 - phi) * 2;

            return valor_p > ALFA;

        } else{

            return false;//teste não pode ser aplicado
        }
    }

      /**
     * <p>Método que recebe um conjunto de zeros e uns e verifica a aleatoiedade usando a distribuiao T-Student </p>
     * @author Renato Hidaka (renatohidaka@gmail.com)
     * @version 1.0
     * @created 11/06/2010
     * @param int[] conjunto de bit
     * @return se valor_p maior que 0,01 conjuto de bit é aleatório
     */
    public boolean runT(Integer[] e) throws MathException {

        n = e.length;
        pi = 0;
        Vobs = 1;

        for (int i = 0; i < e.length; i++) {

            pi += e[i];
        }

        pi /= n;

        if(Math.abs(pi - 0.5) < 2/Math.pow(n, 0.5)){

            for (int i = 0; i < e.length-1; i++) {

                Vobs += Math.abs(e[i]-e[i+1]);
            }

            z = Math.sqrt(2) * Math.abs(Vobs - 2*n*pi*(1-pi)) / (2* Math.sqrt(2*n)*pi*(1-pi));
            distT = new TDistributionImpl(n-1);
            phi = distT.cumulativeProbability(z);
            valor_p = (1 - phi) * 2;

            return valor_p > ALFA;

        } else{

            return false;//teste não pode ser aplicado
        }
    }
  
    public double getValor_p() {
        return valor_p;
    }
    
}
