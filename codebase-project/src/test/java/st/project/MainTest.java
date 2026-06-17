package st.project;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import st.project.Main.Entrada;
import st.project.Main.TelaJogo;

import st.project.model.User;
import st.project.model.UserManager;
import st.project.model.UserRepository;

public class MainTest {

    private Entrada entrada;

    private TelaJogo tela;

    private UserRepository repo;

    private UserManager manager;

    private Main main;

    // antes da run de testes o setup cria um mock para cada componente da main 
    // e um main com esses mocks
    @BeforeEach
    void setup() {

        entrada =
            mock(Entrada.class);

        tela =
            mock(TelaJogo.class);

        repo =
            mock(UserRepository.class);

        when(repo.carregar())
            .thenReturn(new ArrayList<>());

        manager =
            new UserManager(repo);

        main =
            new Main(
                entrada,
                tela,
                manager
            );
    }

    // Na main do jogo há duas condicionais principais
    // A - user existe ou não (no caso se user é diferente de null )
    // B - usuário aceita ou não se cadastrar

    // No total há 3 caminhos possíveis para o fluxo de login/cadastro, sendo eles:
    // 1- A TRUE   
    // 2- A FALSE  B TRUE
    // 3- A FALSE  B FALSE

    // Teste de Domínio
    // 1 - Caminho TRUE para A
    // (usuario passa login e senha e retorna um user válido)

    @Test
    @DisplayName("Teste de domínio: Deve logar com sucesso")
    void deveLogarComSucesso() {

        User admin =
            new User(
                "admin",
                "admin",
                "/images/admin.png",
                true
            );

        when(repo.carregar())
            .thenReturn(
                new ArrayList<>(
                    List.of(admin)
                )
            );

        manager =
            new UserManager(repo);

        main =
            new Main(
                entrada,
                tela,
                manager
            );

        when(entrada.lerLogin())
            .thenReturn("admin");

        when(entrada.lerSenha())
            .thenReturn("admin");

        boolean resultado =
            main.iniciar();

        assertThat(resultado)
            .isTrue();

        
    }

    // 2 - Caminho FALSE para A
    // (usuario passa login e senha e retorna null)

    // Caminho TRUE para B
    // (usuario escolhe cadastrar)

    @Test
    @DisplayName("Teste de domínio: Deve cadastrar e logar com sucesso")
    void deveCadastrarNovoUsuario() {

        when(entrada.lerLogin())
            .thenReturn(
                "douglas",
                "douglas"
            );

        when(entrada.lerSenha())
            .thenReturn(
                "123",
                "123"
            );

        // aceita cadastrar
        // (caminho TRUE para B)

        when(entrada.confirmarCadastro())
            .thenReturn(0);

        boolean resultado =
            main.iniciar();

        assertThat(resultado)
            .isTrue();

        
    }

    // 3 - Caminho FALSE para A
    // (usuario passa login e senha e retorna null)

    // Caminho FALSE para B
    // (usuario escolhe NÃO cadastrar)

    @Test
    @DisplayName("Teste de domínio: Não deve logar com sucesso")
    void naoDeveLogarComSucesso() {

        when(entrada.lerLogin())
            .thenReturn("admin123");

        when(entrada.lerSenha())
            .thenReturn("admin123");

        // usuário escolhe NÃO cadastrar

        when(entrada.confirmarCadastro())
            .thenReturn(1);

        boolean resultado =
            main.iniciar();

        assertThat(resultado)
            .isFalse();

       
    }

    // Teste de Fronteira
    // basicamente as fronteiras da main são:
    // login/senha null |||| login/senha vazios |||| login/senha normal

    @Test
    @DisplayName("Teste de fronteira: Não deve aceitar Login vazio")
    void loginVazio() {

        when(entrada.lerLogin())
            .thenReturn("");

        when(entrada.lerSenha())
            .thenReturn("");

        when(entrada.confirmarCadastro())
            .thenReturn(1);

        boolean resultado =
            main.iniciar();

        assertThat(resultado)
            .isFalse();
    }

