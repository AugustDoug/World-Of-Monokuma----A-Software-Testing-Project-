package st.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameTest {
    
    private final Game jogo = new Game();
    
    //Teste de Domínio
    

    @Test
    @DisplayName("Teste de domínio: Player Não deve se mover caso comando não seja aceito")
    void GamePlayerMoveInSucesso() {
        // pos [0][0] caso Go South se moverá normalmente
        boolean resultado = jogo.movePlayer("sul");
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Teste de domínio: Player deve se mover caso não haja paredes")
    void GamePlayerMoveSucesso() {
        // pos [0][0] caso Go South se moverá normalmente
        boolean resultado = jogo.movePlayer("south");
        assertThat(resultado).isTrue();
    }


    @Test
    @DisplayName("Teste de domínio: Player não deve se mover na direção de um limite")
    void GamePlayerMoveInsucessoLimite() {
        // pos [0][0] caso Go South encontrará limite em [-1][0]
        
        boolean resultado = jogo.movePlayer("north");
        assertThat(resultado).isFalse();
    }


    @Test
    @DisplayName("Teste de domínio: Player não deve se mover na direção de uma parede")
    void GamePlayerMoveInsucessoParede() {
        // pos [2][0] caso Go South encontrará uma parede em [3][0]
        jogo.x = 2;
        boolean resultado = jogo.movePlayer("south");
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Teste de domínio: Player deve se mover na direção da casa final")
    void GamePlayerMoveSucessoWin() {
        // pos [3][4] caso Go South encontrará o final em [4][4]
        // a casa final tem valor diferente de uma casa normal, como uma parede, mas o player
        // deve ser capaz de se mover ate ela
        jogo.x = 3;
        jogo.y = 4;
        jogo.movePlayer("south");
        boolean resultado = jogo.venceu();
        
        assertThat(resultado).isTrue();
    }
    
    // Teste de Fronteira
    // Partições mapa de 1 bloco | mapa 2x2 | mapa 3x3 (player no meio = simula mapa grande)

    @Test
    @DisplayName("Teste de fronteira: Player não deve conseguir se mover pois o mapa é 1x1")
    void GameMap1bloco() {

        int[][] map = {
            {0}
            
        };

        jogo.mapa = map;
        

        boolean resultado = jogo.movePlayer("north");
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Teste de fronteira: Player não deve conseguir se mover para Oeste")
    void GameMap2bloco() {
        //Player começa [0][0] logo não consegue ir para leste e norte 
        
        int[][] map = {
            {0,0},
            {0,0}
        };

        jogo.mapa = map;
    
        boolean resultado = jogo.movePlayer("west");
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Teste de fronteira: Player deve conseguir se mover para Leste")
    void GameMap3bloco() {
        //Player começa [0][0] logo não consegue ir para leste e norte 
        
        int[][] map = {
            {0,0,0},
            {0,0,0},
            {0,0,0}
        };
        jogo.x = 1;
        jogo.y = 1;
        jogo.mapa = map;
    
        boolean resultado = jogo.movePlayer("east");
        assertThat(resultado).isTrue();
    }
    
    // Teste estrutural - Caminhos diferentes
    
    @Test
    @DisplayName("Teste de fronteira: Player não deve se mover na direção de um limite acima")
    void GameMoveLimiteNorthInsucesso() {
        // pos [0][0] caso Go North encontrará um limite acima ( nwx < 0)
        
        boolean resultado = jogo.movePlayer("north");
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Teste de fronteira: Player não deve se mover na direção de um limite abaixo")
    void GameMoveLimiteSouthInsucesso() {
        // pos [0][0] caso Go South encontrará um limite abaixo ( nwx > mapa.lenght)
        jogo.x = 4;
        boolean resultado = jogo.movePlayer("south");
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Teste de fronteira: Player não deve se mover na direção de um limite a esquerda")
    void GameMoveLimiteWestInsucesso() {
        // pos [0][0] caso Go North encontrará um limite a esquerda ( nwy < 0)
        
        boolean resultado = jogo.movePlayer("west");
        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Teste de fronteira: Player não deve se mover na direção de um limite a direita")
    void GameMoveLimiteEastInsucesso() {
        // pos [0][4] caso Go East encontrará um limite a esquerda ( nwy > mapa[0].lenght)
        jogo.y = 4;
        boolean resultado = jogo.movePlayer("east");
        assertThat(resultado).isFalse();
    }
}
