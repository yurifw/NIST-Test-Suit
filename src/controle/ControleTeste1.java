package controle;

import modelo.Teste1;
import org.apache.commons.math.MathException;

/**
 *
 * @author renatohidaka
 */
public class ControleTeste1 extends ControleGenerico {
    
    private final Teste1 teste = new Teste1();
    
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
