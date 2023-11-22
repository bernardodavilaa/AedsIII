import java.io.*;
import java.util.*;

class NoHuffman {
    int frequencia;
    char caractere;
    NoHuffman esq;
    NoHuffman dir;
}

class HuffmanComparator implements Comparator<NoHuffman> {
    public int compare(NoHuffman no1, NoHuffman no2) {
        return no1.frequencia - no2.frequencia;
    }
}

public class CompressaoHuffman {

    private Map<Character, String> huffmanCodes;

    public String compress(String input) {
        huffmanCodes = buildHuffmanCodes(input);

        StringBuilder compressedString = new StringBuilder();

        // Converter a sequência de entrada em uma sequência compactada usando os códigos de Huffman
        for (char c : input.toCharArray()) {
            compressedString.append(huffmanCodes.get(c));
        }

        return compressedString.toString();
    }

    public String descompressao(String compressedString) {
        StringBuilder decompressedString = new StringBuilder();

        StringBuilder currentCode = new StringBuilder();
        for (char c : compressedString.toCharArray()) {
            currentCode.append(c);
            for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
                if (entry.getValue().equals(currentCode.toString())) {
                    decompressedString.append(entry.getKey());
                    currentCode.setLength(0);
                    break;
                }
            }
        }

        return decompressedString.toString();
    }

    private Map<Character, String> buildHuffmanCodes(String input) {
        Map<Character, String> huffmanCodes = new HashMap<>();

        // Calcular a frequência de cada caractere
        Map<Character, Integer> frequenciaMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequenciaMap.put(c, frequenciaMap.getOrDefault(c, 0) + 1);
        }

        // Criar uma fila de prioridade para armazenar os nós
        PriorityQueue<NoHuffman> priorityQueue = new PriorityQueue<>(new HuffmanComparator());

        // Criar um nó para cada caractere e adicioná-lo à fila de prioridade
        for (Map.Entry<Character, Integer> entry : frequenciaMap.entrySet()) {
            NoHuffman no = new NoHuffman();
            no.caractere = entry.getKey();
            no.frequencia = entry.getValue();
            no.esq = null;
            no.dir = null;
            priorityQueue.add(no);
        }

        // Construir a árvore de Huffman
        while (priorityQueue.size() > 1) {
            NoHuffman esq = priorityQueue.poll();
            NoHuffman dir = priorityQueue.poll();

            NoHuffman noPai = new NoHuffman();
            noPai.caractere = '\0';
            noPai.frequencia = esq.frequencia + dir.frequencia;
            noPai.esq = esq;
            noPai.dir = dir;

            priorityQueue.add(noPai);
        }

        // Gerar os códigos de Huffman recursivamente
        if (!priorityQueue.isEmpty()) {
            NoHuffman noRaiz = priorityQueue.peek();
            gerarHuffmanCodes(noRaiz, "", huffmanCodes);
        }

        return huffmanCodes;
    }

    private void gerarHuffmanCodes(NoHuffman no, String code, Map<Character, String> huffmanCodes) {
        if (no == null) {
            return;
        }

        if (no.caractere != '\0') {
            huffmanCodes.put(no.caractere, code);
        }

        gerarHuffmanCodes(no.esq, code + "0", huffmanCodes);
        gerarHuffmanCodes(no.dir, code + "1", huffmanCodes);
    }

    public void escreverCompressedFile(String compressedString) {
        try {
            FileWriter fw = new FileWriter("baseHuffmanCompressao.txt");
            BufferedWriter writer = new BufferedWriter(fw);

            writer.write(compressedString);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String lerCompressedFile(int version) {
        StringBuilder compressedString = new StringBuilder();

        try {
            FileReader fileReader = new FileReader("baseHuffmanCompressao"+version+".txt");
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                compressedString.append(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressedString.toString();
    }
}