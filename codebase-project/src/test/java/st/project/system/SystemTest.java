package st.project.system;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import st.project.model.Game;
import st.project.model.JsonUserRepository;
import st.project.model.User;
import st.project.model.UserManager;

public class SystemTest {
    
    @AfterEach
    void limparArquivos() {

        new File("cadastro.json").delete();
        new File("login.json").delete();
    }

    @Test
    void deveCadastrarUsuario() {

        JsonUserRepository repo =
            new JsonUserRepository(
                "cadastro.json"
            );

        UserManager manager =
            new UserManager(repo);

        manager.cadastrar(
            "novo",
            "123",
            "avatar"
        );

        assertThat(
            manager.login(
                "novo",
                "123"
            )
        ).isNotNull();
    }

    

    @Test
    void deveLogarComSucesso() {

        JsonUserRepository repo =
            new JsonUserRepository(
                "login.json"
            );

        UserManager manager =
            new UserManager(repo);

        manager.cadastrar(
            "admin",
            "admin",
            "avatar"
        );

        User user =
            manager.login(
                "admin",
                "admin"
            );

        assertThat(user)
            .isNotNull();
    }
    
    @Test
    void devePassarParaNivel2() {

        Game jogo =
            new Game(
                new User(
                    "a",
                    "b",
                    "c",
                    false
                )
            );

        jogo.movePlayer("east");
        jogo.movePlayer("east");
        jogo.movePlayer("south");
        jogo.movePlayer("south");

        assertThat(
            jogo.getNivel()
        ).isEqualTo(2);
    }

    @Test
    void devePegarChave() {

        Game game =
            new Game(
                new User(
                    "a",
                    "1",
                    "",
                    false
                )
            );

        game.movePlayer("east");
        game.movePlayer("east");
        game.movePlayer("south");
        game.movePlayer("south");

        game.movePlayer("east");
        game.movePlayer("south");
        game.movePlayer("south");
        game.movePlayer("west");

        assertThat(
            game.temChave()
        ).isTrue();
    }

    @Test
    void deveConcluirJogoNoNivel3() {

        Game game =
            new Game(
                new User(
                    "a",
                    "1",
                    "",
                    false
                )
            );

        // nível 1
        game.movePlayer("east");
        game.movePlayer("east");
        game.movePlayer("south");
        game.movePlayer("south");

        // nível 2
        game.movePlayer("east");
        game.movePlayer("south");
        game.movePlayer("south");
        game.movePlayer("west");
        game.movePlayer("east");

        game.movePlayer("east");
        game.movePlayer("south");
        game.movePlayer("south");
        game.movePlayer("east");
        game.movePlayer("east");


        // nível 3
        game.movePlayer("east");
        game.movePlayer("south");
        game.movePlayer("south");
        game.movePlayer("west");
        game.movePlayer("south");
        game.movePlayer("south");
        game.movePlayer("east");
        game.movePlayer("south");
        game.movePlayer("east");
        game.movePlayer("east");
        game.movePlayer("east");
        game.movePlayer("east");


        assertThat(game.venceu)
            .isTrue();



        assertThat(game.getNivel())
            .isEqualTo(3);
    }
}
