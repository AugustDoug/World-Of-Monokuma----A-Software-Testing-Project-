package st.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MainTest {
    private final Main main = new Main();

    //Teste de Domínio


    @Test
    @DisplayName("Teste de domínio: Projeto deve funcionar inserindo comandos via interface")
    void GameInterfaceSucesso() {
        
        String[] args = {};
        main.main(args);
        boolean resultado = main.getIsInterface();
        assertThat(resultado).isTrue();
    }

}
