package util;

import java.math.BigDecimal;

/**
 * <p>Classe responsável por realizar conversão entre valores numéricos ou objetos</p>
 * @author Renato Hidaka (renatohidaka@gmail.com)
 * @version 1.0
 * @created 25/08/2009
 */
public class Conversor {

    /**
     * <p>Método responsável por inserir zeros a esquerda quanto o número de bits for menor que 7</p>
     * @param recebe o bit a ser tratado
     * @return retorna o bit manipulado
     */
    public static String tratarBit(String bit) {

        String inicio = "";
        if (bit.length() < 8) {
            int zeros = 8 - bit.length();

            for (int j = 0; j < zeros; j++) {
                inicio += "0";

            }
        }

        return inicio + bit;

    }

    /**
     * <p>Método responsável converter o byte em bits sendo o bit mais significativo o bit de sinal</p>
     * @param recebe o byte
     * @return retorna o bit
     */
    public static String byteBit(byte b) {

        return new StringBuilder(Conversor.tratarBit(Integer.toBinaryString(b < 0 ? 256 + b : b))).toString();
    }

    /**
     * <p>Método responsável converter o bit em byte</p>
     * @param recebe uma sitring de bit
     * @return retorna o byte
     */
    public static byte bitByte(String b) {

        if (b.substring(0, 1).equals("1")) {

            return (byte) ((256 - Integer.parseInt(b, 2)) * -1);

        } else {
            return (byte) (Integer.parseInt(b.substring(1), 2));
        }
    }

    /**
     * <p>Método responsável converter o bit em um vetor de byte</p>
     * @param recebe string de bits
     * @return retorna o vetor de byte
     */
    public static byte[] bitByteVetor(String b) {

        byte[] vetor = new byte[b.length() / 8];

        for (int i = 0, j = 0; i < b.length(); i += 8, j++) {

            vetor[j] = bitByte(b.substring(i, i + 8));

        }

        return vetor;
    }

    /**
     * <p>Método responsável converter um vetor de byte em um conjunto de bits</p>
     * @param recebe um vetor de byte
     * @return retorna uma string de bits
     */
    public static String byteBitVetor(byte[] b) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < b.length; i++) {

            builder.append(byteBit(b[i]));

        }

        return builder.toString();
    }

    /**
     * <p>Método responsável converter uma String binária para decimal</p>
     * @param recebe uma String Binária
     * @return retorna um Inteiro
     */
    public static BigDecimal bitToInt(String b) {

        BigDecimal valor = new BigDecimal(0);
        BigDecimal dois = new BigDecimal(2);
        String[] vetor = b.split("");
        for (int i = 1, potencia = vetor.length - 2; i < vetor.length; i++, potencia--) {

            if (vetor[i].equals("1")) {
                valor = valor.add(dois.pow(potencia));
            }
        }

        return valor;
    }

        /**
     * <p>Método resnposável por realizar a conversão de um interio para sua respectiva sequência de bytes</p>
     * @param valor a ser convetido
     * @return retorna um vetor de bytes
     */
    public static byte[] intParaArrayByte(int valor) {

        byte[] b = new byte[4];
        int offset = 0;
        for (int i = 0; i < 4; i++) {
            offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((valor >>> offset) & 0xFF);
        }
        return b;
    }
}