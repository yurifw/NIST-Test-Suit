/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import modelo.Teste6;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste6 extends ControleGenerico {
    
    private final Teste6 teste = new Teste6();
    
    public String run(String arquivo, int distribution) {
        
        Integer[] e = validarArquivoEntrada(arquivo);
        boolean saida;
        
        if(e != null) {
            
            try {
                saida = distribution == 0 ? teste.runNormal(e) : teste.runT(e);                
                return (saida == true ? "Random sequence!" : "Not random sequence!").concat("\np_value="+teste.getValor_p());
                
            } catch (MathException ex) {
                return "Error, try again!";
            } catch(OutOfMemoryError om) {
                return "Out of memory error!";
            }
        }
        
        return msgErro;
    }
}
