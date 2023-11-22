import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Musica {

    private boolean lapide;
    private int id;
    private String artist_name;
    private String release_name;
    private Date release_date;
    private String release_type;
    private ArrayList<String> genres;
    private int review_count;

    public void setLapide(boolean lapide) {
        this.lapide = lapide;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArtistName(String artist_name) {
        this.artist_name = artist_name;
    }

    public void setReleaseName(String release_name) {
        this.release_name = release_name;
    }

    public void setDate(Date release_date) {
        this.release_date = release_date;
    }

    public void setReleaseType(String release_type) {
        this.release_type = release_type;
    }

    public void setReviewCount(int review_count) {
        this.review_count = review_count;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public boolean getLapide() {
        return this.lapide;
    }

    public int getId() {
        return this.id;
    }

    public String getArtistName() {
        return this.artist_name;
    }

    public String getReleaseName() {
        return this.release_name;
    }

    public Date getReleaseData() {
        return this.release_date;
    }

    public String getReleaseType() {
        return this.release_type;
    }

    public int getReviewCount() {
        return review_count;
    }

    public ArrayList<String> getGenres() {
        return this.genres;
    }

    public long getCreatedAt() {
        return release_date.getTime();
    }

    Musica() {
        setLapide(true);
        setId(0);
        setArtistName("Be e Gi");
        setReleaseName("Patati e Patatá");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            setDate(formato.parse("2002-02-13"));
        } catch (ParseException e) {
            System.out.println("Erro ao converter a data.");
            e.printStackTrace();
        }
        setReleaseType("album");
        setReviewCount(0);
        this.genres = new ArrayList<String>();
    }

    Musica(int novoID, String novoArtistName, Date data, String novoReleaseType, int novoReviewsCount,
            ArrayList<String> generos, boolean lapide, String releaseName) {
        this.id = novoID;
        this.artist_name = novoArtistName;
        this.release_date = data;
        this.release_type = novoReleaseType;
        this.review_count = novoReviewsCount;
        this.genres = generos;
        this.lapide = true;
        this.release_name = releaseName;
    }

    Musica(String dados) {
        int index, indexAnt;
        this.lapide = true;
        String[] parseDados = new String[7];
        index = dados.indexOf(",");
        indexAnt = index;
        parseDados[0] = dados.substring(0, indexAnt);
        indexAnt = index;
        index = dados.indexOf(",", indexAnt + 1);
        parseDados[1] = dados.substring(indexAnt + 1, index);
        indexAnt = index;
        index = dados.indexOf(",", indexAnt + 1);
        parseDados[2] = dados.substring(indexAnt + 1, index);
        indexAnt = index;
        index = dados.indexOf(",", indexAnt + 1);
        parseDados[3] = dados.substring(indexAnt + 1, index);
        indexAnt = index;
        index = dados.indexOf(",", indexAnt + 1);
        parseDados[4] = dados.substring(indexAnt + 1, index);
        indexAnt = index;
        index = dados.indexOf(",", indexAnt + 1);
        parseDados[5] = dados.substring(indexAnt + 1, index);
        indexAnt = index;
        index = dados.indexOf(",", indexAnt + 1);
        parseDados[6] = dados.substring(indexAnt + 1);
        formataDados(parseDados);
    }

    public void formataDados(String[] dados) {

        setId(Integer.parseInt(dados[0]));
        setArtistName(dados[1]);
        setReleaseName(dados[2]);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            setDate(formato.parse(dados[3]));
        } catch (ParseException e) {
            System.out.println("Erro ao converter a data.");
            e.printStackTrace();
        }
        setReleaseType(dados[4]);
        setReviewCount(Integer.parseInt(dados[5]));
        String[] generos = dados[6].split(",");
        this.genres = new ArrayList<String>(generos.length);
        for (int i = 0; i < generos.length; i++)
            genres.add(generos[i]);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
        DataOutputStream bData = new DataOutputStream(bOutput);

        bData.writeBoolean(this.lapide);
        bData.writeInt(getId());
        bData.writeInt(artist_name.getBytes(Charset.forName("UTF-8")).length);
        bData.writeUTF(artist_name);
        bData.writeInt(release_name.getBytes(Charset.forName("UTF-8")).length);
        bData.writeUTF(release_name);
        bData.writeLong(getCreatedAt());
        bData.writeInt(release_type.getBytes(Charset.forName("UTF-8")).length);
        bData.writeUTF(release_type);
        bData.writeInt(getReviewCount());
        bData.writeInt(genres.size());
        for (String generos : genres) {
            bData.writeInt(generos.getBytes(Charset.forName("UTF-8")).length);
            bData.writeUTF(generos);
        }

        return bOutput.toByteArray();
    }

    // --------------------------------------------------------------------------------------
    // //

    public Date transformaLongDate(long getTime) {
        Date date = new Date(getTime);
        return date;
    }

    public void fromByteArray(int id, boolean lapis, int len, byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.id = id;
        this.lapide = lapis;
        int tamanho = len;
        byte[] musicaBytes = new byte[tamanho];
        dis.readFully(musicaBytes); // Isso lê 'tamanho' bytes do DataInputStream
        Musica musica = Musica.fromByteArray(musicaBytes);
    }

    public static Musica fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        Musica m = new Musica();
        ArrayList<String> gnrTmp = new ArrayList<String>();

        m.setId(dis.readInt());
        dis.readInt();
        m.setArtistName(dis.readUTF());
        dis.readInt();
        m.setReleaseName(dis.readUTF());
        m.setDate(m.transformaLongDate(dis.readLong()));
        dis.readInt();
        m.setReleaseType(dis.readUTF());
        m.setReviewCount(dis.readInt());
        String[] generos = new String[dis.readInt()];
        for (int i = 0; i < generos.length; i++) {
            dis.readInt(); // Pula o tamanho da String nome
            generos[i] = dis.readUTF(); // Armazena em um vetor os gêneros
            gnrTmp.add(generos[i]); // Passa esses gêneros para um ArrayList<String>
        }
        m.setGenres(gnrTmp); // Pega o ArrayList<String> populado com todos os gêneros e seta os gêneros

        return m;
    }
}
