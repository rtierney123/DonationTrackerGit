package com.track.brachio.donationtracker.model;
import java.util.List;
import java.util.ArrayList;

public class Model {
    //look at model from Lab32VM
    private static final Model _instance = new Model();
    private List<User> _users;

    public static Model getInstance() {
        return _instance;
    }

    private Model() {
        _users = new ArrayList<>();

        //just for demo, delete later with more dynamic app
        demoAdd();
    }

    private boolean demoAdd() {
        User temp = new User("01", "user", "email@email", "pass", UserType.Admin);
        addUser(temp);
        return true;
    }

    public List<User> getUsers() {
        return _users;
    }

    public boolean addUser(User user) {
        for (User u : _users) {
            if (user.getUsername().equals(u.getUsername())) {
                return false;
            }
            if (user.getPassword().equals(u.getPassword())) {
                return false;
            }
        }
            _users.add(user);
            return true;
    }

    public boolean signInCheck(String username, String password) {
        for (User u : _users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
