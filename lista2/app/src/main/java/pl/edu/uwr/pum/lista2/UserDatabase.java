package pl.edu.uwr.pum.lista2;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static UserDatabase instance;
    private List<User> users;

    private UserDatabase() {
        users = new ArrayList<>();

        users.add(new User("BigMike07", "1234"));
        users.add(new User("hotDog", "zaqWSX"));
        users.add(new User("MarekPieczarek", "haslo"));
        users.add(new User("ObamaFan21", "aaazzz"));
        users.add(new User("BruceLee_Official", "dragon"));
    }

    public static synchronized UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public void addUser(String username, String password) {
        users.add(new User(username, password));
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUser(String username) {
        for (User user : users)
            if (user.getUsername().equals(username))
                return user;

        return null;
    }
    public boolean validate(String username, String password) {
        User user = findUser(username);
        if (user != null)
            return user.getPassword().equals(password);

        return false;
    }
}
