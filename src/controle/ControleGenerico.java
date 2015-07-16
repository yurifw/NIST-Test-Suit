package controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author renatohidaka
 */
public class ControleGenerico {

    protected final String msgErro = "Invalid Input!\nThe file should contain only zeros and ones.\n"
            + "If it is a directory, check if all files contain only zeros and ones.";

    ;
    
    public static Integer[][] validarEntrada(String entrada) {

        List<List<Integer>> saida = new LinkedList();
        List<String> arquivos = new ArrayList<>();

        if (new File(entrada).isFile()) {
            arquivos.add(entrada);
        } else {
            for (String s : lerDiretorio(entrada)) {
                arquivos.add(s);
            }
        }

        for (String arquivo : arquivos) {
            List<Integer> s = new ArrayList<>();
            try {

                BufferedReader leitura = new BufferedReader(new FileReader(new File(arquivo)));
                String linha;
                String[] bits;

                while ((linha = leitura.readLine()) != null) {

                    if (linha.matches("[0|1]+")) {
                        bits = linha.split("");
                        for (String bit : bits) {
                            s.add(Integer.valueOf(bit));
                        }
                    }
                }
                saida.add(s);
                
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        //convertendo List de List para array bidimensional para evitar alteracoes maiores nos outros metodos
        Integer [][] array = new Integer[saida.size()][];
        for (int i = 0; i < saida.size(); i++){
            array[i] = new Integer[saida.get(i).size()];
            for (int j = 0; j < saida.get(i).size(); j++){                
                array[i][j] = saida.get(i).get(j);
            }
        }
        
        return array;
    }

    public static List<String> lerDiretorio(String diretorio) {
        File folder = new File(diretorio);
        List<String> listOfFiles = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                listOfFiles.add(diretorio + File.separator + file.getName());
            }
        }
        return listOfFiles;
    }

}
