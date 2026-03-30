package st.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameGUITest {

    private final GameGUI  jogoGUI = new GameGUI();

    //Test de Domínio

    @Test
    @DisplayName("Teste de Domínio: Listener deve reconhecer comandos via interface")
    void deveDispararActionListener() {
        

        jogoGUI.getInputField().setText("go south");
        jogoGUI.getInputField().postActionEvent(); // 🔥 simula ENTER

        String resultado = jogoGUI.processarComando("go north");
        assertThat(resultado).isEqualTo("north");
        
    }

    @Test
    @DisplayName("Teste de Domínio: Deve permitir direção Norte")
    void GameGUIsucessoNorth() {
        
        String resultado = jogoGUI.processarComando("go north");
        assertThat(resultado).isEqualTo("north");
    }


    @Test
    @DisplayName("Teste de Domínio: Deve permitir direção Sul")
    void GameGUIsucessoSouth() {
        
        String resultado = jogoGUI.processarComando("go south");
        assertThat(resultado).isEqualTo("south");
    }

    @Test
    @DisplayName("Teste de Domínio: Deve permitir direção Oeste")
    void GameGUIsucessoWest() {
        
        String resultado = jogoGUI.processarComando("go west");
        assertThat(resultado).isEqualTo("west");
    }

    @Test
    @DisplayName("Teste de Domínio: Deve permitir direção Leste")
    void GameGUIsucessoEast() {
        
        String resultado = jogoGUI.processarComando("go east");
        assertThat(resultado).isEqualTo("east");
    }

    @Test
    @DisplayName("Teste de Domínio: Não Deve permitir direção null")
    void GameGUIsucessoNull() {
        
        String resultado = jogoGUI.processarComando(null);
        assertThat(resultado).isNull();
    }

    @Test
    @DisplayName("Teste de Domínio: Interface Não Deve executar movimento caso player não se mova")
    void GameGUIMovePlayerInsucesso() {
        // player[0][0] não deve se mover para north pois há limites

        boolean resultado = jogoGUI.executarMovimento("north");
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Teste de Domínio: Interface Deve executar movimento caso player se mova")
    void GameGUIMovePlayersucesso() {
        // player[0][0] deve se mover para south não pois há limites nem paredes

        boolean resultado = jogoGUI.executarMovimento("south");
        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Teste de Domínio: Interface deve reconhecer fim de jogo ao player chegar ao objetivo final")
    void GameGUIMovePlayerWinsucesso() {
        Game jogo = jogoGUI.game;
        jogo.x = 3;
        jogo.y = 4;
        jogo.movePlayer("south");
        jogo.venceu();
        boolean resultado = jogoGUI.executarMovimento("south");
        assertThat(resultado).isTrue();
    }

    //Teste de fronteira
    // Partições: 
    //   | vazio | 1 palavra | sem comando go | sem direção válida | 2 palavras | 2 ou mais palavras |

    @Test
    @DisplayName("Teste de fronteira: Não deve reconhecer comando vazio/null")
    void GameGUIinsucessoNulo() {
        
        String resultado = jogoGUI.processarComando("");
        assertThat(resultado).isNull();
    }

    @Test
    @DisplayName("Teste de fronteira: Não deve reconhecer sentença com 1 palavra(o comando go)")
    void GameGUIinsucessUmaPalavra() {
        
        String resultado = jogoGUI.processarComando("go");
        assertThat(resultado).isNull();
    }

    @Test
    @DisplayName("Teste de fronteira: Não deve reconhecer sentenças sem direção válida")
    void GameGUIinsucessoDirecao() {
        
        String resultado = jogoGUI.processarComando("go sul");
        assertThat(resultado).isNull();
    }

    @Test
    @DisplayName("Teste de fronteira: Não deve reconhecer palavra que não é go + direção (duas palavras)")
    void GameGUIinsucessoSemGo() {
        
        String resultado = jogoGUI.processarComando("vá south");
        assertThat(resultado).isNull();
    }

    @Test
    @DisplayName("Teste de fronteira: Não deve reconhecer sentenças com mais de 2 palavras")
    void GameGUIinsucessoOverflow() {
        
        String resultado = jogoGUI.processarComando("go north go south");
        assertThat(resultado).isNull();
    }
}
