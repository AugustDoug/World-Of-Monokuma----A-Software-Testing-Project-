package st.project.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.jqwik.api.Property;
import st.project.model.User;

public class UserTest {

    // TESTE DE DOMÍNIO

    @Test
    @DisplayName("Teste de Domínio: Deve criar usuário corretamente")
    void deveCriarUsuarioCorretamente() {

        User user =
            new User(
                "douglas",
                "123",
                "avatar.png",
                false
            );

        assertThat(user.getLogin())
            .isEqualTo("douglas");

        assertThat(user.getSenha())
            .isEqualTo("123");

        assertThat(user.getAvatar())
            .isEqualTo("avatar.png");

        assertThat(user.isAdmin())
            .isFalse();
    }

    @Test
    @DisplayName("Teste de Domínio: Pontuação inicial deve ser zero")
    void pontuacaoInicialDeveSerZero() {

        User user =
            new User(
                "douglas",
                "123",
                "avatar",
                false
            );

        assertThat(user.getPontuacao())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Teste de Domínio: Sessões iniciais devem ser zero")
    void sessoesIniciaisDevemSerZero() {

        User user =
            new User(
                "douglas",
                "123",
                "avatar",
                false
            );

        assertThat(user.getSessoes())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Teste de Domínio: Deve adicionar pontuação")
    void deveAdicionarPontuacao() {

        User user =
            new User(
                "douglas",
                "123",
                "avatar",
                false
            );

        user.adicionarPontuacao(100);

        assertThat(user.getPontuacao())
            .isEqualTo(100);
    }

    @Test
    @DisplayName("Teste de Domínio: Deve adicionar sessão")
    void deveAdicionarSessao() {

        User user =
            new User(
                "douglas",
                "123",
                "avatar",
                false
            );

        user.adicionarSessao();

        assertThat(user.getSessoes())
            .isEqualTo(1);
    }

    // TESTES DE FRONTEIRA

    @Test
    @DisplayName("Teste de Fronteira: Deve permitir pontuação zero")
    void devePermitirPontuacaoZero() {

        User user =
            new User(
                "douglas",
                "123",
                "avatar",
                false
            );

        user.adicionarPontuacao(0);

        assertThat(user.getPontuacao())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Teste de Fronteira: Deve permitir pontuação negativa")
    void devePermitirPontuacaoNegativa() {

        User user =
            new User(
                "douglas",
                "123",
                "avatar",
                false
            );

        user.adicionarPontuacao(-50);

        assertThat(user.getPontuacao())
            .isEqualTo(-50);
    }

    // TESTES ESTRUTURAIS

    @Test
    @DisplayName("Teste Estrutural: Deve criar administrador")
    void deveCriarAdministrador() {

        User user =
            new User(
                "admin",
                "admin",
                "avatar",
                true
            );

        assertThat(user.isAdmin())
            .isTrue();
    }

    @Test
    @DisplayName("Teste Estrutural: Deve criar usuário comum")
    void deveCriarUsuarioComum() {

        User user =
            new User(
                "user",
                "123",
                "avatar",
                false
            );

        assertThat(user.isAdmin())
            .isFalse();
    }

    // TESTE DE PROPRIEDADE


    @Property
    @DisplayName("Teste de Propriedade: Soma das pontuações deve ser acumulativa")
    void propriedadePontuacaoAcumulativa() {

        // pré condições: um usuário frequentemente pontuando 

        User user =
            new User(
                "douglas",
                "123",
                "avatar",
                false
            );

        int soma = 0;
        
        // pós condições: ranking do usuário deve ser atualizado a cada pontuação
        for(int i = 1; i <= 100; i++) {

            user.adicionarPontuacao(i);

            soma += i;
        }

        // e no fim a soma deve estar correta
        assertThat(user.getPontuacao())
            .isEqualTo(soma);
    }

    @Property
    @DisplayName("Teste de Propriedade: Sessões devem incrementar corretamente")
    void propriedadeIncrementoSessao() {

        // pré condições: um usuário jogando várias sessões

        User user =
            new User(
                "douglas",
                "123",
                "avatar",
                false
            );
        
        // pós condições: número de sessões deve ser incrementado a cada nova sessão
        for(int i = 0; i < 50; i++) {
            user.adicionarSessao();
        }

        assertThat(user.getSessoes())
            .isEqualTo(50);
    }

    
}