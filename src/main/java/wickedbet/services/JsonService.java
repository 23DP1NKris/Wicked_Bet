package wickedbet.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import wickedbet.adapters.LocalDateAdapter;
import wickedbet.models.SlotStats;
import wickedbet.models.User;
import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonService {
    private static final String USERS_FILE_PATH = "src/main/resources/data/users.json";
    private static final String STATS_FILE_PATH = "src/main/resources/data/slotstats.json";

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter())  // uses an adapter for the registration date
            .setPrettyPrinting() // makes the json look more readable
            .create();

    public void saveUser(User user) {
        List<User> users = loadUsers(); // loads all users from the json file
        users.add(user);    // adds the user to a list
        writeUsersToFile(users); // writes the user to json
    }

    public List<User> loadUsers() {
        try {
            Path path = Paths.get(USERS_FILE_PATH);

            if (!Files.exists(path)) { // if the file doesn't exist it creates one
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                return new ArrayList<>();
            }

            try (Reader reader = new FileReader(path.toFile())) { // reads the file
                Type userListType = new TypeToken<ArrayList<User>>() {
                }.getType();
                List<User> users = gson.fromJson(reader, userListType);
                return (users != null) ? users : new ArrayList<>();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // in case of an error it creates a new array list
        }
    }

    public void writeUsersToFile(List<User> users) {
        // writes the user to the given file
        try (Writer writer = new FileWriter(USERS_FILE_PATH)) {
            gson.toJson(users, writer); // writes the list of users to json
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserUpdate(User updatedUser) {
        List<User> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                break;
            }
        }
        writeUsersToFile(users); // writes the users to json
    }

    public void saveSlotStats(SlotStats stats) {
        try (Writer writer = new FileWriter(STATS_FILE_PATH)) {
            gson.toJson(stats, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SlotStats loadSlotStats() {
        try (Reader reader = new FileReader(STATS_FILE_PATH)) {
            return gson.fromJson(reader, SlotStats.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new SlotStats(0, BigDecimal.ZERO, BigDecimal.ZERO);
        }
    }
}
