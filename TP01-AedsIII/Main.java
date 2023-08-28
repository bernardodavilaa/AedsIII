import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc= new Scanner(System.in);
        CRUD fileAF = new CRUD("BancoDados");
        String csv = "musicas.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String entrada;
            while ((entrada = br.readLine()) != null) {
                Musica m = new Musica(entrada);
                fileAF.create(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int opcao=0;
    while(opcao!=5){
        System.out.println("Escolha uma operação: ");
        System.out.println("1- Create");
        System.out.println("2- Read");
        System.out.println("3- Update");
        System.out.println("4- Delete");
        System.out.println("5- Sair");
        opcao= sc.nextInt();

        switch(opcao){

        case 1:
        //----------------------------------------------------------------------------- //
         
          //CREATE





         //----------------------------------------------------------------------------- //



        //Read
        
        case 2: 
        try{
            System.out.println("Digite o ID: ");
            int readID= sc.nextInt();
            fileAF.Read(readID).getId(); // Teste para ver se game existe (teste de pointer)
            System.out.println("\nArquivo encontrado!\n");
            System.out.print("ID: "+ fileAF.Read(readID).getId() +", Nome Artista: "+ fileAF.Read(readID).getArtistName() + ", Data de Lançamento: " + fileAF.Read(readID).getReleaseData()+ ", Tipo de Lançamento: "+ fileAF.Read(readID).getReleaseType()+", Quantidade de Reviews: "+ fileAF.Read(readID).getReviewCount()+", ");
            System.out.print(" Genrero(s): ");
            for(int i=0; i<fileAF.Read(readID).getGenres().size(); i++){
                System.out.print(fileAF.Read(readID).getGenres().get(i));
                if(i!=fileAF.Read(readID).getGenres().size()-1){
                System.out.print(", ");
                }      
            }
            System.out.println("");
        }   catch(Exception e){
            System.out.println("\nArquivo não encontrado!");
        }
        //----------------------------------------------------------------------------- //

        //Update
        
        case 3:

        //----------------------------------------------------------------------------- //

        //Delete

        case 4:

        //----------------------------------------------------------------------------- //

        //Sair

        case 5:

        break;

        //----------------------------------------------------------------------------- //
    }

        }

    }
    

}


//System.out.println(fileAF.Read(readID).getId() +" "+ fileAF.Read(readID).getArtistName() + " " + fileAF.Read(readID).getReleaseData() + fileAF.Read(readID).getReleaseType()+" "+ fileAF.Read(readID).getReviewCount()+" ");