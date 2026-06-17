package st.project.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUserRepository
    implements UserRepository {

    private final String arquivo;

    private final Gson gson =
        new Gson();

    public JsonUserRepository(String arquivo) {

        this.arquivo = arquivo;
    }

    @Override
    public List<User> carregar() {

        try(FileReader reader =
            new FileReader(arquivo)) {

            List<User> usuarios =
                gson.fromJson(
                    reader,
                    new TypeToken<List<User>>(){}.getType()
                );

            return usuarios != null
                ? usuarios
                : new ArrayList<>();
        }
        catch(Exception e) {

            return new ArrayList<>();
        }
    }

    @Override
    public void salvar(List<User> usuarios) {

        try(FileWriter writer =
            new FileWriter(arquivo)) {

            gson.toJson(usuarios, writer);

        }
        catch(Exception e) {

            e.printStackTrace();
        }
    }
}