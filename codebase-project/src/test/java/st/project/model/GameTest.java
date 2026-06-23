package st.project.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import st.project.model.Game;
import st.project.model.User;

public class GameTest {

    private Game jogo;

    private User user;

    @BeforeEach
    void setup() {

        user =
            new User(
                "login",
                "senha",
                "avatar",
                false
            );

        jogo = new Game(user);
    }

    // =========================================
    // TESTES DE DOMÍNIO
    // =========================================

    @Test
    @DisplayName("Domínio: Deve mover jogador para leste")
    void deveMoverPlayer() {

        boolean resultado =
            jogo.movePlayer("east");

        assertThat(resultado)
            .isTrue();

        assertThat(jogo.getPlayerY())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Domínio: Não deve mover com direção inválida")
    void naoDeveMoverDirecaoInvalida() {

        boolean resultado =
            jogo.movePlayer("sul");

        assertThat(resultado)
            .isFalse();
    }

    @Test
    @DisplayName("Domínio: Não deve sair do mapa pelo norte")
    void naoDeveSairMapaNorte() {

        boolean resultado =
            jogo.movePlayer("north");

        assertThat(resultado)
            .isFalse();

        assertThat(jogo.getPlayerX())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Domínio: Não deve sair do mapa pela esquerda")
    void naoDeveSairMapaWest() {

        boolean resultado =
            jogo.movePlayer("west");

        assertThat(resultado)
            .isFalse();

        assertThat(jogo.getPlayerY())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Domínio: Não deve atravessar parede")
    void naoDeveAtravessarParede() {

        jogo.movePlayer("east");

        boolean resultado =
            jogo.movePlayer("south");

        assertThat(resultado)
            .isFalse();
    }

    // =========================================
    // TESTES ESTRUTURAIS
    // =========================================

    @Test
    @DisplayName("Estrutural: Nao Deve aumentar pontuação ao mover")
    void deveAdicionarPontuacao() {

        jogo.movePlayer("east");

        assertThat(jogo.getPontuacao())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Estrutural: Deve avançar para próximo nível")
    void deveAvancarNivel() {

        // caminho até saída nível 1

        jogo.movePlayer("east");
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");

        assertThat(jogo.getNivel())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("Estrutural: Deve resetar posição ao avançar nível")
    void deveResetarPosicaoAoAvancarNivel() {

        jogo.movePlayer("east");
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");

        assertThat(jogo.getPlayerX())
            .isEqualTo(0);

        assertThat(jogo.getPlayerY())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Estrutural: Deve pegar chave no nível 2")
    void devePegarChave() {

        // vai para nível 2
        jogo.movePlayer("east");
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");

        // caminho até chave
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");
        jogo.movePlayer("west");
        jogo.movePlayer("west");

        assertThat(jogo.temChave())
            .isTrue();
    }

    @Test
    @DisplayName("Estrutural: Deve perder nível ao cair em alçapão sem chave")
    void deveCairAlcapaoSemChave() {

        // vai para nível 2
        jogo.movePlayer("east");
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");

        // caminho até alçapão
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");
        jogo.movePlayer("east");
        jogo.movePlayer("east");

        assertThat(jogo.getNivel())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Estrutural: Não deve perder nível com chave")
    void naoDevePerderNivelComChave() {

        // vai nível 2
        jogo.movePlayer("east");
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");

        // pega chave
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");
        jogo.movePlayer("west");
        jogo.movePlayer("west");

        // vai pro alçapão
        jogo.movePlayer("east");
        jogo.movePlayer("east");
        jogo.movePlayer("east");

        assertThat(jogo.getNivel())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("Estrutural: Nível não deve ficar abaixo de 1")
    void nivelNaoDeveFicarAbaixoDe1() throws Exception {

        var campoNivel =
            Game.class.getDeclaredField("nivel");

        campoNivel.setAccessible(true);

        // força nível 0
        campoNivel.setInt(jogo, 0);

        // recarrega mapa
        var carregar =
            Game.class.getDeclaredMethod(
                "carregarNivel"
            );

        carregar.setAccessible(true);

        carregar.invoke(jogo);

        // coloca player em um alçapão do mapa default
        // posição [2][1] = 3

        jogo.player.setPosicao(2, 1);

        // chama verificarEventos
        var verificar =
            Game.class.getDeclaredMethod(
                "verificarEventos"
            );

        verificar.setAccessible(true);

        verificar.invoke(jogo);

        assertThat(jogo.getNivel())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Estrutural: Não deve sair do mapa pelo sul")
    void naoDeveSairMapaSul() {

        // leva player até última linha

        jogo.player.setPosicao(
            jogo.getMapa().length - 1,
            0
        );

        boolean resultado =
            jogo.movePlayer("south");

        assertThat(resultado)
            .isFalse();

        assertThat(jogo.getPlayerX())
            .isEqualTo(
                jogo.getMapa().length - 1
            );
    }

    @Test
    @DisplayName("Estrutural: Não deve sair do mapa pela direita")
    void naoDeveSairMapaLeste() {

        // leva player até última coluna

        jogo.player.setPosicao(
            0,
            jogo.getMapa()[0].length - 1
        );

        boolean resultado =
            jogo.movePlayer("east");

        assertThat(resultado)
            .isFalse();

        assertThat(jogo.getPlayerY())
            .isEqualTo(
                jogo.getMapa()[0].length - 1
            );
    }


    @Test
    @DisplayName("Estrutural: Deve vencer jogo no nível 3")
    void deveVencerJogoNivel3() throws Exception {

        var campoNivel =
            Game.class.getDeclaredField("nivel");

        campoNivel.setAccessible(true);

        // força nível 3
        campoNivel.setInt(jogo, 3);

        // recarrega mapa do nível 3
        var carregar =
            Game.class.getDeclaredMethod(
                "carregarNivel"
            );

        carregar.setAccessible(true);

        carregar.invoke(jogo);

        // coloca player na saída
        // ajuste posição conforme mapa do nível 3

        jogo.player.setPosicao(5, 5);

        var verificar =
            Game.class.getDeclaredMethod(
                "verificarEventos"
            );

        verificar.setAccessible(true);

        // cobre if(valor == 2)
        verificar.invoke(jogo);

        // se não usar System.exit
        assertThat(jogo.getPontuacao())
            .isGreaterThan(0);
    }
}