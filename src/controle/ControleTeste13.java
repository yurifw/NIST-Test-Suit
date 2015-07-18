/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import modelo.Teste13;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste13 extends ControleGenerico {
    
    private final Teste13 teste = new Teste13();
    
        public String run(String arquivo, int mode) {
        
        Integer[][] e = validarEntrada(arquivo);
        boolean saida;
        
        if(e != null) {
            
            try {
                saida = teste.run(e, mode);                
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
