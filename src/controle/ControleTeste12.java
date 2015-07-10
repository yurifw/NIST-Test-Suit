/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import modelo.Teste12;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste12 extends ControleGenerico {
    
    private final Teste12 teste = new Teste12();
    
    public String run(String arquivo, String bloco) {

        Integer[] e = validarArquivoEntrada(arquivo);
        boolean saida;
        int M;
        
        if (e != null) {
            try {

                M = Integer.parseInt(bloco);

            } catch (NumberFormatException ex) {
                return "Block length must to be filled with numbers!";
            }

            if (M > e.length || M <= 0) {
                return "Block length is invalid!";
            }

            try {
                saida = teste.run(e, M);
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
