/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import modelo.Teste8;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste8 extends ControleGenerico {
    
    private final Teste8 teste = new Teste8();
    
    public String run(String arquivo, String bloco, String template) {

        Integer[] e = validarArquivoEntrada(arquivo);
        boolean saida;
        int N;
        
        if (e != null) {
            try {

                N = Integer.parseInt(bloco);

            } catch (NumberFormatException ex) {
                return "Number of blocks must to be filled with numbers!";
            }

            if (e.length < N || N <= 0) {
                return "Number of blocks is invalid!";
            }

            if(template == null || template.equals("") || template.length() > (e.length/N)) {
                return "Template matched is invalid!";
            }
            
            try {
                saida = teste.run(e, N, template);
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
