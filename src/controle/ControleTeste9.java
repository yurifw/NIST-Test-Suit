/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import modelo.Teste9;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste9 extends ControleGenerico {

    private final Teste9 teste = new Teste9();

    public String run(String arquivo, int distribution, String block_length, String number_blocks) {

        Integer[] e = validarArquivoEntrada(arquivo);
        boolean saida;
        int L, Q, K, soma;

        if (e != null) {
            try {

                L = Integer.parseInt(block_length);
                Q = Integer.parseInt(number_blocks);

            } catch (NumberFormatException ex) {
                return "Block length and number of blocks must be filled with numbers!";
            }

            if (L <= 0 || Q <= 0) {
                return "Block length or number of blocks is invalid!";
            }

            K = (int) Math.floor(e.length / L) - Q;
            soma = (Q * L) + (K * L);
            
            if(soma > e.length || K <= 0) {
                return "Block length or number of blocks is invalid!";
            }

            try {
                saida = distribution == 0 ? teste.runNormal(e, L, Q) : teste.runT(e, L, Q);
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
