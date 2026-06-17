package st.project.model;

import java.util.List;

public class UserManager {

    // pré condições: todo usuário deve ser cadastrado com login único e senha
    // pós condições: todo usuário cadastrado deve conseguir logar

    private List<User> usuarios;

    private UserRepository repository;

    public UserManager(
        UserRepository repository
    ) {

        this.repository = repository;

        usuarios = repository.carregar();

        if(buscar("admin") == null) {

            usuarios.add(
                new User(
                    "admin",
                    "admin",
                    "/images/admin.png",
                    true
                )
            );

            repository.salvar(usuarios);
        }
    }

    public boolean cadastrar(
        String login,
        String senha,
        String avatar
    ) {

        if(buscar(login) != null) {
            return false;
        }

        usuarios.add(
            new User(
                login,
                senha,
                avatar,
                false
            )
        );

        repository.salvar(usuarios);

        return true;
    }

    public User login(
        String login,
        String senha
    ) {

        for(User user : usuarios) {

            if(
                user.getLogin().equals(login)
                &&
                user.getSenha().equals(senha)
            ) {
                return user;
            }
        }

        return null;
    }

    public User buscar(String login) {

        for(User user : usuarios) {

            if(user.getLogin().equals(login)) {
                return user;
            }
        }

        return null;
    }

    public boolean remover(String login) {

        User user = buscar(login);

        if(user == null) {
            return false;
        }

        usuarios.remove(user);

        repository.salvar(usuarios);

        return true;
    }

    public void salvarPontuacoes() {

        repository.salvar(usuarios);
    }

    public List<User> getUsuarios() {
        return usuarios;
    }
}