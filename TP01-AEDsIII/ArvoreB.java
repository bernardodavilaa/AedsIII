import java.io.IOException;
import java.io.RandomAccessFile;

public class ArvoreB {

    // Declaração de atributos

    private RandomAccessFile arq, file, arq2;
    private NoArvore raiz;
    private int ordem;
    private int quant;

    // Construtor

    public ArvoreB(int ordem) {
        this.ordem = ordem;
        this.raiz = null;
        try{
        arq = new RandomAccessFile("arqArvore", "rw");
        arq2= new RandomAccessFile("arqHash", "rw");
        arq.seek(0);
        arq2.seek(0);
        file = new RandomAccessFile("arqArvore", "rw");
        }catch(Exception e){}
        quant=0;
    }


    // Método de inserção

    public void imprimir(){
      Musica m= new Musica();
        try{
            file= new RandomAccessFile("BancoDadosOrdenado", "rw");
            CRUD crud = new CRUD("BancoDados.db");
            NoArvore no= new NoArvore(ordem);
            for(int i=0;i<5000;i++){
            try{
                no=busca(i);
            }
            catch(Exception e){
                i++;
            }
                m=crud.Read(i);
                if(m.getLapide()==true){
                file.seek(0); // Ponteiro vai para o inicio do arquivo
                file.writeInt(m.getId()); // Escreve no inicio do registro o seu ID
                file.seek(file.length()); // Ponteiro vai para o final do arquivo

                byte[] byteArr = m.toByteArray(); // Vetor de Bytes populado com os dados do     CSV já filtrados
                file.writeInt(m.toByteArray().length); // Escreve o tamanho desse vetor de Bytes
                file.write(byteArr); // Escreve o vetor de Bytes
                }
     
            
        }
    }catch(Exception e){
        System.out.println();
    }
    }


    public void insere(int chave, long pos) {
        // Atualizar arquivo
        quant++;
        try{
            arq.seek(0);
            arq2.seek(0);
            arq.writeInt(quant);
            arq2.writeInt(quant);
            arq.seek(arq.length());
            arq2.seek(arq.length());
            arq.writeInt(chave);
            arq2.writeInt(chave);
            arq.writeLong(pos);
            arq2.writeLong(pos);
        } catch(Exception e){}
        // Checa se árvore está vazia
        if (raiz == null) {
            // Cria nova a raiz e prepara a árvore
            raiz = new NoArvore(ordem);
            raiz.setChave(0, chave);
            raiz.setPos(0, pos);
            raiz.setNumChaves(1);
        } else {
            // Checa se nó está cheio
            if (raiz.getNumChaves() == 2 * ordem - 1) {
                // Cria novo nó e balanceia árvore
                NoArvore novoNo = new NoArvore(ordem);
                novoNo.setFilho(0, raiz);
                divideNo(novoNo, 0, raiz);
                int i = 0;
                if (novoNo.getChave(0) < chave) {
                    i++;
                }
                insereNoNaoCheio(novoNo.getFilho(i), chave, pos);
                raiz = novoNo;
            } else {
                // Insere no nó designado caso não esteja cheio
                insereNoNaoCheio(raiz, chave, pos);
            }
        }
    }

    // Método para balancear árvore

    private void divideNo(NoArvore noPai, int index, NoArvore noFilho) {
        // Cria novo nó
        NoArvore novoNoFilho = new NoArvore(ordem);
        novoNoFilho.setNumChaves(ordem - 1);
        // Preenche novo nó
        for (int j = 0; j < ordem - 1; j++) {
            novoNoFilho.setChave(j, noFilho.getChave(j + ordem));
            novoNoFilho.setPos(j, noFilho.getPos(j+ordem));
        }
        // Checa se nó filho não é folha
        if (!noFilho.isFolha()) {
            for (int j = 0; j < ordem; j++) {
                novoNoFilho.setFilho(j, noFilho.getFilho(j + ordem));
            }
        }

        // Prepara nós filhos

        noFilho.setNumChaves(ordem - 1);

        for (int j = noPai.getNumChaves(); j >= index + 1; j--) {
            noPai.setFilho(j + 1, noPai.getFilho(j));
        }

        noPai.setFilho(index + 1, novoNoFilho);

        for (int j = noPai.getNumChaves() - 1; j >= index; j--) {
            noPai.setChave(j + 1, noPai.getChave(j));
            noPai.setPos(j+1, noPai.getPos(j));
        }

        // Termina balanceamento da árvore

        noPai.setChave(index, noFilho.getChave(ordem - 1));
        noPai.setPos(index, noFilho.getPos(ordem-1));
        noPai.incrementaChaves();
    }

    // Método de inserção

