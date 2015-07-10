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

        Integer[] e = validarArquivoEntrada(arquivo);
        boolean saida;
        int M, Q;
        
        if (e != null) {
            try {

                M = Integer.parseInt(linha);
                Q = Integer.parseInt(coluna);

            } catch (NumberFormatException ex) {
                return "Number of rows and columns must be filled with numbers!";
            }

            if (e.length < (M*Q) || M <= 0 || Q <= 0) {
                return "Number of rows or columns is invalid!";
            }

            try {
                saida = teste.run(e, M, Q);
                return (saida == true ? "Random sequence!" : "Not random sequence!").concat("\np_value=" + teste.getValor_p());

            } catch (MathException ex) {
                return "Error, try again!";
            } catch(OutOfMemoryError om) {
                return "Out of memory error!";
            }
        }

        return msgErro;
    }
}