    @Test
    @DisplayName("Teste de fronteira: Não deve aceitar Login null")
    void loginNull() {

        when(entrada.lerLogin())
            .thenReturn(null);

        when(entrada.lerSenha())
            .thenReturn(null);

        when(entrada.confirmarCadastro())
            .thenReturn(1);

        boolean resultado =
            main.iniciar();

        assertThat(resultado)
            .isFalse();
    }

    // Teste Estrutural
    // Verifica se o método salvar foi chamado

    @Test
    @DisplayName("Teste estrutural: Deve salvar usuário ao cadastrar")
    void deveSalvarUsuarioAoCadastrar() {

        when(entrada.lerLogin())
            .thenReturn(
                "novo",
                "novo"
            );

        when(entrada.lerSenha())
            .thenReturn(
                "123",
                "123"
            );

        when(entrada.confirmarCadastro())
            .thenReturn(0);

        main.iniciar();

        verify(repo, atLeastOnce())
            .salvar(any());
    }

    // cobertura interface gráfica

    @Test
    @DisplayName("Teste estrutural: Entrada não deve ser nula")
    void deveCriarEntradaGUI() {

        Main.EntradaGUI entrada =
            new Main.EntradaGUI();

        assertThat(entrada)
            .isNotNull();
    }

    @Test
    @DisplayName("Teste estrutural: EntradaGUI deve ler login")
    void deveLerLogin() {

        try(
            MockedStatic<JOptionPane> mock =
                mockStatic(JOptionPane.class)
        ) {

            mock.when(() ->
                JOptionPane.showInputDialog("Login:")
            ).thenReturn("douglas");

            Main.EntradaGUI entrada =
                new Main.EntradaGUI();

            assertThat(
                entrada.lerLogin()
            ).isEqualTo("douglas");
        }
    }

    @Test
    @DisplayName("Teste estrutural: EntradaGUI deve ler senha")
    void deveLerSenha() {

        try(
            MockedStatic<JOptionPane> mock =
                mockStatic(JOptionPane.class)
        ) {

            mock.when(() ->
                JOptionPane.showInputDialog("Senha:")
            ).thenReturn("123");

            Main.EntradaGUI entrada =
                new Main.EntradaGUI();

            assertThat(
                entrada.lerSenha()
            ).isEqualTo("123");
        }
    }

    @Test
    @DisplayName("Teste estrutural: EntradaGUI deve confirmar cadastro")
    void deveConfirmarCadastro() {

        try(
            MockedStatic<JOptionPane> mock =
                mockStatic(JOptionPane.class)
        ) {

            mock.when(() ->
                JOptionPane.showConfirmDialog(
                    null,
                    "Usuário não existe. Deseja cadastrar?"
                )
            ).thenReturn(0);

            Main.EntradaGUI entrada =
                new Main.EntradaGUI();

            assertThat(
                entrada.confirmarCadastro()
            ).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("Teste estrutural: Main deve executar")
    void deveExecutarMain() {

        try(
            MockedStatic<Main> mock =
                mockStatic(Main.class, CALLS_REAL_METHODS)
        ) {

            Main app =
                mock(Main.class);

            when(app.iniciar())
                .thenReturn(true);

            mock.when(Main::criarAplicacao)
                .thenReturn(app);

            Main.main(new String[]{});

            verify(app)
                .iniciar();
        }
    }

    @Test
    @DisplayName("Teste estrutural: TelaJogoGUI deve abrir GameGUI")
    void telaJogoGuiDeveAbrirGameGUI() {

        User user =
            new User(
                "login",
                "senha",
                "avatar",
                false
            );

        when(repo.carregar())
            .thenReturn(new ArrayList<>());

        UserManager manager =
            new UserManager(repo);

        Main.TelaJogoGUI tela =
            new Main.TelaJogoGUI();

        tela.abrir(user, manager);

        assertThat(tela)
            .isNotNull();
    }
}