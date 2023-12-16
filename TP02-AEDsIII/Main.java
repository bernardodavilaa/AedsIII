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
    public static int version = 0;

    public static void main(String[] args) throws IOException {
        try {
            CRUD crud = new CRUD("BancoDados");
            try {
                // Ler o arquivo CSV
                String csv = "musicas.csv";

                FileInputStream fInputStream = new FileInputStream(csv);
                BufferedReader br = new BufferedReader(new InputStreamReader(fInputStream));

                // Leitura de todo o CSV
                String line;

                while ((line = br.readLine()) != null) {
                    Musica m = new Musica(line);
                    crud.create(m);
                }

                Scanner sc = new Scanner(System.in);

                int opcao = 0;
                String lixo = "";

                while (opcao != 7) {
                    System.out.println("Digite qual operação deseja realizar:");
                    System.out.println("1 - Create                           ");
                    System.out.println("2 - Read                             ");
                    System.out.println("3 - Update                           ");
                    System.out.println("4 - Delete                           ");    
                    System.out.println("5 - Criptografar                     ");
                    System.out.println("6 - Descriptografar                  ");
                    System.out.println("7 - Saída                            ");
                    System.out.println("|____________________________________");
                    System.out.println();

                    try {
                        opcao = sc.nextInt();
                        System.out.println();
                        switch (opcao) {

                            // CREATE
                            case 1:
                                try {
                                    System.out.println("Digite o ID: ");
                                    int readID = sc.nextInt();
                                    if (crud.Pesquisar(readID)) {
                                        System.out.println("Digite o nome do artista: ");
                                        sc.nextLine();
                                        String novoArtistName = sc.nextLine();
                                        System.out.println("Digite a nova data: ");
                                        System.out.println("Ex.: dd/MM/yyyy");
                                        String novaData = sc.nextLine();
                                        System.out.println("Digite o tipo de lançamento: ");
                                        String novoReleaseType = sc.nextLine();
                                        System.out.println("Digite o nome de lançamento:");
                                        String novoReleaseName = sc.nextLine();
                                        System.out.println("Digite a quantidade de Reviews: ");
                                        int novoReviewsCount = sc.nextInt();
                                        System.out.println("Digite o(s) genero(s) separado(s) por vírgula(s): ");
                                        System.out.println("Ex.: Pop-Rock, Rock, Country");
                                        sc.nextLine();
                                        String[] novoGenero = sc.nextLine().split(",");
                                        ArrayList<String> g = new ArrayList();
                                        for (int i = 0; i < novoGenero.length; i++)
                                            g.add(novoGenero[i]);
                                        crud.Create(readID, novoArtistName, novaData, novoReleaseType, novoReviewsCount,
                                                novoGenero, novoReleaseName);
                                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                                        Date date = formato.parse(novaData);
                                        Musica mus = new Musica(readID, novoArtistName, date, novoReleaseType,
                                                novoReviewsCount, g, true, novoReleaseName);
                                        long pos = crud.getPos(mus);
                                        System.out.println("\nArquivo criado!\n");
                                        System.out.print("ID: " + crud.Read(readID).getId() + ", Nome Artista: "
                                                + crud.Read(readID).getArtistName() + ", Nome de Lançamento: "
                                                + crud.Read(readID).getReleaseName() + ", Data de Lançamento: "
                                                + crud.Read(readID).getReleaseData() + ", Tipo de Lançamento: "
                                                + crud.Read(readID).getReleaseType() + ", Quantidade de Reviews: "
                                                + crud.Read(readID).getReviewCount() + ", ");
                                        System.out.print(" Gênero(s): ");
                                        for (int i = 0; i < crud.Read(readID).getGenres().size(); i++) {
                                            System.out.print(crud.Read(readID).getGenres().get(i));
                                            if (i != crud.Read(readID).getGenres().size() - 1) {
                                                System.out.print(", ");
                                            }
                                        }
                                        System.out.println("");
                                    } else
                                        System.out.println("ID já existente.");
                                } catch (Exception e) {
                                    System.out.println("Erro ao criar Arquivo.");
                                }

                                break;

                            // READ
                            case 2:
                                try {
                                    System.out.println("Digite o ID: ");
                                    int readID = sc.nextInt();
                                    crud.Read(readID).getId(); // Teste para ver se música existe (teste de pointer)
                                    if (crud.Read(readID).getLapide()) {
                                        System.out.println("\nArquivo encontrado!\n");
                                        System.out.print("ID: " + crud.Read(readID).getId() + ", Nome Artista: "
                                                + crud.Read(readID).getArtistName() + ", Data de Lançamento: "
                                                + crud.Read(readID).getReleaseData() + ", Nome de Lançamento: "
                                                + crud.Read(readID).getReleaseName() + ", Tipo de Lançamento: "
                                                + crud.Read(readID).getReleaseType() + ", Quantidade de Reviews: "
                                                + crud.Read(readID).getReviewCount() + ", ");
                                        System.out.print(" Gênero(s): ");
                                        for (int i = 0; i < crud.Read(readID).getGenres().size(); i++) {
                                            System.out.print(crud.Read(readID).getGenres().get(i));
                                            if (i != crud.Read(readID).getGenres().size() - 1) {
                                                System.out.print(", ");
                                            }
                                        }
                                        System.out.println("");
                                    } else
                                        System.out.println("Arquivo não Encontrado!");
                                    ;
                                } catch (Exception e) {
                                    System.out.println("\nArquivo não encontrado!");
                                }

                                break;

                            // UPDATE
                            case 3:
                                try {
                                    System.out.println("Infome o ID: ");
                                    int readID = sc.nextInt();
                                    boolean resp = crud.PesquisarID_Apagado(readID);
                                    if (resp == false) {
                                        System.out.println("ID já cadastrado");
                                        break;
                                    }
                                    System.out.println("Digite o ID atualizado: ");
                                    int novoID = sc.nextInt();
                                    if ((crud.Pesquisar(novoID))) {
                                        System.out.println("Digite o nome do artista: ");
                                        sc.nextLine();
                                        String novoArtistName = sc.nextLine();
                                        System.out.println("Digite a nova data: ");
                                        System.out.println("Ex.: dd/MM/yyyy");
                                        String novaData = sc.nextLine();
                                        System.out.println("Digite o tipo de lançamento: ");
                                        String novoReleaseType = sc.nextLine();
                                        System.out.println("Digite o nome de lançamento: ");
                                        String novoReleaseName = sc.nextLine();
                                        System.out.println("Digite a quantidade de Reviews: ");
                                        int novoReviewsCount = sc.nextInt();
                                        System.out.println("Digite o(s) genero(s) separado(s) por vírgula(s): ");
                                        System.out.println("Ex.: Pop-Rock, Rock, Country");
                                        sc.nextLine();
                                        String[] novoGenero = sc.nextLine().split(",");
                                        crud.Delete(readID);
                                        crud.Create(novoID, novoArtistName, novaData, novoReleaseType, novoReviewsCount,
                                                novoGenero, novoReleaseName);
                                        System.out.println("\nArquivo atualizado!\n");
                                        System.out.print("ID: " + crud.Read(novoID).getId() + ", Nome Artista: "
                                                + crud.Read(novoID).getArtistName() + ", Data de Lançamento: "
                                                + crud.Read(novoID).getReleaseData() + ", Nome de Lançamento: "
                                                + crud.Read(novoID).getReleaseName() + ", Tipo de Lançamento: "
                                                + crud.Read(novoID).getReleaseType() + ", Quantidade de Reviews: "
                                                + crud.Read(novoID).getReviewCount() + ", ");
                                        System.out.print(" Gênero(s): ");
                                        for (int i = 0; i < crud.Read(novoID).getGenres().size(); i++) {
                                            System.out.print(crud.Read(novoID).getGenres().get(i));
                                            if (i != crud.Read(novoID).getGenres().size() - 1) {
                                                System.out.print(", ");
                                            }
                                        }
                                        System.out.println("");
                                    } else {
                                        System.out.println("ID já cadastrado");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Erro ao Atualizar o arquivo.");
                                }

                                break;

                            // DELETE
                            case 4:
                                try {
                                    System.out.println("Informe o ID: ");
                                    int readID = sc.nextInt();
                                    crud.Read(readID).getId(); // Teste para ver se música existe (teste de pointer)
                                    if (crud.Read(readID).getLapide()) {
                                        System.out.println("\nArquivo encontrado!\n");
                                        System.out.print("ID: " + crud.Read(readID).getId() + ", Nome Artista: "
                                                + crud.Read(readID).getArtistName() + ", Nome de Lançamento: "
                                                + crud.Read(readID).getReleaseName() + ", Data de Lançamento: "
                                                + crud.Read(readID).getReleaseData() + ", Tipo de Lançamento: "
                                                + crud.Read(readID).getReleaseType() + ", Quantidade de Reviews: "
                                                + crud.Read(readID).getReviewCount() + ", ");
                                        System.out.print(" Genero(s): ");
                                        for (int i = 0; i < crud.Read(readID).getGenres().size(); i++) {
                                            System.out.print(crud.Read(readID).getGenres().get(i));
                                            if (i != crud.Read(readID).getGenres().size() - 1) {
                                                System.out.print(", ");
                                            }
                                        }
                                        System.out.println("");
                                        crud.Delete(readID);
                                        System.out.println(" ");
                                        System.out.println("Arquivo deletado com sucesso!");
                                        System.out.println(" ");
                                    } else
                                        System.out.println("Arquivo não Encontrado");
                                } catch (Exception e) {
                                    System.out.println("Erro ao deletar Arquivo.");
                                }

                                break;
                            case 5:
                        // Ciframento de César
                        BufferedWriter bwCesar = new BufferedWriter(new FileWriter("Cesar.txt"));                        
                        String caminhoArquivo = "games.csv";
                        String conteudoBanco = lerArquivoBD(caminhoArquivo);
                        String txtCifradoCesar = cesar.cifrar(conteudoBanco);
                        bwCesar.write(txtCifradoCesar);
                        bwCesar.close();
                        // Cifra por substituição
                        
                        String txtCifradoSubstituicao = substituicao.criptografa(conteudoBanco);
                        FileWriter crip = new FileWriter("substituicao.txt");
                        crip.write(txtCifradoSubstituicao);
                        crip.close();
                        System.out.println("Arquivos criptografados com sucesso!\n");
                        break;
                        case 6:
                        // Cesar
                        int chaveCesarDecrip = 3;
                        CifraCesar cesarDecript = new CifraCesar(chaveCesarDecrip);
                        BufferedReader brCesar = new BufferedReader(new FileReader("Cesar.txt"));
                        String lineCesar;
                        StringBuilder strBuilder = new StringBuilder();
                        while ((lineCesar = brCesar.readLine()) != null) {
                        strBuilder.append(lineCesar);
                        strBuilder.append("\n");
                        }
                        String cesarDecrip = strBuilder.toString();
                        cesarDecrip = cesarDecript.decifrar(cesarDecrip); // Descriptografia.
                        brCesar.close();
                        FileWriter escreveCesar = new FileWriter("Cesar.txt");
                        escreveCesar.write(cesarDecrip);
                        escreveCesar.close();

                        // Cifra por substituição
                        try {
                            // Abre arquivo criptografado para leitura
                            BufferedReader reader = new BufferedReader(new FileReader("substituicao.txt"));
                            StringBuilder textoCriptografadoBuilder = new StringBuilder();
                            String linhaArq;
                            while ((linhaArq = reader.readLine()) != null) {
                                // Lê todo arquivo e salva em um String Builder
                                textoCriptografadoBuilder.append(linhaArq);
                                textoCriptografadoBuilder.append("\n");
                            }
                            reader.close();
                            // Converte String Builder para String
                            String textoCriptografado = textoCriptografadoBuilder.toString();

                            // Descriptografa o texto
                            String textoDescriptografado = substituicao.descriptografa(textoCriptografado);

                            // Sobrescreve o arquivo com o texto descriptografado
                            FileWriter writer = new FileWriter("substituicao.txt");
                            writer.write(textoDescriptografado);
                            writer.close();

                            System.out.println("Arquivos descriptografados com sucesso!\n");
                        } catch (IOException e) {
                            System.out.println("Erro ao ler ou escrever no arquivo");
                            e.printStackTrace();
                        }
                        break;
                        case 7:
                        System.out.print("\nSalvando arquivo e encerrando programa...");
                            default:
                                System.out.println("Opção Inválida! Digite Novamente: \n\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Opção Inválida, digite novamente!\n\n");
                    }
                }

                System.out.println("\n");
                // Fechamento do CSV
                fInputStream.close();
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static String lerArquivo(String caminhoArquivo) throws IOException {
        StringBuilder conteudo = new StringBuilder();

        BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));
        String linha;
        while ((linha = leitor.readLine()) != null) {
            conteudo.append(linha);
            conteudo.append("\n");
        }
        leitor.close();

        return conteudo.toString();
    }

    