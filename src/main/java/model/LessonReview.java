package model;

public class LessonReview extends Review {
    private String lessonId;

    public LessonReview() {
        super();
    }

    public LessonReview(String reviewId, String studentId, String lessonId,
                        int rating, String comment) {
        super(reviewId, studentId, rating, comment);
        this.lessonId = lessonId;
    }

    // Getters and Setters
    public String getLessonId() { return lessonId; }
    public void setLessonId(String lessonId) { this.lessonId = lessonId; }

    @Override
    public String getReviewType() {
        return "LESSON";
    }

    @Override
    public String getReviewedEntityId() {
        return lessonId;
    }
}
