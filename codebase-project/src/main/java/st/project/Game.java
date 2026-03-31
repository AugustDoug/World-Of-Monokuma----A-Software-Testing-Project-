package st.project;

public class Game {

    public int[][] mapa = {
        {0, 1, 0, 0, 0},
        {0, 1, 0, 1, 0},
        {0, 0, 0, 1, 0},
        {1, 1, 0, 0, 0},
        {0, 0, 0, 1, 2}
    };

    public int x;
    public int y;

    public Game() {
        x = 0;
        y = 0;
    }

    // 🎮 Movimento
    public boolean movePlayer(String direction) {
        int newX = x;
        int newY = y;

        switch(direction) {
            case "north": newX--; break;
            case "south": newX++; break;
            case "west": newY--; break;
            case "east": newY++; break;
            default: return false;
        }

        //  limites
        if (newX < 0 || newX >= mapa.length || newY < 0 || newY >= mapa[0].length) {
            return false;
        }

        //  parede
        if (mapa[newX][newY] == 1) {
            return false;
        }

        //  move
        x = newX;
        y = newY;
        return true;
    }

    //  posição
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //  mapa
    public int[][] getMapa() {
        return mapa;
    }

    //  vitória
    public boolean venceu() {
        return mapa[x][y] == 2;
    }
}