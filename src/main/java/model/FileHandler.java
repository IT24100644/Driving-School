package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FileHandler{
    public abstract class Lesson implements Serializable {
        private String id;
        private Student student;
        private Instructor instructor;
        private LocalDateTime startTime;
        private int duration; // in minutes
        private String status; // Scheduled, Completed, Cancelled
        private String carType; // Manual, Automatic
        private String location;

        public Lesson() {}

        public Lesson(String id, Student student, Instructor instructor, LocalDateTime startTime,
                      int duration, String carType, String location) {
            this.id = id;
            this.student = student;
            this.instructor = instructor;
            this.startTime = startTime;
            this.duration = duration;
            this.status = "Scheduled";
            this.carType = carType;
            this.location = location;
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public Student getStudent() { return student; }
        public void setStudent(Student student) { this.student = student; }

        public Instructor getInstructor() { return instructor; }
        public void setInstructor(Instructor instructor) { this.instructor = instructor; }

        public LocalDateTime getStartTime() { return startTime; }
        public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

        public int getDuration() { return duration; }
        public void setDuration(int duration) { this.duration = duration; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getCarType() { return carType; }
        public void setCarType(String carType) { this.carType = carType; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public abstract String getLessonType();
    }

    class BeginnerLesson extends Lesson {
        private static int DEFAULT_DURATION = 60; // 1 hour

        public BeginnerLesson() {
            super();
        }

        public BeginnerLesson(String id, Student student, Instructor instructor,
                              LocalDateTime startTime, String carType, String location) {
            super(id, student, instructor, startTime, DEFAULT_DURATION, carType, location);
        }

        @Override
        public String getLessonType() {
            return "Beginner";
        }
    }

    class AdvancedLesson extends Lesson {
        private static int DEFAULT_DURATION = 90; // 1.5 hours

        public AdvancedLesson() {
            super();
        }

        public AdvancedLesson(String id, Student student, Instructor instructor,
                              LocalDateTime startTime, String carType, String location) {
            super(id, student, instructor, startTime, DEFAULT_DURATION, carType, location);
        }

        @Override
        public String getLessonType() {
            return "Advanced";
        }
    }

}
