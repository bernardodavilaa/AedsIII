import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

class Main {
    public static void main(String[]args){
        Musica m= new Musica();
        while (!(enderecoArq.equals("FIM"))){
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(enderecoArq), "UTF-8"))){
                String entrada= br.readLine();
                
            }
        }



        FileOutputStrem arq;    
        DataOutputStream dos;
        
        try{
            arq=new FileOutputStrem("musica.db");
            dos= new DataOutputStream(arq);
            dos.writeInt()
        }

    }
}
