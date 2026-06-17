package st.project.model;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import st.project.model.Ranking;
import st.project.model.User;
import st.project.model.UserManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RankingTest {
    @SuppressWarnings("static-access")
    @Test
    @DisplayName("Teste de domínio: Deve retornar ranking")
    void deveRetornarRanking() {

        UserRepository repo = mock(UserRepository.class);

        when(repo.carregar()).thenReturn(new ArrayList<>());

        UserManager manager = new UserManager(repo);

        Ranking ranking = new Ranking();
        
        List<User> resultado = ranking.ordenar(manager.getUsuarios());

        assertThat(resultado).isNotNull();
    }
}
