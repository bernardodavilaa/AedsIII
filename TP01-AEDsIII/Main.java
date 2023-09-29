import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc= new Scanner(System.in);
        CRUD crud = new CRUD("BancoDados.db");
        ArvoreB arv = new ArvoreB(8);
        HashExtensivel hash= new HashExtensivel(1);
        // Criar lista invertida para gêneros
        ListaInvertida listaGeneros = new ListaInvertida("listaGeneros");
        // Criar lista invertida para nome
        ListaInvertida listaNomes = new ListaInvertida("listaNomes");
        String csv = "musicas.csv";
        System.out.println("Deseja carregar o arquivo?");
        System.out.println("1-Sim \n2-Nao");
        if(sc.nextInt()==1){
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String entrada;
            while ((entrada = br.readLine()) != null) {
                Musica m = new Musica(entrada);
                crud.create(m);
                long pos= crud.getPos(m);
                arv.insere(m.getId(),pos);
                hash.insert(m.getId(), pos);
                listaGeneros.addDocument((int)pos, m.getGenres());
                listaNomes.addDocument((int)pos, m.getReleaseName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            
            //CREATE
        case 1:
        try{
            System.out.println("Digite o ID: ");
            int readID= sc.nextInt();
         if(crud.Pesquisar(readID)){
                System.out.println("Digite o nome do artista: ");
                sc.nextLine();
                String novoArtistName= sc.nextLine();
                System.out.println("Digite a nova data: ");
                System.out.println("Ex.: dd/MM/yyyy");
                String novaData= sc.nextLine();
                System.out.println("Digite o tipo de lançamento: ");
                String novoReleaseType=sc.nextLine();
                System.out.println("Digite o nome de lançamento:");
                String novoReleaseName=sc.nextLine();
                System.out.println("Digite a quantidade de Reviews: ");
                int novoReviewsCount= sc.nextInt();
                System.out.println("Digite o(s) genero(s) separado(s) por vírgula(s): ");
                System.out.println("Ex.: Pop-Rock, Rock, Country");
                sc.nextLine();
                String [] novoGenero= sc.nextLine().split(",");
                ArrayList<String> g = new ArrayList();
                for(int i=0;i<novoGenero.length;i++) g.add(novoGenero[i]);
                crud.Create(readID, novoArtistName, novaData, novoReleaseType, novoReviewsCount, novoGenero, novoReleaseName);
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date date= formato.parse(novaData);
                Musica mus= new Musica(readID, novoArtistName, date, novoReleaseType, novoReviewsCount, g, true, novoReleaseName);
                long pos= crud.getPos(mus);
                arv.insere(readID, pos);
                hash.insert(readID, opcao);
                listaGeneros.addDocument((int)pos, mus.getGenres());
                listaNomes.addDocument((int)pos, mus.getReleaseName());
                System.out.println("\nArquivo atualizado!\n");
                System.out.print("ID: "+ crud.Read(readID).getId() +", Nome Artista: "+ crud.Read(readID).getArtistName() + ", Nome de Lançamento: "+ crud.Read(readID).getReleaseName()+ ", Data de Lançamento: " + crud.Read(readID).getReleaseData()+ ", Tipo de Lançamento: "+ crud.Read(readID).getReleaseType()+", Quantidade de Reviews: "+ crud.Read(readID).getReviewCount()+", ");
                System.out.print(" Genero(s): ");
            for(int i=0; i<crud.Read(readID).getGenres().size(); i++){
                System.out.print(crud.Read(readID).getGenres().get(i));
                if(i!=crud.Read(readID).getGenres().size()-1){
                System.out.print(", ");
                }      
            }
            System.out.println("");
            }
            else
            System.out.println("ID já existente.");
        }catch(Exception e){
            System.out.println("Erro ao criar Arquivo.");
        }

        

        
        break;
        
        
            //----------------------------------------------------------------------------- //


            //Read
        
        case 2: 
        try{
            System.out.println("Digite o ID: ");
            int readID= sc.nextInt();
            crud.Read(readID).getId(); // Teste para ver se musica existe (teste de pointer)
            if(crud.Read(readID).getLapide()){
            System.out.println("\nArquivo encontrado!\n");
            System.out.print("ID: "+ crud.Read(readID).getId() +", Nome Artista: "+ crud.Read(readID).getArtistName() + ", Data de Lançamento: " + crud.Read(readID).getReleaseData()+  ", Nome de Lançamento: "+ crud.Read(readID).getReleaseName()+ ", Tipo de Lançamento: "+ crud.Read(readID).getReleaseType()+", Quantidade de Reviews: "+ crud.Read(readID).getReviewCount()+", ");
            System.out.print(" Genero(s): ");
            for(int i=0; i<crud.Read(readID).getGenres().size(); i++){
                System.out.print(crud.Read(readID).getGenres().get(i));
                if(i!=crud.Read(readID).getGenres().size()-1){
                System.out.print(", ");
                }      
            }
            System.out.println("");
        }
            else 
            System.out.println("Arquivo não Encontrado!");;
        }   catch(Exception e){
            System.out.println("\nArquivo não encontrado!");
        }
    
        break;
        //----------------------------------------------------------------------------- //
        
        //Update
        case 3:
        try{
            System.out.println("Infome o ID: ");
            int readID=sc.nextInt();
            boolean resp=crud.PesquisarID_Apagado(readID);
            if(resp==false){
                System.out.println("ID já cadastrado");break;
            }
            System.out.println("Digite o ID atualizado: ");
            int novoID= sc.nextInt();
            if((crud.Pesquisar(novoID))){
                System.out.println("Digite o nome do artista: ");
                sc.nextLine();
                String novoArtistName= sc.nextLine();
                System.out.println("Digite a nova data: ");
                System.out.println("Ex.: dd/MM/yyyy");
                String novaData= sc.nextLine();
                System.out.println("Digite o tipo de lançamento: ");
                String novoReleaseType=sc.nextLine();
                System.out.println("Digite o nome de lançamento: ");
                String novoReleaseName=sc.nextLine();
                System.out.println("Digite a quantidade de Reviews: ");
                int novoReviewsCount= sc.nextInt();
                System.out.println("Digite o(s) genero(s) separado(s) por vírgula(s): ");
                System.out.println("Ex.: Pop-Rock, Rock, Country");
                sc.nextLine();
                String [] novoGenero= sc.nextLine().split(",");
                crud.Delete(readID);
                crud.Create(novoID, novoArtistName, novaData, novoReleaseType, novoReviewsCount, novoGenero, novoReleaseName);
                System.out.println("\nArquivo atualizado!\n");
                System.out.print("ID: "+ crud.Read(novoID).getId() +", Nome Artista: "+ crud.Read(novoID).getArtistName() + ", Data de Lançamento: " + crud.Read(novoID).getReleaseData()+ ", Nome de Lançamento: "+ crud.Read(novoID).getReleaseName()+  ", Tipo de Lançamento: "+ crud.Read(novoID).getReleaseType()+", Quantidade de Reviews: "+ crud.Read(novoID).getReviewCount()+", ");
                System.out.print(" Genero(s): ");
            for(int i=0; i<crud.Read(novoID).getGenres().size(); i++){
                System.out.print(crud.Read(novoID).getGenres().get(i));
                if(i!=crud.Read(novoID).getGenres().size()-1){
                System.out.print(", ");
                }      
            }
            System.out.println("");
            }
            else{
                System.out.println("ID já cadastrado");
            }
        }catch(Exception e){
            System.out.println("Erro ao Atualizar o arquivo.");
        }
        
        
        break;
        //----------------------------------------------------------------------------- //
        
        //Delete

        case 4:
        try{
            System.out.println("Informe o ID: ");
            int readID= sc.nextInt();
             crud.Read(readID).getId(); // Teste para ver se musica existe (teste de pointer)
             if(crud.Read(readID).getLapide()){
            System.out.println("\nArquivo encontrado!\n");
            System.out.print("ID: "+ crud.Read(readID).getId() +", Nome Artista: "+ crud.Read(readID).getArtistName() +  ", Nome de Lançamento: "+ crud.Read(readID).getReleaseName()+ ", Data de Lançamento: " + crud.Read(readID).getReleaseData()+ ", Tipo de Lançamento: "+ crud.Read(readID).getReleaseType()+", Quantidade de Reviews: "+ crud.Read(readID).getReviewCount()+", ");
            System.out.print(" Genero(s): ");
            for(int i=0; i<crud.Read(readID).getGenres().size(); i++){
                System.out.print(crud.Read(readID).getGenres().get(i));
                if(i!=crud.Read(readID).getGenres().size()-1){
                System.out.print(", ");
                }      
            }
            System.out.println("");
            crud.Delete(readID);
            System.out.println(" ");
            System.out.println("Arquivo deletado com sucesso!");
            System.out.println(" ");
        }
        else
        System.out.println("Arquivo não Encontrado");
        }
        catch(Exception e){
            System.out.println("Erro ao deletar Arquivo.");
        }
            
        break;
        //----------------------------------------------------------------------------- //

        //Sair
        
        case 5:
        
        break;
        
        
        //----------------------------------------------------------------------------- //
    }
    
}

while(opcao!=4){
    System.out.println("Digite como deseja ordenar: ");
    System.out.println("1- Intercalação Balanceada Comum");
    System.out.println("2- Intercalação Balanceada com Blocos de Tamanho Variável");
    System.out.println("3- Intercalação Balanceada com seleção por Substituição");
    System.out.println("4- Sair");
    ArvoreB I= arv;I.imprimir();
    opcao = sc.nextInt();
    
    switch(opcao){

        case 1:
        IntercalacaoSimples i= new IntercalacaoSimples();
        System.out.println("Arquivo Ordenado com Intercalação Balanceada Comum!");
        break;

        case 2:

        break;

        case 3:
        break;

        case 4:
        break;


    }
}
}
    

}


//System.out.println(fileAF.Read(readID).getId() +" "+ fileAF.Read(readID).getArtistName() + " " + fileAF.Read(readID).getReleaseData() + fileAF.Read(readID).getReleaseType()+" "+ fileAF.Read(readID).getReviewCount()+" ");