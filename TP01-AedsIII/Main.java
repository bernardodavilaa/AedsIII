import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.sql.rowset.spi.SyncResolver;

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
        String deucerto = sc.nextLine();
        System.out.println("Deu Certo?");
    }

}
