package st.project.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.jqwik.api.Property;
import st.project.model.Game;
import st.project.model.User;

public class GameControllerTest {

    private GameController controller;

    @BeforeEach
    void setup() {

        User user =
            new User(
                "login",
                "senha",
                "avatar",
                false
            );

        Game game =
            new Game(user);

        controller =
            new GameController(game);
    }

    // =====================================
    // TESTES DE DOMÍNIO
    // =====================================

    @Test
    @DisplayName("Teste de Domínio: Deve mover jogador")
    void deveMoverJogador() {

        String resultado =
            controller.processarComando(
                "go east"
            );

        assertThat(resultado)
            .isEqualTo(
                "Movendo para east"
            );
    }

    @Test
    @DisplayName("Teste de Domínio: Deve reconhecer ranking")
    void deveReconhecerRanking() {

        String resultado =
            controller.processarComando(
                "ranking"
            );

        assertThat(resultado)
            .isEqualTo("RANKING");
    }

    // TESTES ESTRUTURAIS
    // =====================================
    // MC/DC
    // =====================================

    @Test
    @DisplayName("Teste Estrutural: input null")
    void inputNull() {

        String resultado =
            controller.processarComando(
                null
            );

        assertThat(resultado)
            .isEqualTo(
                "Comando vazio!"
            );
    }

    @Test
    @DisplayName("Teste Estrutural: input vazio")
    void inputVazio() {

        String resultado =
            controller.processarComando(
                "   "
            );

        assertThat(resultado)
            .isEqualTo(
                "Comando vazio!"
            );
    }

    @Test
    @DisplayName("Teste Estrutural: ranking inválido")
    void rankingInvalido() {

        String resultado =
            controller.processarComando(
                "rank"
            );

        assertThat(resultado)
            .isEqualTo(
                "Comando inválido!"
            );
    }

    @Test
    @DisplayName("Teste Estrutural: comando sem GO")
    void comandoSemGo() {

        String resultado =
            controller.processarComando(
                "andar east"
            );

        assertThat(resultado)
            .isEqualTo(
                "Use GO"
            );
    }

    @Test
    @DisplayName("Teste Estrutural: direção inválida")
    void direcaoInvalida() {

        String resultado =
            controller.processarComando(
                "go cima"
            );

        assertThat(resultado)
            .isEqualTo(
                "Não pode mover!"
            );
    }

    @Test
    @DisplayName("Teste Estrutural: comando com muitas palavras")
    void comandoMuitasPalavras() {

        String resultado =
            controller.processarComando(
                "go east agora"
            );

        assertThat(resultado)
            .isEqualTo(
                "Comando inválido!"
            );
    }

    // =====================================
    // PROPRIEDADE
    // =====================================

    @Property
    @DisplayName("Propriedade: Toda direção válida deve gerar resposta válida")
    void direcoesValidasDevemGerarResposta() {

        // PRÉ CONDIÇÕES 
        // RECEBE UMA SEQUENCIA DE DIREÇÕES VÁLIDAS SEMANTICAMENTE
        String[] direcoes = {
            "north",
            "south",
            "east",
            "west"
        };

        // PÓS CONDIÇÕES DEVE GERAR RESPOSTA VÁLIDA PARA CADA DIREÇÃO
        // SE É POSSIVEL MOVER OU NÃO CASO HAJA OBSTACULO
        for(String direcao : direcoes) {

            Game jogo =
                new Game(
                    new User(
                        "login",
                        "senha",
                        "avatar",
                        false
                    )
                );

            GameController controller =
                new GameController(jogo);

            String resposta =
                controller.processarComando(
                    "go " + direcao
                );

            assertThat(resposta)
                .isIn(
                    "Movendo para " + direcao,
                    "Não pode mover!"
                );
        }
    }
}