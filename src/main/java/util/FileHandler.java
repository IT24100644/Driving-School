package util;

import model.Payment;
import model.Lesson;
import model.Progress;
import model.Review;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);

    // User Management (Component 01)
    public static boolean saveUsers(List<User> users, String filename) {
        File file = new File(filename);
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
                LOGGER.info("Created directory for users file: {}", parentDir.getAbsolutePath());
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(users);
                LOGGER.info("Successfully saved {} users to {}", users.size(), filename);
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save users to {}: {}", filename, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while saving users to {}: {}", filename, e.getMessage(), e);
            return false;
        }
    }

    public static List<User> loadUsers(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            LOGGER.info("Users file {} does not exist, returning empty list", filename);
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<User> users = (List<User>) ois.readObject();
            LOGGER.info("Successfully loaded {} users from {}", users.size(), filename);
            return users;
        } catch (IOException e) {
            LOGGER.error("IO error while loading users from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found while loading users from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while loading users from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    // Lesson Scheduling (Component 02)
    public static boolean saveLessons(List<Lesson> lessons, String filename) {
        File file = new File(filename);
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
                LOGGER.info("Created directory for lessons file: {}", parentDir.getAbsolutePath());
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(lessons);
                LOGGER.info("Successfully saved {} lessons to {}", lessons.size(), filename);
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save lessons to {}: {}", filename, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while saving lessons to {}: {}", filename, e.getMessage(), e);
            return false;
        }
    }

    public static List<Lesson> loadLessons(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            LOGGER.info("Lessons file {} does not exist, returning empty list", filename);
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Lesson> lessons = (List<Lesson>) ois.readObject();
            LOGGER.info("Successfully loaded {} lessons from {}", lessons.size(), filename);
            return lessons;
        } catch (IOException e) {
            LOGGER.error("IO error while loading lessons from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found while loading lessons from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while loading lessons from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    // Student Progress Tracking (Component 04)
    public static boolean saveProgress(List<Progress> progressRecords, String filename) {
        File file = new File(filename);
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
                LOGGER.info("Created directory for progress file: {}", parentDir.getAbsolutePath());
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(progressRecords);
                LOGGER.info("Successfully saved {} progress records to {}", progressRecords.size(), filename);
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save progress records to {}: {}", filename, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while saving progress records to {}: {}", filename, e.getMessage(), e);
            return false;
        }
    }

    public static List<Progress> loadProgress(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            LOGGER.info("Progress file {} does not exist, returning empty list", filename);
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Progress> progressRecords = (List<Progress>) ois.readObject();
            LOGGER.info("Successfully loaded {} progress records from {}", progressRecords.size(), filename);
            return progressRecords;
        } catch (IOException e) {
            LOGGER.error("IO error while loading progress from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found while loading progress from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while loading progress from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    // Payment and Billing (Component 05)
    public static boolean savePayments(List<Payment> payments, String filename) {
        File file = new File(filename);
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
                LOGGER.info("Created directory for payments file: {}", parentDir.getAbsolutePath());
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(payments);
                LOGGER.info("Successfully saved {} payments to {}", payments.size(), filename);
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save payments to {}: {}", filename, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while saving payments to {}: {}", filename, e.getMessage(), e);
            return false;
        }
    }

    public static List<Payment> loadPayments(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            LOGGER.info("Payments file {} does not exist, returning empty list", filename);
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Payment> payments = (List<Payment>) ois.readObject();
            LOGGER.info("Successfully loaded {} payments from {}", payments.size(), filename);
            return payments;
        } catch (IOException e) {
            LOGGER.error("IO error while loading payments from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found while loading payments from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while loading payments from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    // Feedback and Reviews (Component 06)
    public static boolean saveReviews(List<Review> reviews, String filename) {
        File file = new File(filename);
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
                LOGGER.info("Created directory for reviews file: {}", parentDir.getAbsolutePath());
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(reviews);
                LOGGER.info("Successfully saved {} reviews to {}", reviews.size(), filename);
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save reviews to {}: {}", filename, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while saving reviews to {}: {}", filename, e.getMessage(), e);
            return false;
        }
    }

    public static List<Review> loadReviews(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            LOGGER.info("Reviews file {} does not exist, returning empty list", filename);
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Review> reviews = (List<Review>) ois.readObject();
            LOGGER.info("Successfully loaded {} reviews from {}", reviews.size(), filename);
            return reviews;
        } catch (IOException e) {
            LOGGER.error("IO error while loading reviews from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found while loading reviews from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while loading reviews from {}: {}", filename, e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}