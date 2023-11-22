import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class LZW {
    public static int[] compress(byte[] input) {
        Map<String, Integer> dicionario = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dicionario.put(String.valueOf((char) i), i);
        }

        StringBuilder current = new StringBuilder();
        int[] compressedData = new int[input.length]; // Tamanho máximo inicial
        int compressedIndex = 0;
        int tamDicionario = 256;

        for (byte nextByte : input) {
            current.append((char) nextByte);
            if (!dicionario.containsKey(current.toString())) {
                // A sequência atual não está no dicionário
                // Adiciona a sequência no dicionário
                dicionario.put(current.toString(), tamDicionario++);

                // Grava o código da sequência anterior no array de saída
                String previousSequence = current.substring(0, current.length() - 1);
                if (dicionario.containsKey(previousSequence)) {
                    compressedData[compressedIndex++] = dicionario.get(previousSequence);
                }

                // Limpa a sequência atual para conter apenas o próximo byte
                current = new StringBuilder(String.valueOf((char) nextByte));

                // Redimensiona o array de saída se necessário
                if (compressedIndex + 1 > compressedData.length) {
                    int[] newCompressedData = new int[compressedData.length * 2];
                    System.arraycopy(compressedData, 0, newCompressedData, 0, compressedIndex);
                    compressedData = newCompressedData;
                }
            }
        }

        // Grava o código da última sequência no array de saída
        compressedData[compressedIndex++] = dicionario.get(current.toString());

        // Redimensiona o array para conter apenas os valores comprimidos
        int[] compressedValues = new int[compressedIndex];
        System.arraycopy(compressedData, 0, compressedValues, 0, compressedIndex);

        return compressedValues;
    }

    public static byte[] decompress(int[] compressedData) {
        Map<Integer, String> dicionario = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dicionario.put(i, String.valueOf((char) i));
        }

        StringBuilder current = new StringBuilder();
        StringBuilder decompressedData = new StringBuilder();
        int tamDicionario = 256;

        for (int code : compressedData) {

            String currentSequence;

            if (dicionario.containsKey(code)) {
                currentSequence = dicionario.get(code);
                decompressedData.append(currentSequence);
                if (!current.toString().isEmpty()) {
                    dicionario.put(tamDicionario++, current.toString() + currentSequence.charAt(0));
                }

                current = new StringBuilder(currentSequence);
            } else if (code == tamDicionario) {
                currentSequence = current.toString() + current.charAt(0);
                decompressedData.append(currentSequence);
                if (!current.toString().isEmpty()) {
                    dicionario.put(tamDicionario++, current.toString() + currentSequence.charAt(0));
                }

                current = new StringBuilder(currentSequence);
            } else {
                currentSequence = "";
            }
        }

        return decompressedData.toString().getBytes();
    }

}