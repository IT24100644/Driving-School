package service;

import model.*;
import util.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);
    private static final String REVIEWS_FILE = "reviews.txt";
    private final UserService userService;
    private final LessonService lessonService;

    public ReviewService(UserService userService, LessonService lessonService) {
        if (userService == null || lessonService == null) {
            throw new IllegalArgumentException("UserService and LessonService cannot be null");
        }
        this.userService = userService;
        this.lessonService = lessonService;
    }

    public boolean submitReview(Review review) {
        if (!isValidReview(review)) {
            LOGGER.warn("Invalid review data: {}", review);
            return false;
        }

        if (!validateReviewTarget(review)) {
            LOGGER.warn("Invalid review target: reviewId={}, targetId={}", review.getReviewId(), review.getTargetId());
            return false;
        }

        List<Review> reviews = getAllReviews();
        reviews.add(review);
        boolean success = FileHandler.saveReviews(reviews, REVIEWS_FILE);
        if (!success) {
            LOGGER.error("Failed to save reviews to file: {}", REVIEWS_FILE);
        }
        return success;
    }

    public List<Review> getAllReviews() {
        try {
            return FileHandler.loadReviews(REVIEWS_FILE);
        } catch (Exception e) {
            LOGGER.error("Error loading reviews from file: {}", REVIEWS_FILE, e);
            return new ArrayList<>();
        }
    }

    public List<Review> getReviewsByStudent(String studentId) {
        if (studentId == null) {
            LOGGER.warn("Invalid student ID: null");
            return new ArrayList<>();
        }
        return getAllReviews().stream()
                .filter(r -> studentId.equals(r.getStudentId()))
                .collect(Collectors.toList());
    }

    public List<Review> getReviewsByInstructor(String instructorId) {
        if (instructorId == null) {
            LOGGER.warn("Invalid instructor ID: null");
            return new ArrayList<>();
        }
        return getAllReviews().stream()
                .filter(r -> r instanceof InstructorReview && instructorId.equals(r.getTargetId()))
                .collect(Collectors.toList());
    }

    public List<Review> getReviewsByLesson(String lessonId) {
        if (lessonId == null) {
            LOGGER.warn("Invalid lesson ID: null");
            return new ArrayList<>();
        }
        return getAllReviews().stream()
                .filter(r -> r instanceof LessonReview && lessonId.equals(r.getTargetId()))
                .collect(Collectors.toList());
    }

    public List<Review> getPendingReviews() {
        return getAllReviews().stream()
                .filter(r -> "Pending".equals(r.getStatus()))
                .collect(Collectors.toList());
    }

    public Review getReviewById(String reviewId) {
        if (reviewId == null) {
            LOGGER.warn("Invalid review ID: null");
            return null;
        }
        return getAllReviews().stream()
                .filter(r -> reviewId.equals(r.getReviewId()))
                .findFirst()
                .orElse(null);
    }

    public boolean updateReview(String reviewId, String content, int rating, String status) {
        if (reviewId == null || !isValidContent(content) || !isValidRating(rating) || (status != null && !isValidStatus(status))) {
            LOGGER.warn("Invalid update parameters: reviewId={}, content={}, rating={}, status={}", reviewId, content, rating, status);
            return false;
        }

        List<Review> reviews = getAllReviews();
        for (Review review : reviews) {
            if (reviewId.equals(review.getReviewId())) {
                review.setContent(sanitizeInput(content));
                review.setRating(rating);
                if (status != null) {
                    review.setStatus(status);
                }
                boolean success = FileHandler.saveReviews(reviews, REVIEWS_FILE);
                if (!success) {
                    LOGGER.error("Failed to save reviews during update: {}", REVIEWS_FILE);
                }
                return success;
            }
        }
        LOGGER.info("Review not found for update: {}", reviewId);
        return false;
    }

    public boolean deleteReview(String reviewId) {
        if (reviewId == null) {
            LOGGER.warn("Invalid review ID: null");
            return false;
        }

        List<Review> reviews = getAllReviews();
        boolean removed = reviews.removeIf(r -> reviewId.equals(r.getReviewId()));
        if (removed) {
            boolean success = FileHandler.saveReviews(reviews, REVIEWS_FILE);
            if (!success) {
                LOGGER.error("Failed to save reviews during deletion: {}", REVIEWS_FILE);
            }
            return success;
        }
        LOGGER.info("Review not found for deletion: {}", reviewId);
        return false;
    }

    private boolean isValidReview(Review review) {
        if (review == null ||
                review.getReviewId() == null ||
                review.getStudentId() == null ||
                review.getContent() == null ||
                review.getStatus() == null ||
                !isValidRating(review.getRating()) ||
                !isValidStatus(review.getStatus())) {
            return false;
        }
        return review instanceof InstructorReview || review instanceof LessonReview;
    }

    private boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    private boolean isValidStatus(String status) {
        return status != null && List.of("Pending", "Approved", "Rejected").contains(status);
    }

    private boolean isValidContent(String content) {
        return content != null && !content.trim().isEmpty() && content.length() <= 1000;
    }

    private boolean validateReviewTarget(Review review) {
        if (review instanceof InstructorReview) {
            User instructor = userService.getUserById(((InstructorReview) review).getInstructorId());
            return instructor instanceof Instructor;
        } else if (review instanceof LessonReview) {
            Lesson lesson = lessonService.getLessonById(((LessonReview) review).getLessonId());
            return lesson != null && lesson.getStudent().getId().equals(review.getStudentId()) && "Completed".equals(lesson.getStatus());
        }
        return false;
    }

    private String sanitizeInput(String input) {
        // Basic sanitization to prevent XSS
        if (input == null) return "";
        return input.replaceAll("[<>\"&']", "");
    }
}
