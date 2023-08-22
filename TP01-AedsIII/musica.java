import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class musica{

    private boolean lapide;
    private int id;
    private String artist_name;
    private String release_name;
    private Date release_date;
    private String release_type;
    private ArrayList<String> genres;
    private int review_count;

    public void setLapide(boolean lapide){ this.lapide=lapide;}
    public void setId(int id) { this.id = id; }
    public void setArtistName(String artist_name) { this.artist_name = artist_name; }
    public void setReleaseName(String release_name) { this.release_name = release_name; }
    public void setDate(Date release_date) { this.release_date = release_date; }
    public void setReleaseType(String release_type) { this.release_type = release_type; }
    public void setReviewCount(int review_count) { this.review_count = review_count; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }


    public boolean getLapide(){return this.lapide;}
    public int getId(){return this.id;}
    public String getArtistName(){return this.artist_name;}
    public String getReleaseName(){return this.release_name;}
    public Date getReleaseData(){return this.release_date;}
    public String getReleaseType(){return this.release_type;}
    public int getReviewCount(){return review_count;}
    public ArrayList<String> getGenres(){return this.genres;}



    musica(String dados){
        int index, indexAnt;
        this.lapide=true;
        String[] parseDados = new String[7];
        index = dados.indexOf(",");
        indexAnt=index;
        parseDados[0]= dados.substring(0,indexAnt);
        indexAnt=index;
        index= dados.indexOf(",", indexAnt);
        parseDados[1]= dados.substring(indexAnt, index);
        indexAnt=index;
        index= dados.indexOf(",", indexAnt);
        parseDados[2]= dados.substring(index, indexAnt);
        indexAnt=index;
        index= dados.indexOf(",", indexAnt);
        parseDados[3]= dados.substring(indexAnt, index);
        indexAnt=index;
        index= dados.indexOf(",", indexAnt);
        parseDados[4]= dados.substring(indexAnt, index);
        indexAnt=index;
        index= dados.indexOf(",", indexAnt);
        parseDados[5]= dados.substring(indexAnt, index);
        indexAnt=index;
        index= dados.indexOf(",", indexAnt);
        parseDados[6]= dados.substring(indexAnt, index);
        indexAnt=index;
        index= dados.indexOf(",", indexAnt);
        parseDados[7]= dados.substring(indexAnt);
        formataDados(parseDados);
    }

    public void formataDados(String[] dados){   
    
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
        

    }

    
    
}