public class NoArvore {

    // Declaração de atributos

    private int ordem;
    private int numChaves;
    private int[] chaves;
    private long[] pos;
    private NoArvore[] filhos;

    // Construtor

    public NoArvore(int ordem) {
        this.ordem = ordem;
        this.numChaves = 0;
        this.chaves = new int[2 * ordem - 1];
        this.pos = new long[2 * ordem - 1];
        this.filhos = new NoArvore[2 * ordem];
    }


    // Métodos de acesso e modificação dos campos

    public int getOrdem() {
        return ordem;
    }

    public int getNumChaves() {
        return numChaves;
    }

    public void setNumChaves(int numChaves) {
        this.numChaves = numChaves;
    }

    public int getChave(int i) {
        return chaves[i];
    }

    public long getPos(int i) {
        return pos[i];
    }

    public void setChave(int i, int chave) {
        chaves[i] = chave;
    }

    public void setPos(int i, long posicao) {
        pos[i] = posicao;
    }

    public NoArvore getFilho(int i) {
        return filhos[i];
    }

    public void setFilho(int i, NoArvore filho) {
        filhos[i] = filho;
    }

    // Métodos auxiliares
    
    public boolean isFolha() {
        return filhos[0] == null;
    }

    public void incrementaChaves() {
        numChaves++;
    }

    public void decrementaChaves() {
        numChaves--;
    }
}