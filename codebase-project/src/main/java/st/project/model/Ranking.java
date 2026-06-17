package st.project.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking {

    public static List<User> ordenar(
        List<User> usuarios
    ) {

        List<User> copia =
            new ArrayList<>(usuarios);

        copia.sort(
            Comparator.comparingInt(
                User::getPontuacao
            ).reversed()
        );

        return copia;
    }
}