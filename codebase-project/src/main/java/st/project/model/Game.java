package st.project.model;

public class Game {

    private User usuario;

    public Player player;

    private Level level;

    public int nivel;

    public boolean venceu = false;

    public Game(User usuario) {

        this.usuario = usuario;

        nivel = 1;

        player = new Player();

        carregarNivel();

        
    }

    private void carregarNivel() {

        level = new Level(nivel);

        player.setPosicao(0, 0);

        
    }

    public boolean movePlayer(String direction) {

        int newX = player.getX();
        int newY = player.getY();

        switch(direction) {

            case "north":
                newX--;
                break;

            case "south":
                newX++;
                break;

            case "west":
                newY--;
                break;

            case "east":
                newY++;
                break;

            default:
                return false;
        }

        int[][] mapa = level.getMapa();

        // limites
        if(newX < 0 || newX >= mapa.length ||
           newY < 0 || newY >= mapa[0].length) {

            return false;
        }

        // parede
        if(mapa[newX][newY] == 1) {
            return false;
        }

        // move
        player.setPosicao(newX, newY);

        player.adicionarPontos(10);

        usuario.adicionarPontuacao(10);

        verificarEventos();

        return true;
    }

    private void verificarEventos() {

        int valor =
            level.getMapa()
            [player.getX()]
            [player.getY()];

        // chave
        if(valor == 4) {

            player.pegarChave();

            player.adicionarPontos(100);

            usuario.adicionarPontuacao(100);

            level.getMapa()
                [player.getX()]
                [player.getY()] = 0;
        }

        // alçapão
        if(valor == 3) {

            if(!player.temChave()) {

                nivel--;

                if(nivel < 1) {
                    nivel = 1;
                }

                carregarNivel();
            }
            else {

                // perde chave ao passar
                player.removerChave();

                // remove alçapão usado
                level.getMapa()
                    [player.getX()]
                    [player.getY()] = 0;
            }
        }

        // saída
        if(valor == 2) {

            if(nivel == 3) {

                player.adicionarPontos(1000);

                venceu();

                return;
            }

            nivel++;

            player.adicionarPontos(500);

            carregarNivel();
        }
    }

    public int[][] getMapa() {
        return level.getMapa();
    }

    public int getPlayerX() {
        return player.getX();
    }

    public int getPlayerY() {
        return player.getY();
    }

    public int getNivel() {
        return nivel;
    }

    public int getPontuacao() {
        return player.getPontuacao();
    }

    public boolean temChave() {
        return player.temChave();
    }
    public void venceu(){
        this.venceu = true;
    };
}