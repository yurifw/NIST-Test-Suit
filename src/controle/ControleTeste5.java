/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import modelo.Teste5;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste5 extends ControleGenerico {

    private final Teste5 teste = new Teste5();

    public String run(String arquivo, String linha, String coluna) {

        Integer[][] e = validarEntrada(arquivo);
        try {
            boolean saida;
            int M, Q;

            if (e != null) {

                M = Integer.parseInt(linha);
                Q = Integer.parseInt(coluna);
                for (int i =0; i<e.length; i++){
                    if (e[i].length < (M * Q) || M <= 0 || Q <= 0) {
                        return "Number of rows or columns is invalid!";
                    }
                }

                saida = teste.run(e, M, Q);
                return (saida == true ? "Random sequence!" : "Not random sequence!").concat("\np_value=" + teste.getValor_p());

            }
        } catch (MathException ex) {
            return "Error, try again!";
        } catch (OutOfMemoryError om) {
            return "Out of memory error!";
        } catch (NumberFormatException ex) {
            return "Number of rows and columns must be filled with numbers!";
        }
        return msgErro ;
    }   
}
