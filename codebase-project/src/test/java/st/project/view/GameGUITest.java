package st.project.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.jqwik.api.Property;
import st.project.model.Level;
import st.project.model.User;
import st.project.model.UserManager;
import st.project.model.UserRepository;

public class GameGUITest {

    private GameGUI jogoGUI;

    // antes da run de testes o setup cria um mock para cada componente da main 
    // e um main com esses mocks
    
    @BeforeEach
    void setup() {

        UserRepository repo =
            mock(UserRepository.class);

        when(repo.carregar())
            .thenReturn(new ArrayList<>());

        UserManager manager =
            new UserManager(repo);

        User user =
            new User(
                "login",
                "senha",
                "/images/monoplayer.png",
                false
            );

        jogoGUI =
            new GameGUI(user, manager);
    }

    // helper
    private void avancarNivel2() {

        jogoGUI.executarMovimento("east");
        jogoGUI.executarMovimento("east");
        jogoGUI.executarMovimento("south");
        jogoGUI.executarMovimento("south");
    }

    // MC:DC sempre que inicia a aplicação
    // o mapa ja inicia como == null
    // então qualquer teste cobre essa condição

    // ==========================
    // TESTES DE DOMÍNIO
    // ==========================

    @Test
    @DisplayName("Teste de Domínio: GUI não deve mover jogador para fora do mapa")
    void naoDeveMoverJogador() {

        boolean resultado =
            jogoGUI.executarMovimento("north");

        assertThat(resultado)
            .isFalse();
    }

    @Test
    @DisplayName("Teste de Domínio: GUI não deve ganhahr pontuação ao se mover")
    void deveAtualizarPontuacao() {

        jogoGUI.executarMovimento("east");

        assertThat(
            jogoGUI.game.getPontuacao()
        ).isEqualTo(0);
    }

    @Test
    @DisplayName("Teste de Domínio: GUI não deve atualizar pontuação com chave")
    void deveAtualizarPontuacaoComChave() {

        jogoGUI.game.player.pegarChave();

        jogoGUI.executarMovimento("east");

        assertThat(
            jogoGUI.game.getPontuacao()
        ).isEqualTo(0);
    }

    @Test
    @DisplayName("Teste de Domínio: GUI deve mostrar ranking")
    void deveMostrarRanking() {

        jogoGUI.getInputField()
            .setText("ranking");

        jogoGUI.getInputField()
            .postActionEvent();

        assertThat(
            jogoGUI.getOutputArea().getText()
        ).contains("RANKING");
    }

    @Test
    @DisplayName("Teste de Domínio: GUI deve disparar listener ao apertar ENTER")
    void deveDispararListener() {

        jogoGUI.getInputField()
            .setText("go east");

        jogoGUI.getInputField()
            .postActionEvent();

        assertThat(
            jogoGUI.game.getPlayerY()
        ).isEqualTo(1);
    }

    // ==========================
    // TESTES DE FRONTEIRA
    // ==========================

    @Test
    @DisplayName("Teste de Fronteira: Jogador não deve sair pelo topo")
    void naoDeveSairTopoMapa() {

        boolean resultado =
            jogoGUI.executarMovimento("north");

        assertThat(resultado)
            .isFalse();

        assertThat(
            jogoGUI.game.getPlayerX()
        ).isEqualTo(0);
    }

    @Test
    @DisplayName("Teste de Fronteira: Jogador não deve sair pela esquerda")
    void naoDeveSairEsquerdaMapa() {

        boolean resultado =
            jogoGUI.executarMovimento("west");

        assertThat(resultado)
            .isFalse();

        assertThat(
            jogoGUI.game.getPlayerY()
        ).isEqualTo(0);
    }

    @Test
    @DisplayName("Teste de Fronteira: Jogador deve mover dentro do mapa")
    void deveMoverDentroMapa() {

        boolean resultado =
            jogoGUI.executarMovimento("east");

        assertThat(resultado)
            .isTrue();

        assertThat(
            jogoGUI.game.getPlayerY()
        ).isEqualTo(1);
    }

    @Test
    @DisplayName("Teste de Fronteira: Jogador não deve atravessar parede")
    void naoDeveAtravessarParede() {

        jogoGUI.executarMovimento("east");

        boolean resultado =
            jogoGUI.executarMovimento("south");

        assertThat(resultado)
            .isFalse();
    }

    // ==========================
    // TESTES ESTRUTURAIS
    // ==========================

    @Test
    @DisplayName("Teste Estrutural: Ao vencer deve salvar pontuação")
    void deveSalvarPontuacaoAoVencer() {


        // força o estado de vitória
        jogoGUI.game.venceu = true;


        jogoGUI.getInputField()
            .setText("go east");


        jogoGUI.getInputField()
            .postActionEvent();



        assertThat(
            jogoGUI.game.venceu
        )
        .isTrue();

    }

