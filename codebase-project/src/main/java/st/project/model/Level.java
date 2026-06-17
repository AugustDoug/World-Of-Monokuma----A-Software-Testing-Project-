package st.project.model;

public class Level {

    private int[][] mapa;

    public Level(int nivel) {

        switch(nivel) {

            // =========================
            // NÍVEL 1 (pequeno)
            // =========================
            case 1:

                mapa = new int[][] {

                    // 0 = vazio
                    // 1 = parede
                    // 2 = saída

                    {0, 0, 0},
                    {1, 1, 0},
                    {0, 0, 2}
                };

                break;

            // =========================
            // NÍVEL 2
            // chave + alçapão
            // =========================
            case 2:

                mapa = new int[][] {

                    // 3 = alçapão
                    // 4 = chave

                    {0, 0, 1, 0, 0},
                    {1, 0, 1, 0, 1},
                    {4, 0, 0, 3, 0},
                    {1, 1, 0, 1, 0},
                    {0, 0, 0, 0, 2}
                };

                break;

            // =========================
            // NÍVEL 3
            // vários alçapões
            // sem chave
            // =========================
            default:

                mapa = new int[][] {

                    {0, 0, 1, 0, 0, 0},
                    {1, 0, 1, 0, 1, 0},
                    {0, 3, 0, 3, 1, 0},
                    {0, 1, 1, 0, 0, 0},
                    {0, 0, 3, 1, 3, 0},
                    {1, 0, 0, 0, 0, 2}
                };

                break;
        }
    }

    public int[][] getMapa() {
        return mapa;
    }

    
}