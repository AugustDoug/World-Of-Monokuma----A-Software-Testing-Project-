package st.project.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import st.project.Main;
import st.project.controller.GameController;
import st.project.model.Game;
import st.project.model.JsonUserRepository;
import st.project.model.Ranking;
import st.project.model.User;
import st.project.model.UserManager;
import st.project.model.UserRepository;

public class IntegrationTest {

    @Test
    void deveLogarUsuarioExistente() {

        UserRepository repo =
            new UserRepository() {

                private List<User> users =
                    new ArrayList<>(
                        List.of(
                            new User(
                                "admin",
                                "123",
                                "avatar",
                                false
                            )
                        )
                    );

                @Override
                public List<User> carregar() {
                    return users;
                }

                @Override
                public void salvar(List<User> usuarios) {
                    users = usuarios;
                }
            };

        UserManager manager =
            new UserManager(repo);

        Main.Entrada entrada =
            new Main.Entrada() {

                public String lerLogin() {
                    return "admin";
                }

                public String lerSenha() {
                    return "123";
                }

                public int confirmarCadastro() {
                    return 1;
                }
            };

        final boolean[] abriu = {false};

        Main.TelaJogo tela =
            (u,m) -> abriu[0] = true;

        Main main =
            new Main(
                entrada,
                tela,
                manager
            );

        assertThat(
            main.iniciar()
        ).isTrue();

        assertThat(abriu[0])
            .isTrue();
    }

    @Test
    void deveOrdenarPorPontuacao() {

        User a =
            new User("a","1","x",false);

        User b =
            new User("b","1","x",false);

        a.adicionarPontuacao(10);
        b.adicionarPontuacao(100);

        List<User> ranking =
            Ranking.ordenar(
                List.of(a,b)
            );

        assertThat(
            ranking.get(0)
        ).isEqualTo(b);
    }

    @Test
    void deveMoverJogador() {

        User user =
            new User(
                "login",
                "senha",
                "avatar",
                false
            );

        Game game =
            new Game(user);

        GameController controller =
            new GameController(game);

        String resposta =
            controller.processarComando(
                "go east"
            );

        assertThat(resposta)
            .contains("Movendo");

        assertThat(
            game.getPlayerY()
        ).isEqualTo(1);
    }
}