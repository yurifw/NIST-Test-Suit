/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import modelo.Teste2;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste2 extends ControleGenerico {

    private final Teste2 teste = new Teste2();

    public String run(String arquivo, String bloco) {
        try {
            Integer[][] e = validarEntrada(arquivo);
            boolean saida;
            int M = Integer.parseInt(bloco);

            for (int i = 0; i < e.length; i++) {
                if (e[i].length < M || M <= 0) {
                    return "Number of blocks is invalid!";
                }
            }
            saida = teste.run(e, M);
            return (saida == true ? "Random sequence!" : "Not random sequence!").concat("\np_value=" + teste.getValor_p());

        } catch (MathException ex) {
            ex.printStackTrace();
            return "Error, try again!";
        } catch (OutOfMemoryError om) {
            om.printStackTrace();
            return "Out of memory error!";
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return "Number of blocks must to be filled with numbers!";
        }
    }
}
