import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

class Main {
    public static int version=0;
    public static void main(String[] args) throws IOException {
        try{
            CRUD crud = new CRUD("BancoDados");
            HuffmanCompression huffman = new HuffmanCompression();
            KMP kmp = new KMP();
            try {
                // Ler o CSV file
                String basefile = "musicas.csv";
    
                FileInputStream fstream = new FileInputStream(basefile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    
                // ------------------------------------ //
    
                // Leitura de todo o CSV.
                String line;
      
                while((line = br.readLine()) != null) {
                    Musica m = new Musica(line);
                    crud.create(m);
                }
                
                Scanner sc = new Scanner(System.in);

    int opcao=0;
    String lixo="";
    
   while(opcao!=7){
                    System.out.println(" _____________________________________");
                    System.out.println("|Digite qual operação deseja realizar |");
                    System.out.println("|1 - Create                           |");
                    System.out.println("|2 - Read                             |");
                    System.out.println("|3 - Update                           |");
                    System.out.println("|4 - Delete                           |");
                    System.out.println("|5 - Comprimir                        |");
                    System.out.println("|6 - Descomprimir                     |");
                    System.out.println("|7 - Pesquisar Padrão                 |");
                    System.out.println("|8 - Sair e salvar arquivo            |");
                    System.out.println("|_____________________________________|");
                    System.out.println();
                    System.out.print("Opção: ");
                    try{
                    opcao = sc.nextInt();
                    System.out.println();
                    switch(opcao){

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
            crud.Read(readID).getId(); // Teste para ver se game existe (teste de pointer)
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
             crud.Read(readID).getId(); // Teste para ver se game existe (teste de pointer)
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
                //Compressão do arquivo
                         case 5:
                        System.out.println("Comprimindo arquivo...\n");
                        // Comprimir arquivos
                        // Huffman
                        long iHuffComp = System.currentTimeMillis();
                        try{
                            String arquivo = new Scanner(new File("musicas.csv")).useDelimiter("\\\\Z").next();
                            //System.out.println(arquivo);
                            String compressedString = huffman.compress(arquivo);
                            huffman.writeCompressedFile(compressedString);
                            System.out.println("Arquivo da sequência compactada gerado: baseHuffmanCompressao"+Main.version+".txt");
                        }
                        catch (Exception e){
                            System.out.println("Erro na compressão Huffman");
                        }
                        long fHuffComp = System.currentTimeMillis()-iHuffComp;
                        System.out.println("Tempo de compressão Huffman: "+fHuffComp+"ms");
                        // LZW
                        long iLzwComp = System.currentTimeMillis();
                        try{
                            String baseIncial="BancoDados";
                            String arqComprimido = "baseLzwCompressao"+version++;
                            byte[] fileContent = Files.readAllBytes(Paths.get(baseIncial));
                            int[] compressed = LZW.compress(fileContent);
                            byte[] compressedBytes = new byte[compressed.length * 2];
                            for (int i = 0; i < compressed.length; i++) {
                                compressedBytes[2 * i] = (byte) (compressed[i] >> 8);
                                compressedBytes[2 * i + 1] = (byte) (compressed[i] & 0xFF);
                            }
                            Files.write(Paths.get(arqComprimido), compressedBytes);
                            System.out.println("Arquivo da sequência compactada gerado: baseLzwCompressao"+(version-1)+".txt");
                        } catch (Exception e){
                            System.out.println("Erro na compressão LZW");
                        }
                        long fLzwComp = System.currentTimeMillis()-iLzwComp;
                        System.out.println("Tempo de compressão LZW: "+fLzwComp+"ms");
                        if(fHuffComp<fLzwComp){
                            System.out.print("Compressão Huffman foi ");
                            System.out.printf("%.2f ", (1.0-((float)fHuffComp/(float)fLzwComp))*100);
                            System.out.println("% mais eficiente");
                        } else{
                            System.out.print("Compressão LZW foi ");
                            System.out.printf("%.2f "+ (1.0-((float)fLzwComp/(float)fHuffComp))*100);
                            System.out.println("% mais eficiente");
                        }
                        System.out.println("\nArquivo comprimido com sucesso!");
                        break;
                        case 6:
                        System.out.print("Qual a versão do arquivo que deseja descomprimir? ");
                        int versao = sc.nextInt();
                        System.out.println("Descomprimindo arquivo...\n");
                        // Descomprimir arquivo da versão escolhida
                        // Huffman
                        long iHuffDesc = System.currentTimeMillis();
                        try{
                            String readString = huffman.readCompressedFile(versao);
                            String decompressedString = huffman.decompress(readString);
                            FileWriter fw = new FileWriter("baseHuffmanCompressao"+versao+".txt");
                            BufferedWriter writer = new BufferedWriter(fw);
                            writer.write(decompressedString);
                            writer.close();
                        }catch(Exception e){
                            System.out.println("Erro na descompressão Huffman");
                        }
                        long fHuffDesc = System.currentTimeMillis()-iHuffDesc;
                        System.out.println("Tempo de descompressão Huffman: "+fHuffDesc+"ms");
                        // LZW
                        long iLzwDesc = System.currentTimeMillis();
                        try {
                            String fileName = "baseLzwCompressao"+versao;
                            byte[] compressedFileContent = Files.readAllBytes(Paths.get(fileName));
                            int[] compressedData = new int[compressedFileContent.length / 2];
                            for (int i = 0; i < compressedData.length; i++) {
                                compressedData[i] = ((compressedFileContent[2 * i] & 0xFF) << 8) | (compressedFileContent[2 * i + 1] & 0xFF);
                            }

                            byte[] decompressed = LZW.decompress(compressedData);
                            Files.write(Paths.get(fileName), decompressed);
                        } catch (Exception e){
                            System.out.println("Erro na descompressão LZW");
                        }
                        long fLzwDesc = System.currentTimeMillis()-iLzwDesc;
                        System.out.println("Tempo de descompressão LZW: "+fLzwDesc+"ms");
                        if(fHuffDesc<fLzwDesc){
                            System.out.print("Descompressão Huffman foi ");
                            System.out.printf("%.2f "+(1.0-((float)fHuffDesc/(float)fLzwDesc))*100);
                            System.out.println("% mais eficiente");
                        } else{
                            System.out.print("Descompressão LZW foi ");
                            System.out.printf("%.2f ", (1.0-((float)fLzwDesc/(float)fHuffDesc))*100);
                            System.out.println("% mais eficiente");
                        }
                        System.out.println("\nArquivo descomprimido com sucesso!");
                        break;
                        case 7:
                        lixo=sc.nextLine();
                        System.out.print("Digite o nome da musica que deseja encontrar: ");
                        String pattern = sc.nextLine();
                        // Procura do game
                        long iKmpComp = 0;
                        long iBoyerComp = 0;
                        try{
                            String caminhoArquivo = "musicas.csv";
                            String conteudoBanco = lerArquivoBD(caminhoArquivo);
                            // KMP.
                            iKmpComp = System.currentTimeMillis(); 
                            // Pesquisa no KMP
                            kmp.search(conteudoBanco, pattern);
                            iKmpComp = System.currentTimeMillis() - iKmpComp;
                            // Boyer Moore.
                            BoyerMoore bm = new BoyerMoore();
                            System.out.print("Use a heurística do mau caractere ou do sufixo bom? (1 - Sufixo bom; 2 - Mau caractere): ");
                            int heuristica = sc.nextInt();
                            boolean charOrSufix = false;
                            if(heuristica == 2){
                                charOrSufix = true;
                            }
                            iBoyerComp = System.currentTimeMillis();
                            // Pesquisa no Boyer Moore.
                            List<Integer> occurrences = bm.search(conteudoBanco, pattern, charOrSufix);
                            if (occurrences.isEmpty()) {
                            System.out.println("Padrão não encontrado no texto.");
                            }  
                            else {
                            System.out.println("Padrão encontrado nos índices: " + occurrences);
                            }
                            iBoyerComp = System.currentTimeMillis() - iBoyerComp;
                        }
                        catch (Exception e){
                            //System.out.println(e.getLocalizedMessage());
                            System.out.println("Erro no padrão");
                        }
                        // Comparação no tempo de pesquisa.
                        System.out.println("Tempo de pesquisa KMP: "+iKmpComp+"ms");
                        System.out.println("Tempo de pesquisa BoyerMoore: "+iBoyerComp+"ms");
                        System.out.println();
                        if(iKmpComp<iBoyerComp){
                            System.out.println("KMP foi mais eficiente");
                        } else{
                            System.out.println("BoyerMoore foi mais eficiente");
                        }
                        break;
                        case 8:
                        System.out.print("\nSalvando arquivo e encerrando programa...");
                        break;
                        default:
                        System.out.println("Digite uma opção válida!\n\n");
                    }
                    } catch(Exception e){
                        System.out.println("Digite uma opção válida!\n\n");
                    }
                    }
                    System.out.println("\n");
                    System.out.println(" _______________");
                    System.out.println("|               |");
                    System.out.println("|FIM DO PROGRAMA|");
                    System.out.println("|_______________|");
                // Fechamento do CSV
                fstream.close();
                sc.close();
            }
            catch(IOException e) { e.printStackTrace(); }
        }catch (IOException e){
            e.getStackTrace();
        }
    }

       public static String lerArquivoBD(String caminhoArquivo) throws IOException {
    StringBuilder conteudo = new StringBuilder();

    BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));
    String linha;
    while ((linha = leitor.readLine()) != null) {
        conteudo.append(linha);
        conteudo.append("\n"); // Adicione uma quebra de linha, se necessário
    }
    leitor.close();

    return conteudo.toString();
}
       
    }   
