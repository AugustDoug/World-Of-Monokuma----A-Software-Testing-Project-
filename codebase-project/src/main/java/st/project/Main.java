package st.project;

import javax.swing.JOptionPane;

import st.project.model.JsonUserRepository;
import st.project.model.UserRepository;
import st.project.model.User;
import st.project.model.UserManager;
import st.project.view.GameGUI;

public class Main {

    // interfaces para entrada e tela
    public interface Entrada {

        String lerLogin();

        String lerSenha();

        int confirmarCadastro();
    }

    public static class EntradaGUI implements Entrada {

        @Override
        public String lerLogin() {

            return JOptionPane.showInputDialog("Login:");
        }

        @Override
        public String lerSenha() {

            return JOptionPane.showInputDialog("Senha:");
        }

        @Override
        public int confirmarCadastro() {

            return JOptionPane.showConfirmDialog(
                null,
                "Usuário não existe. Deseja cadastrar?"
            );
        }
    }

    public interface TelaJogo {

        void abrir(User user, UserManager manager);
    }

    public static class TelaJogoGUI implements TelaJogo {

        @Override
        public void abrir(User user, UserManager manager) {

            new GameGUI(user, manager);
        }
    }

    // componentes principais

    private Entrada entrada;

    private TelaJogo tela;

    private UserManager manager;

    public Main(
        Entrada entrada,
        TelaJogo tela,
        UserManager manager
    ) {

        this.entrada = entrada;
        this.tela = tela;
        this.manager = manager;
    }

    public boolean iniciar() {

        // ao iniciar solicita login e senha
        while(true) {

            String login =
                entrada.lerLogin();

            String senha =
                entrada.lerSenha();

            User user =
                manager.login(login, senha);

            // caso não encontre o usuário no cadastro, pergunta se deseja cadastrar
            if(user != null) {

                user.adicionarSessao();

                manager.salvarPontuacoes();

                tela.abrir(user, manager);

                return true;
            }
            // se sim, retorna true e volta pra tela de login
            //  se não, retorna false e fecha o jogo

            int escolha =
                entrada.confirmarCadastro();

            if(escolha == 0) {

                manager.cadastrar(
                    login,
                    senha,
                    "/images/monoplayer.png"
                );
            }
            else {

                return false;
            }
        }
    }

    public static Main criarAplicacao() {

        // inicia repositório, manager e main
        
        UserRepository repo =
            new JsonUserRepository(
                "usuarios.json"
            );

        return new Main(
            new EntradaGUI(),
            new TelaJogoGUI(),
            new UserManager(repo)
        );
    }

    public static void main(String[] args) {

        criarAplicacao().iniciar();
    }
}