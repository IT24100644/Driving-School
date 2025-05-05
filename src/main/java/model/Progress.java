package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Progress implements Serializable {
    private String studentId;
    private int lessonsCompleted;
    private int lessonsAttended;
    private Map<String, Boolean> skills; // Skill name -> mastered
    private String overallStatus; // "Beginner", "Intermediate", "Ready for Test"
    private Map<String, String> lessonFeedback; // Lesson ID -> feedback

    public Progress() {
        this.skills = new HashMap<>();
        this.lessonFeedback = new HashMap<>();
        this.overallStatus = "Beginner";
    }

    public Progress(String studentId) {
        this();
        this.studentId = studentId;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public int getLessonsCompleted() { return lessonsCompleted; }
    public void setLessonsCompleted(int lessonsCompleted) { this.lessonsCompleted = lessonsCompleted; }

    public int getLessonsAttended() { return lessonsAttended; }
    public void setLessonsAttended(int lessonsAttended) { this.lessonsAttended = lessonsAttended; }

    public Map<String, Boolean> getSkills() { return skills; }
    public void setSkills(Map<String, Boolean> skills) { this.skills = skills; }

    public String getOverallStatus() { return overallStatus; }
    public void setOverallStatus(String overallStatus) { this.overallStatus = overallStatus; }

    public Map<String, String> getLessonFeedback() { return lessonFeedback; }
    public void setLessonFeedback(Map<String, String> lessonFeedback) { this.lessonFeedback = lessonFeedback; }

    // Business methods
    public void addCompletedLesson(String lessonId, String feedback) {
        lessonsCompleted++;
        lessonFeedback.put(lessonId, feedback);
        updateOverallStatus();
    }

    public void markSkill(String skill, boolean mastered) {
        skills.put(skill, mastered);
        updateOverallStatus();
    }

    private void updateOverallStatus() {
        int masteredSkills = (int) skills.values().stream().filter(b -> b).count();

        if (masteredSkills >= 10) {
            overallStatus = "Ready for Test";
        } else if (masteredSkills >= 5) {
            overallStatus = "Intermediate";
        } else {
            overallStatus = "Beginner";
        }
    }

    public double getProgressPercentage() {
        if (skills.isEmpty()) return 0;
        long mastered = skills.values().stream().filter(b -> b).count();
        return (double) mastered / skills.size() * 100;
    }
}
