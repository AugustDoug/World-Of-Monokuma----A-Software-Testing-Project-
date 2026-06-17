package st.project.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.jqwik.api.Property;

public class UserManagerTest {

    private final String ARQUIVO_TESTE =
        "usuarios_test.json";

    private UserManager manager;

    @BeforeEach
    void setup() {

        // remove arquivo antigo antes do teste
        File file =
            new File(ARQUIVO_TESTE);

        if(file.exists()) {
            file.delete();
        }

        manager =
            spy(new UserManager(
                new JsonUserRepository(
                    ARQUIVO_TESTE
                )
            ));
    }

    @AfterEach
    void cleanup() {

        File file =
            new File(ARQUIVO_TESTE);

        if(file.exists()) {
            file.delete();
        }
    }

    // TESTES DE DOMÍNIO

    @Test
    @DisplayName("Teste de Domínio: Deve criar lista quando arquivo não existir")
    void deveCriarListaVaziaQuandoArquivoNaoExiste() {

        assertThat(manager.getUsuarios())
            .isNotNull();

        // admin criado automaticamente
        assertThat(manager.getUsuarios())
            .isNotEmpty();
    }

    @Test
    @DisplayName("Teste de Domínio: Não deve cadastrar usuário duplicado")
    void naoDeveCadastrarUsuarioDuplicado() {

        boolean resultado =
            manager.cadastrar(
                "admin",
                "123",
                "avatar"
            );

        assertThat(resultado)
            .isFalse();

        // MC:DC
        // buscar(login) != null = TRUE
        verify(manager, never())
            .salvarPontuacoes();
    }

    @Test
    @DisplayName("Teste de Domínio: Deve cadastrar novo usuário")
    void deveCadastrarNovoUsuario() {

        boolean resultado =
            manager.cadastrar(
                "douglas",
                "123",
                "avatar"
            );

        assertThat(resultado)
            .isTrue();

        assertThat(
            manager.buscar("douglas")
        ).isNotNull();
    }

    @Test
    @DisplayName("Teste de Domínio: Deve logar com login e senha corretos")
    void deveLogarComLoginESenhaCorretos() {

        User user =
            manager.login(
                "admin",
                "admin"
            );

        assertThat(user)
            .isNotNull();
    }

    @Test
    @DisplayName("Teste de Domínio: Não deve logar com login errado")
    void naoDeveLogarComLoginErrado() {

        User user =
            manager.login(
                "errado",
                "admin"
            );

        assertThat(user)
            .isNull();
    }

    @Test
    @DisplayName("Teste de Domínio: Não deve logar com senha errada")
    void naoDeveLogarComSenhaErrada() {

        User user =
            manager.login(
                "admin",
                "123"
            );

        assertThat(user)
            .isNull();
    }

    @Test
    @DisplayName("Teste de Domínio: Deve remover usuário")
    void deveRemoverUsuario() {

        manager.cadastrar(
            "douglas",
            "123",
            "avatar"
        );

        boolean resultado =
            manager.remover("douglas");

        assertThat(resultado)
            .isTrue();

        assertThat(
            manager.buscar("douglas")
        ).isNull();
    }

    @Test
    @DisplayName("Teste de Domínio: Não deve remover usuário inexistente")
    void naoDeveRemoverUsuarioInexistente() {

        boolean resultado =
            manager.remover("inexistente");

        assertThat(resultado)
            .isFalse();
    }

    // TESTES ESTRUTURAIS
    
    @Test
    @DisplayName("Teste Estrutural: Deve salvar pontuações")
    void deveSalvarPontuacoesSemErro() {

        manager.salvarPontuacoes();

        verify(manager)
            .salvarPontuacoes();
    }

    
    // MC:DC caminho onde o banco json esta vazio/null
    @Test
    @DisplayName("Teste Estrutural: Deve criar lista quando JSON retornar null")
    void deveCriarListaQuandoJsonRetornaNull() throws Exception {

        FileWriter writer =
            new FileWriter(ARQUIVO_TESTE);

        writer.write("null");

        writer.close();

        UserManager manager =
            new UserManager(
                new JsonUserRepository(
                    ARQUIVO_TESTE
                )
            );

        assertThat(manager.getUsuarios())
            .isNotNull();

        // admin é criado automaticamente
        assertThat(manager.buscar("admin"))
            .isNotNull();
    }

    // MC:DC caminho TRUE para buscar(login) != null
    @Test
    @DisplayName("Teste Estrutural: Buscar deve retornar usuário existente")
    void buscarUsuarioExistente() {

        User user =
            manager.buscar("admin");

        assertThat(user)
            .isNotNull();
    }

    // MC:DC caminho FALSE para buscar(login) != null
    @Test
    @DisplayName("Teste Estrutural: Buscar deve retornar null")
    void buscarUsuarioInexistente() {

        User user =
            manager.buscar("naoExiste");

        assertThat(user)
            .isNull();
    }

    @Test
    @DisplayName("Teste estrutural: Deve tratar exceção ao salvar JSON")
    void deveTratarExcecaoAoSalvarJson() {

        JsonUserRepository repo =
            new JsonUserRepository(
                "/diretorio/invalido/usuarios.json"
            );

        List<User> usuarios =
            new ArrayList<>();

        usuarios.add(
            new User(
                "douglas",
                "123",
                "avatar",
                false
            )
        );

        // cobre catch(Exception e)
        repo.salvar(usuarios);

        assertThat(usuarios)
            .isNotEmpty();
    }
    
    @Property
    @DisplayName("Teste de Propriedade: Usuário cadastrado deve conseguir logar")
    void propriedadeUsuarioCadastradoDeveLogar() {
        
        // pré condições: todo usuário deve ser cadastrado com login único e senha
        // pós condições: todo usuário cadastrado deve conseguir logar
        for(int i = 0; i < 50; i++) {

            String login =
                "user" + i;

            String senha =
                "senha" + i;

            manager.cadastrar(
                login,
                senha,
                "avatar"
            );

            User user =
                manager.login(
                    login,
                    senha
                );

            assertThat(user)
                .isNotNull();
        }
    }

}