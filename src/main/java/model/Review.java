package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Review implements Serializable {
    private String reviewId;
    private String studentId;
    private int rating; // 1-5
    private String comment;
    private LocalDate reviewDate;
    private boolean isEdited;

    public Review() {
        this.reviewDate = LocalDate.now();
        this.isEdited = false;
    }

    public Review(String reviewId, String studentId, int rating, String comment) {
        this();
        this.reviewId = reviewId;
        this.studentId = studentId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }

    public boolean isEdited() { return isEdited; }
    public void setEdited(boolean edited) { isEdited = edited; }

    // Abstract methods
    public abstract String getReviewType();
    public abstract String getReviewedEntityId();
}

