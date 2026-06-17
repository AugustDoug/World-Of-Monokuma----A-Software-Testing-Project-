package st.project.model;

public class User {

    private String login;

    private String senha;

    private String avatar;

    private int pontuacao;

    private int sessoes;

    private boolean admin;

    public User(
        String login,
        String senha,
        String avatar,
        boolean admin
    ) {

        this.login = login;
        this.senha = senha;
        this.avatar = avatar;
        this.admin = admin;

        pontuacao = 0;
        sessoes = 0;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void adicionarPontuacao(int pontos) {
        pontuacao += pontos;
    }

    public int getSessoes() {
        return sessoes;
    }

    public void adicionarSessao() {
        sessoes++;
    }

    public boolean isAdmin() {
        return admin;
    }
}