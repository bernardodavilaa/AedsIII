import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

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

        byte[] byteArr = m.toByteArray(); // Vetor de Bytes populado com os dados do     CSV já filtrados
        file.writeInt(m.toByteArray().length); // Escreve o tamanho desse vetor de Bytes
        file.write(byteArr); // Escreve o vetor de Bytes
    }

    public void create(Musica m) throws IOException{
        create(file, m);
    }


    public Musica Read(int entradaID) throws IOException{
        long pos;
        int qntBytesInic, id;
        Musica musLida = null;
        boolean lap;

        file.seek(0);
        file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
        try{
        while(file.getFilePointer() < file.length()){
            pos = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a quantidade de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Game específico
            id = file.readInt(); // Armazena o ID do registro Game específico
            if(id == entradaID){ // Verifica se o id é o mesmo que o selecionado
                if(lap){ // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                    try{
                        musLida = convertTo(file, pos); // Gera uma instância de Game e popula com as informações do banco de dados
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }                    
                }
                else{
                    file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
                }
            }
            else{
                file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
            }
        }
    }catch (IOException e){
        e.printStackTrace();
        musLida = null;
    }

    return musLida;
    }

    // ------------------------------------ //


    public Musica convertTo(RandomAccessFile file, long pos) throws Exception{
        Musica musicaSelec = new Musica();
        ArrayList<String> gnrTmp = new ArrayList<String>();

        file.seek(pos); // O ponteiro é direcionado para a posição inicial do registro
        file.readInt(); // Pula o ID do regitro
        musicaSelec.setLapide(file.readBoolean()); // Seta o valor da lapide na instância de Game
        musicaSelec.setId(file.readInt());
        file.readInt(); // Seta o ID
        musicaSelec.setArtistName(file.readUTF()); // Seta o nome da musica/álbum
        file.readInt();
        musicaSelec.setReleaseName(file.readUTF()); //Seta nome artista
        musicaSelec.setDate(musicaSelec.transformaLongDate(file.readLong()));
        file.readInt(); // Recebo o valor em long da data e tranformo de volta para date
        musicaSelec.setReleaseType(file.readUTF()); // Seta o nome
        musicaSelec.setReviewCount(file.readInt());
        String[] generos = new String[file.readInt()];
        for(int i=0; i<generos.length; i++){
            file.readInt(); // Pula o tamanho da String nome
            generos[i] = file.readUTF(); // Armazena em um vetor os gêneros
            gnrTmp.add(generos[i]); // Passa esses gêneros para um ArrayList<String>
        }
        musicaSelec.setGenres(gnrTmp); // Pega o ArrayList<String> populado com todos os gêneros e seta os gêneros
        return musicaSelec;
    }




}