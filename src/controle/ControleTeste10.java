/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import modelo.Teste10;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste10 extends ControleGenerico {
    
    private final Teste10 teste = new Teste10();
    
        public String run(String arquivo, String bloco) {

        Integer[][] e = validarEntrada(arquivo);
        for (int i =0; i< e.length; i++){
            boolean saida;
            int M;

            if (e[i] != null) {
                try {

                    M = Integer.parseInt(bloco);

                } catch (NumberFormatException ex) {
                    return "Block length must to be filled with numbers!";
                }

                if (e[i].length < M || M <= 0) {
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
        }

        return msgErro;
    }
}
