package controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author renatohidaka
 */
public class ControleGenerico {

    protected final String msgErro = "Invalid file!\nThe file should contain only zeros and ones.";

    ;
    
    public Integer[] validarArquivoEntrada(String arquivo) {

        Queue<Integer> saida = new LinkedList();

        try {
                                    
            if(arquivo == null || arquivo.equals("")) {
                return null;
            }
            
            BufferedReader leitura = new BufferedReader(new FileReader(new File(arquivo)));
            String linha;
            String[] bits;

            while ((linha = leitura.readLine()) != null) {

                if (linha.matches("[0|1]+")) {
                    bits = linha.split("");
                    for (String bit : bits) {
                        saida.add(Integer.valueOf(bit));
                    }
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            return null;
        }

        return saida.size() > 0 ? saida.toArray(new Integer[0]) : null;
    }

}