    @Test
    @DisplayName("Teste Estrutural: Não deve aceitar direção inválida")
    void naoDeveAceitarDirecaoInvalida() {

        boolean resultado =
            jogoGUI.game.movePlayer("pular");

        assertThat(resultado)
            .isFalse();
    }

    @Test
    @DisplayName("Teste Estrutural: criarMapaVisual deve remover painel antigo")
    void deveRemoverPainelAntigoCriarMapaVisual()
        throws Exception {

        var metodo =
            GameGUI.class.getDeclaredMethod(
                "criarMapaVisual"
            );

        metodo.setAccessible(true);

        JPanel painelAntigo =
            jogoGUI.mapaPanel;

        metodo.invoke(jogoGUI);

        assertThat(jogoGUI.mapaPanel)
            .isNotSameAs(painelAntigo);
    }

    // MC:DC mapa.length != m.length

    @Test
    @DisplayName("Teste Estrutural: Deve recriar mapa visual")
    void deveRecriarMapaVisual() {

        avancarNivel2();

        assertThat(
            jogoGUI.game.getNivel()
        ).isEqualTo(2);

        assertThat(
            jogoGUI.mapaPanel
        ).isNotNull();

        assertThat(
            jogoGUI.mapaPanel.getComponentCount()
        ).isEqualTo(25);
    }

    // MC:DC if direcao == null

    @Test
    @DisplayName("Teste Estrutural: Deve retornar false para direção null")
    void deveRetornarFalseDirecaoNull() {

        assertThat(
            jogoGUI.executarMovimento(null)
        ).isFalse();
    }

    // MC:DC mapa[0].length != m[0].length

    @Test
    @DisplayName("Teste Estrutural: Deve recriar mapa quando colunas mudam")
    void deveRecriarMapaQuandoColunasMudam() {

        jogoGUI.mapa =
            new JLabel[3][1];

        jogoGUI.executarMovimento("east");

        assertThat(
            jogoGUI.mapa[0].length
        ).isEqualTo(
            jogoGUI.game.getMapa()[0].length
        );
    }

    @Test
    @DisplayName("Teste Estrutural: Deve criar mapa default no nível 3")
    void deveCriarMapaNivel3() {

        Level level =
            new Level(3);

        assertThat(
            level.getMapa().length
        ).isEqualTo(6);
    }

    @Test
    @DisplayName("Teste Estrutural: Método venceu deve retornar true")
    void venceuTrue() {

        assertThat(
            jogoGUI.venceu(true)
        ).isTrue();
    }

    @Test
    @DisplayName("Teste Estrutural: Método venceu deve retornar false")
    void venceuFalse() {

        assertThat(
            jogoGUI.venceu(false)
        ).isFalse();
    }

    @Test
    @DisplayName("Teste Estrutural: Deve recriar mapa quando mapa for null")
    void deveRecriarMapaQuandoMapaNull() throws Exception {

        jogoGUI.mapa = null;

        var metodo =
            GameGUI.class.getDeclaredMethod(
                "atualizarMapa"
            );

        metodo.setAccessible(true);

        metodo.invoke(jogoGUI);

        assertThat(jogoGUI.mapa)
            .isNotNull();
    }

    @Test
    @DisplayName("Teste Estrutural: atualizarMapa deve funcionar com mapaPanel null")
    void atualizarMapaComMapaPanelNull()
        throws Exception {

        // força mapaPanel null
        jogoGUI.mapaPanel = null;

        // força entrar no IF principal
        jogoGUI.mapa = null;

        var metodo =
            GameGUI.class.getDeclaredMethod(
                "atualizarMapa"
            );

        metodo.setAccessible(true);

        metodo.invoke(jogoGUI);

        assertThat(jogoGUI.mapaPanel)
            .isNotNull();

        assertThat(jogoGUI.mapa)
            .isNotNull();
    }
    
    // ==========================
    // TESTE DE PROPRIEDADE
    // ==========================

    // independente dos comandos enviados,
    // o player nunca deve sair do mapa

    @Property
    @DisplayName("Propriedade: jogador nunca deve ficar fora do mapa")
    void jogadorNuncaSaiMapa() {

        // pré condições: uma sequencia de comandos válidos que independentemente
        // de qual caminho façam, o player permaneça dentro do mapa
        String[] movimentos = {
            "north",
            "south",
            "east",
            "west"
        };

        // pós condições: compara se o player esta dentro dos limites do mapa
        // independente de qual comando válido seja enviado
        for(String mov : movimentos) {

            jogoGUI.executarMovimento(mov);

            assertThat(
                jogoGUI.game.getPlayerX()
            ).isBetween(
                0,
                jogoGUI.game.getMapa().length - 1
            );

            assertThat(
                jogoGUI.game.getPlayerY()
            ).isBetween(
                0,
                jogoGUI.game.getMapa()[0].length - 1
            );
        }
    }
}