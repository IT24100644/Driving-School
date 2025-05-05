package service;

import model.User;
import model.Student;
import model.Instructor;
import util.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final String USERS_FILE = "users.txt";
    private List<User> users;

    public UserService() {
        this.users = loadUsersFromFile();
    }

    // Save the in-memory list of users to the file
    public boolean saveUsers() {
        if (users == null) {
            LOGGER.error("Cannot save users: user list is null");
            return false;
        }
        boolean success = FileHandler.saveUsers(users, USERS_FILE);
        if (success) {
            LOGGER.info("Successfully saved {} users to {}", users.size(), USERS_FILE);
        } else {
            LOGGER.error("Failed to save users to {}", USERS_FILE);
        }
        return success;
    }

    // Overloaded method to save a provided list of users
    public boolean saveUsers(List<User> usersToSave) {
        if (usersToSave == null) {
            LOGGER.error("Cannot save users: provided user list is null");
            return false;
        }
        this.users = new ArrayList<>(usersToSave); // Update in-memory list
        boolean success = FileHandler.saveUsers(users, USERS_FILE);
        if (success) {
            LOGGER.info("Successfully saved {} users to {}", users.size(), USERS_FILE);
        } else {
            LOGGER.error("Failed to save users to {}", USERS_FILE);
        }
        return success;
    }

    // Load users from file
    private List<User> loadUsersFromFile() {
        List<User> loadedUsers = FileHandler.loadUsers(USERS_FILE);
        if (loadedUsers == null) {
            LOGGER.warn("Loaded users list is null, initializing empty list");
            return new ArrayList<>();
        }
        LOGGER.info("Loaded {} users from {}", loadedUsers.size(), USERS_FILE);
        return loadedUsers;
    }

    // Create a new user with hashed password
    public boolean createUser(String id, String name, String email, String password, String role) {
        if (id == null || name == null || email == null || password == null || role == null) {
            LOGGER.warn("Invalid user data: id={}, name={}, email={}, role={}", id, name, email, role);
            return false;
        }

        if (getUserById(id) != null) {
            LOGGER.warn("User with ID {} already exists", id);
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user;
        switch (role.toLowerCase()) {
            case "student":
                user = new Student(id, name, email, hashedPassword);
                break;
            case "instructor":
                user = new Instructor(id, name, email, hashedPassword);
                break;
            default:
                LOGGER.warn("Invalid role: {}", role);
                return false;
        }

        users.add(user);
        return saveUsers();
    }

    // Retrieve a user by ID
    public User getUserById(String id) {
        if (id == null) {
            LOGGER.warn("Invalid user ID: null");
            return null;
        }
        return users.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElse(null);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Update an existing user
    public boolean updateUser(String id, String name, String email, String password) {
        if (id == null) {
            LOGGER.warn("Invalid user ID: null");
            return false;
        }

        User user = getUserById(id);
        if (user == null) {
            LOGGER.info("User not found for update: {}", id);
            return false;
        }

        if (name != null && !name.trim().isEmpty()) {
            user.setName(name);
        }
        if (email != null && !email.trim().isEmpty()) {
            user.setEmail(email);
        }
        if (password != null && !password.trim().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }

        return saveUsers();
    }

    // Delete a user
    public boolean deleteUser(String id) {
        if (id == null) {
            LOGGER.warn("Invalid user ID: null");
            return false;
        }

        boolean removed = users.removeIf(u -> id.equals(u.getId()));
        if (removed) {
            return saveUsers();
        }
        LOGGER.info("User not found for deletion: {}", id);
        return false;
    }

    // Authenticate a user
    public User authenticate(String email, String password) {
        if (email == null || password == null) {
            LOGGER.warn("Invalid authentication credentials: email={}, password={}", email, password);
            return null;
        }

        User user = users.stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            LOGGER.info("User authenticated successfully: {}", email);
            return user;
        }
        LOGGER.warn("Authentication failed for email: {}", email);
        return null;
    }

    // Get all users by role
    public List<User> getUsersByRole(String role) {
        if (role == null) {
            LOGGER.warn("Invalid role: null");
            return new ArrayList<>();
        }

        return users.stream()
                .filter(u -> {
                    if ("student".equalsIgnoreCase(role)) return u instanceof Student;
                    if ("instructor".equalsIgnoreCase(role)) return u instanceof Instructor;
                    return false;
                })
                .collect(Collectors.toList());
    }
}