package st.project.model;

public class Player {

    public int x;
    public int y;

    private int pontuacao;

    private boolean possuiChave;

    public Player() {
        x = 0;
        y = 0;
        pontuacao = 0;
        possuiChave = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void adicionarPontos(int pontos) {
        pontuacao += pontos;
    }

    public boolean temChave() {
        return possuiChave;
    }

    public void pegarChave() {
        possuiChave = true;
    }

    public void removerChave() {
        possuiChave = false;
    }
}