/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.distribution.TDistributionImpl;

/**
 * <p>Teste de Frequência Monobit</p>
 * <p>Classe responsável por verificar se, em uma sequência de bits (entrada), a frequência de 0’s e 1’s é a mesma, ou seja, se a proporção é ½.</p>
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 10/06/2010
 */
public class Teste1 {

    private int[] x;
    private int n;
    private int Sn;
    private double Sobs;
    private double phi;
    private double valor_p;
    private static final double ALFA = 0.01;
    private final NormalDistributionImpl normal = new NormalDistributionImpl();
    private TDistributionImpl distT;

    /**
     * <p>Método que recebe um conjunto de zeros e uns e verifica a aleatoiedade usando a distribuicao Normal </p>
     * @author Renato Hidaka (renatohidaka@gmail.com)
     * @version 1.0
     * @param e
     * @throws org.apache.commons.math.MathException
     * @created 10/06/2010
     * @param int[] conjunto de bit
     * @return se valor_p maior que 0,01 conjuto de bit é aleatório
     */
    public boolean runNormal(Integer[][] e) throws MathException {
        valor_p=0;
        for(int i=0; i< e.length; i++){
            n = e[i].length;
            x = new int[n];
            Sn = 0;
            for (int j = 0; j < n; j++) {
                x[j] = (2 * e[i][j]) - 1;
                Sn += x[j];
            }

            Sobs = Math.abs(Sn) / Math.sqrt(n);        
            phi = normal.cumulativeProbability(Sobs);        
            valor_p += (1 - phi) * 2;
        }
        valor_p = valor_p / e.length;
        return getValor_p() > ALFA;
    }

    /**
     * <p>Método que recebe um conjunto de zeros e uns e verifica a aleatoiedade usando a distribuicao T-Student</p>
     * @author Renato Hidaka (renatohidaka@gmail.com)
     * @version 1.0
     * @param e
     * @throws org.apache.commons.math.MathException
     * @created 28/06/2010
     * @param int[] conjunto de bit
     * @return se valor_p maior que 0,01 conjuto de bit é aleatório
     */
    public boolean runT(Integer[][] e) throws MathException{
        valor_p = 0;
        for (int i =0; i < e.length; i++) {
            n = e[i].length;
            x = new int[n];
            Sn = 0;
            for (int j = 0; j < n; j++) {

                x[j] = (2 * e[i][j]) - 1;
                Sn += x[j];

            }

            Sobs = Math.abs(Sn) / Math.sqrt(n);
            distT = new TDistributionImpl(e[i].length - 1);
            phi = distT.cumulativeProbability(Sobs);
            valor_p += (1 - phi) * 2;
        }
        valor_p=valor_p/e.length;
        return getValor_p() > ALFA;
    }
 
    public double getValor_p() {
        return valor_p;
    } 
}
