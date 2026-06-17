package st.project.controller;

import st.project.model.Game;

public class GameController {

    public Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public String processarComando(String input) {

        if(input == null || input.trim().isEmpty()) {
            return "Comando vazio!";
        }

        String[] partes =
            input.trim()
                .toLowerCase()
                .split(" ");

        // comando ranking
        if(partes.length == 1 &&
        partes[0].equals("ranking")) {

            return "RANKING";
        }

        if(partes.length != 2) {
            return "Comando inválido!";
        }

        if(!partes[0].equals("go")) {
            return "Use GO";
        }

        String direcao = partes[1];

        boolean moved =
            game.movePlayer(direcao);

        if(!moved) {
            return "Não pode mover!";
        }

        return "Movendo para " + direcao;
    }
}