import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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


    public long getPos(RandomAccessFile file, Musica mus) throws IOException{
        file.seek(0); // Ponteiro vai para o inicio do arquivo
        file.writeInt(mus.getId()); // Escreve no inicio do registro o seu ID
        file.seek(file.length()); // Ponteiro vai para o final do arquivo
        long pos=file.getFilePointer();
        byte[] byteArr = mus.toByteArray(); // Vetor de Bytes populado com os dados do CSV já filtrados
        file.writeInt(mus.toByteArray().length); // Escreve o tamanho desse vetor de Bytes
        file.write(byteArr); // Escreve o vetor de Bytes
        return pos;
    }

    public long getPos(Musica mus) throws IOException{
        return getPos(file, mus);
    }



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

    public Boolean Pesquisar(int entradaID) throws IOException{
        long pos;
        int qntBytesInic, id;
        boolean lap;

        file.seek(0);
        file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
        try{
        while(file.getFilePointer() < file.length()){
            pos = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a quantidade de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Musica específico
            id = file.readInt(); // Armazena o ID do registro Musica específico
            if(id == entradaID){ // Verifica se o id é o mesmo que o selecionado
                if(lap){ // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                    return false;                 
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
    }

    return true;
    }


    public int getTamanho(int entradaID) throws IOException{
        long pos;
        int qntBytesInic, id;
        boolean lap;

        file.seek(0);
        file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
        try{
        while(file.getFilePointer() < file.length()){
            pos = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a quantidade de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Musica específico
            id = file.readInt(); // Armazena o ID do registro Musica específico
            if(id == entradaID){ // Verifica se o id é o mesmo que o selecionado
                return (qntBytesInic-5);
            }
            else{
                file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
            }
        }
    }catch (IOException e){
        e.printStackTrace();
    }

    return 0;
    }



    public Boolean PesquisarID_Apagado(int entradaID) throws IOException{
        long pos;
        int qntBytesInic, id;
        boolean lap;

        file.seek(0);
        file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
        try{
        while(file.getFilePointer() < file.length()){
            pos = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a quantidade de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Musica específico
            id = file.readInt(); // Armazena o ID do registro Musica específico
            if(id == entradaID){ // Verifica se o id é o mesmo que o selecionado
                return false;
            }
            else{
                file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
            }
        }
    }catch (IOException e){
        e.printStackTrace();
    }

    return true;
    }

        // ------------------------------------ //



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
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Musica específico
            id = file.readInt(); // Armazena o ID do registro Musica específico
            if(id == entradaID){ // Verifica se o id é o mesmo que o selecionado
                if(lap){ // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                    try{
                        musLida = convertTo(file, pos); // Gera uma instância de Musica e popula com as informações do banco de dados
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }                    
                }
                else{
                    return musLida;
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


    public Musica Delete(int entradaID) throws IOException{
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
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Musica específico
            id = file.readInt(); // Armazena o ID do registro Musica específico
            if(id == entradaID){ // Verifica se o id é o mesmo que o selecionado
                if(lap){ // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                    try{
                        file.seek(pos); // Volta para começo do registro
                        file.readInt(); // Pula primeira parte do registro
                        file.writeBoolean(false); // Acessa posição da lápide e deixa ela falsa
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

    public void Update(int readID, int novoID, String novoArtistName,String novaData,String novoReleaseType,int novoReviewsCount,String[] novoGenero, String releaseName) throws IOException, ParseException{
        ArrayList<String> arrayUpdate = new ArrayList<String>();
                        for(int i=0;i<novoGenero.length;i++){
                            arrayUpdate.add(novoGenero[i]);
                        }
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
         Date data= formato.parse(novaData);
         boolean lap= true;
        Musica musTemp = new Musica(novoID, novoArtistName, data, novoReleaseType, novoReviewsCount, arrayUpdate, lap, releaseName); 
        file.seek(0);
        file.writeInt(musTemp.getId());
        file.seek(file.length());
        byte[] byteArray = musTemp.toByteArray();
        file.writeInt(musTemp.toByteArray().length);
        file.write(byteArray);
    }
    // ------------------------------------ //

    public void Create(int readID, String novoArtistName,String novaData,String novoReleaseType,int novoReviewsCount,String[] novoGenero, String releaseName) throws IOException, ParseException{
        ArrayList<String> arrayUpdate = new ArrayList<String>();
                        for(int i=0;i<novoGenero.length;i++){
                            arrayUpdate.add(novoGenero[i]);
                        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date data = df.parse(novaData);
        boolean lap=true;
        Musica musTemp = new Musica(readID, novoArtistName, data, novoReleaseType, novoReviewsCount, arrayUpdate, lap, releaseName); 
        file.seek(0);
        file.writeInt(musTemp.getId());
        file.seek(file.length());
        byte[] byteArray = musTemp.toByteArray();
        file.writeInt(musTemp.toByteArray().length);
        file.write(byteArray);
    }
    // ------------------------------------ //


    public Musica convertTo(RandomAccessFile file, long pos) throws Exception{
        Musica musicaSelec = new Musica();
        ArrayList<String> gnrTmp = new ArrayList<String>();

        file.seek(pos); // O ponteiro é direcionado para a posição inicial do registro
        file.readInt(); // Pula o ID do regitro
        musicaSelec.setLapide(file.readBoolean()); // Seta o valor da lapide na instância de Musica
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