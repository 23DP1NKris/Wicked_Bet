package wickedbet.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import wickedbet.adapters.LocalDateAdapter;
import wickedbet.models.User;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonService {
    private static final String FILE_PATH = "src/main/resources/data/users.json";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    public void saveUser(User user) {
        List<User> users = loadUsers();
        users.add(user);
        writeUsersToFile(users);
    }

    public List<User> loadUsers() {
        try {
            Path path = Paths.get(FILE_PATH);

            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                return new ArrayList<>();
            }

            try (Reader reader = new FileReader(path.toFile())) {
                Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
                List<User> users = gson.fromJson(reader, userListType);
                return (users != null) ? users : new ArrayList<>();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void writeUsersToFile(List<User> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
