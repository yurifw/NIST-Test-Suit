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
        try {

            Integer[][] e = validarEntrada(arquivo);
            boolean saida;
            int N;
            N = Integer.parseInt(bloco);
            for (int i = 0; i < e.length; i++) {

                if (e[i].length < N || N <= 0) {
                    return "Number of blocks is invalid!";
                }

                if (template == null || template.equals("") || template.length() > (e[i].length / N)) {
                    return "Template matched is invalid!";
                }

            }

            saida = teste.run(e, N, template);
            return (saida == true ? "Random sequence!" : "Not random sequence!").concat("\np_value=" + teste.getValor_p());
        } catch (NumberFormatException ex) {
            return "Number of blocks must to be filled with numbers!";
        } catch (MathException ex) {
            return "Error, try again!";
        } catch (OutOfMemoryError om) {
            return "Out of memory error!";
        }
    }
}
