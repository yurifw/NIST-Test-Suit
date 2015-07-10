/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import modelo.Teste11;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste11 extends ControleGenerico {
    
    private final Teste11 teste = new Teste11();
 
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

            if (M < 3) {
                return "Block length must be greater or equal to 3!";
            }

            try {
                saida = teste.run(e, M);
                return (saida == true ? "Random sequence!" : "Not random sequence!").concat("\np_value1=" + teste.getValor_p1()).
                        concat("\np_value2="+teste.getValor_p2());

            } catch (MathException ex) {
                return "Error, try again!";
            } catch(OutOfMemoryError om) {
                return "Out of memory error!";
            }
        }

        return msgErro;
    }
    
}
