import java.io.IOException;
import java.io.RandomAccessFile;

public class CRUD {
    private RandomAccessFile file;

    // ------------------------------------ //
    
    /*
     * Construtor
     */
    CRUD(String nomeArq) throws IOException{
        file = new RandomAccessFile(nomeArq, "rw");
        file.seek(0);
    }

    // ------------------------------------ //

    // Primeira injeção do CSV para o banco de dados

    public void create(RandomAccessFile file, Musica m) throws IOException{
        file.seek(0); // Ponteiro vai para o inicio do arquivo
        file.writeInt(m.getId()); // Escreve no inicio do registro o seu ID
        file.seek(file.length()); // Ponteiro vai para o final do arquivo

        byte[] byteArr = m.toByteArray(); // Vetor de Bytes populado com os dados do CSV já filtrados
        file.writeInt(m.toByteArray().length); // Escreve o tamanho desse vetor de Bytes
        file.write(byteArr); // Escreve o vetor de Bytes
    }

    public void create(Musica m) throws IOException{
        create(file, m);
    }
}