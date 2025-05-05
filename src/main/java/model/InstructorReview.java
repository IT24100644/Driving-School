package model;

public class InstructorReview extends Review {
    private String instructorId;

    public InstructorReview() {
        super();
    }

    public InstructorReview(String reviewId, String studentId, String instructorId,
                            int rating, String comment) {
        super(reviewId, studentId, rating, comment);
        this.instructorId = instructorId;
    }

    // Getters and Setters
    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    @Override
    public String getReviewType() {
        return "INSTRUCTOR";
    }

    @Override
    public String getReviewedEntityId() {
        return instructorId;
    }
}