    private void insereNoNaoCheio(NoArvore no, int chave, long pos) {
        int i = no.getNumChaves() - 1;
        // Checa se o nó é folha
        if (no.isFolha()) {
            // Redefine o jeito que o nó está organizado
            while (i >= 0 && chave < no.getChave(i)) {
                no.setChave(i + 1, no.getChave(i));
                no.setPos(i + 1, no.getPos(i));
                i--;
            }
            // Insere novos valores
            no.setChave(i + 1, chave);
            no.setPos(i+1, pos);
            no.incrementaChaves();
        } else {
            // Vai para posição onde o ID deve ser inserido
            while (i >= 0 && chave < no.getChave(i)) {
                i--;
            }

            i++;
            // Checa se nó está cheio
            if (no.getFilho(i).getNumChaves() == 2 * ordem - 1) {
                // Gera novos nós
                divideNo(no, i, no.getFilho(i));

                if (chave > no.getChave(i)) {
                    i++;
                }
            }
            // Insere ID no nó
            insereNoNaoCheio(no.getFilho(i), chave, pos);
        }
    }
    // Método de busca que retorna o nó
    public NoArvore busca(int chave) {
        return busca(raiz, chave);
    }
    // Chama o método com a raiz
    private NoArvore busca(NoArvore no, int chave) {
        int i = 0;
        // Procura a posição dentro do nó
        while (i < no.getNumChaves() && chave > no.getChave(i)) {
            i++;
        }
        // Checa se posição corresponde ao ID procurado
        if (i < no.getNumChaves() && chave == no.getChave(i)) {
            // Se sim, retorna o nó desejado
            return no;
        }
        // Se não achar, retorna null
        if (no.isFolha()) {
            return null;
        }
        // Busca no próximo nó caso o atual não seja folha
        return busca(no.getFilho(i), chave);
    }
    // Método de busca que retorna a posição da Musica no arquivo binário (retorna o ponteiro)
    public long buscaPos(int chave) {
        return buscaPos(raiz, chave);
    }
    // Chama método passando a raiz
    private long buscaPos(NoArvore no, int chave) {
        int i = 0;
        // Procura a posição no nó
        while (i < no.getNumChaves() && chave > no.getChave(i)) {
            i++;
        }
        // Checa se o elemento da posição é o nó
        if (i < no.getNumChaves() && chave == no.getChave(i)) {
            return no.getPos(i);
        }
        // Se for folha, retorna 0
        if (no.isFolha()) {
            return 0;
        }
        // Refaz a busca no próximo nó caso esse não seja folha
        return buscaPos(no.getFilho(i), chave);
    }
    // Método de busca que retorna o índice do ID desejado dentro do nó
    public int buscaI(int chave) {
        return buscaI(raiz, chave);
    }
    // Chama método passando a raiz
    private int buscaI(NoArvore no, int chave) {
        int i = 0;
        // Procura a posição do ID no nó
        while (i < no.getNumChaves() && chave > no.getChave(i)) {
            i++;
        }
        // Checa se posição corresponde ao ID
        if (i < no.getNumChaves() && chave == no.getChave(i)) {
            // Retorna índice
            return i;
        }
        // Checa se é folha
        if (no.isFolha()) {
            return 9; // Retornar erro
        }
        // Continua busca no próximo nó
        return buscaI(no.getFilho(i), chave);
    }
    // Método de remover a partir do ID
    public void remove(int chave) {
        if (raiz == null) {
            return;
        }
    
        remove(raiz, chave);
        // Se árvore estiver vazia
        if (raiz.getNumChaves() == 0) {
            if (raiz.isFolha()) {
                raiz = null;
            } else {
                raiz = raiz.getFilho(0);
            }
        }
    }
    // Chama método passando raiz
    private void remove(NoArvore no, int chave) {
        int i = 0;
        // Acha posição
        while (i < no.getNumChaves() && chave > no.getChave(i)) {
            i++;
        }
        // Checa se a posição corresponde ao ID
        if (i < no.getNumChaves() && chave == no.getChave(i)) {
            if (no.isFolha()) {
                removeChaveFolha(no, i);
            } else {
                removeChaveNaoFolha(no, i);
            }
        } else {
            if (no.isFolha()) {
                return;
            }
            // Checa tamanho do nó
            boolean flag = (i == no.getNumChaves());
    
            if (no.getFilho(i).getNumChaves() < ordem) {
                preenche(no, i);
            }
            
            if (flag && i > no.getNumChaves()) {
                remove(no.getFilho(i - 1), chave);
            } else {
                remove(no.getFilho(i), chave);
            }
        }
    }
    // Método para remoção de folha
    private void removeChaveFolha(NoArvore no, int index) {
        // Remove a chave desejada da folha
        for (int i = index + 1; i < no.getNumChaves(); ++i) {
            no.setPos(i-1, no.getPos(i));
            no.setChave(i - 1, no.getChave(i));
        }
        // Decrementa chave do nó
        no.decrementaChaves();
    }
    // Método para remoção de nó que não é folha
    private void removeChaveNaoFolha(NoArvore no, int index) {
        int chave = no.getChave(index);
        // Checa se nó está sobrecarregado
        if (no.getFilho(index).getNumChaves() >= ordem) {
            // Balanceia o nó
            int predecessor = getPredecessor(no, index);
            no.setChave(index, predecessor);
            remove(no.getFilho(index), predecessor);
        } else if (no.getFilho(index + 1).getNumChaves() >= ordem) {
            // Checa se nó vai ficar acima do tamanho
            int sucessor = getSucessor(no, index);
            no.setChave(index, sucessor);
            remove(no.getFilho(index + 1), sucessor);
        } else {
            // Balanceia árvore fazendo a remoção
            merge(no, index);
            remove(no.getFilho(index), chave);
        }
    }
    // Método para pegar predecessor
    private int getPredecessor(NoArvore no, int index) {
        // Vai para o nó filho
        NoArvore cur = no.getFilho(index);
        while (!cur.isFolha()) {
            cur = cur.getFilho(cur.getNumChaves());
        }
        // Retorna quantidade de chaves do nó filho
        return cur.getChave(cur.getNumChaves() - 1);
    }
    // Método para pegar sucessor
    private int getSucessor(NoArvore no, int index) {
        // Vai para nó filho
        NoArvore cur = no.getFilho(index + 1);
        while (!cur.isFolha()) {
            cur = cur.getFilho(0);
        }
        // Retorna primeiro elemento do filho
        return cur.getChave(0);
    }
    // Método para preeencher nó
    private void preenche(NoArvore no, int index) {
        // Preenche nó
        if (index != 0 && no.getFilho(index - 1).getNumChaves() >= ordem) {
            moveChaveParaDireita(no, index);
        } else if (index != no.getNumChaves() && no.getFilho(index + 1).getNumChaves() >= ordem) {
            moveChaveParaEsquerda(no, index);
        } else {
            // Realiza balanceamento
            if (index != no.getNumChaves()) {
                merge(no, index);
            } else {
                merge(no, index - 1);
            }
        }
    }
    // Método para mover a chave para a direita
    private void moveChaveParaDireita(NoArvore no, int index) {
        // Remaneja nó para esquerda
        NoArvore filho = no.getFilho(index);
        NoArvore irmao = no.getFilho(index - 1);
        // Adiciona nó à direita
        for (int i = filho.getNumChaves() - 1; i >= 0; --i) {
            filho.setChave(i + 1, filho.getChave(i));
        }
    
        if (!filho.isFolha()) {
            for (int i = filho.getNumChaves(); i >= 0; --i) {
                filho.setFilho(i + 1, filho.getFilho(i));
            }
        }
    
        filho.setChave(0, no.getChave(index - 1));
    
        if (!no.isFolha()) {
            filho.setFilho(0, irmao.getFilho(irmao.getNumChaves()));
        }
    
        no.setChave(index - 1, irmao.getChave(irmao.getNumChaves() - 1));
        // Muda o balanceamento da árvore
        filho.incrementaChaves();
        irmao.decrementaChaves();
    }
    // Método para mover a chave para a esquerda
    private void moveChaveParaEsquerda(NoArvore no, int index) {
        // Remaneja nó para direita
        NoArvore filho = no.getFilho(index);
        NoArvore irmao = no.getFilho(index + 1);
        // Prepara inserção do nó
        filho.setChave(filho.getNumChaves(), no.getChave(index));
    
        if (!filho.isFolha()) {
            filho.setFilho(filho.getNumChaves() + 1, irmao.getFilho(0));
        }
    
        no.setChave(index, irmao.getChave(0));
    
        for (int i = 1; i < irmao.getNumChaves(); ++i) {
            irmao.setChave(i - 1, irmao.getChave(i));
        }
    
        if (!irmao.isFolha()) {
            for (int i = 1; i <= irmao.getNumChaves(); ++i) {
                irmao.setFilho(i - 1, irmao.getFilho(i));
            }
        }
        // Realiza balanceamento da árvore
        filho.incrementaChaves();
        irmao.decrementaChaves();
    }
    // Método para utilização do merge
    private void merge(NoArvore no, int index) {
        // Preparação utilizando atributos
        NoArvore filho = no.getFilho(index);
        NoArvore irmao = no.getFilho(index + 1);
    
        filho.setChave(ordem - 1, no.getChave(index));
        filho.setPos(ordem-1, no.getPos(index));
        // Reorganizando nó
        for (int i = 1; i < irmao.getNumChaves(); ++i) {
            filho.setChave(i + ordem - 1, irmao.getChave(i));
            filho.setPos(i+ ordem -1, irmao.getPos(i));
        }
        // Checa se não é folha
        if (!filho.isFolha()) {
            for (int i = 1; i <= irmao.getNumChaves(); ++i) {
                filho.setFilho(i + ordem - 1, irmao.getFilho(i));
            }
        }
    
        for (int i = index + 1; i < no.getNumChaves(); ++i) {
            no.setChave(i - 1, no.getChave(i));
            no.setPos(i-1,no.getPos(i));
        }
    
        for (int i = index + 2; i <= no.getNumChaves(); ++i) {
            no.setFilho(i - 1, no.getFilho(i));
        }
        // Termina balanceamento
        filho.setNumChaves(filho.getNumChaves() + irmao.getNumChaves() + 1);
        no.decrementaChaves();
    }
}    