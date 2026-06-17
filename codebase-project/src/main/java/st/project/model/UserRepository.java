package st.project.model;

import java.util.List;

public interface UserRepository {

    List<User> carregar();

    void salvar(List<User> usuarios);
}