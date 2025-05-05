package service;

import model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import util.FileHandler;

public class LessonService {
    private static final String LESSONS_FILE = "lessons.txt";
    private UserService userService;

    public LessonService(UserService userService) {
        this.userService = userService;
    }

    public boolean scheduleLesson(Lesson lesson) {
        if (!isInstructorAvailable(lesson.getInstructor(), lesson.getStartTime(), lesson.getDuration())) {
            return false;
        }

        List<Lesson> lessons = getAllLessons();
        lessons.add(lesson);
        return FileHandler.saveLessons(lessons, LESSONS_FILE);
    }

    public List<Lesson> getAllLessons() {
        return FileHandler.loadLessons(LESSONS_FILE);
    }

    public List<Lesson> getLessonsByStudent(String studentId) {
        return getAllLessons().stream()
                .filter(l -> l.getStudent().getId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByInstructor(String instructorId) {
        return getAllLessons().stream()
                .filter(l -> l.getInstructor().getId().equals(instructorId))
                .collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByDate(LocalDateTime date) {
        return getAllLessons().stream()
                .filter(l -> l.getStartTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }

    public boolean updateLesson(Lesson updatedLesson) {
        List<Lesson> lessons = getAllLessons();
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getId().equals(updatedLesson.getId())) {
                lessons.set(i, updatedLesson);
                return FileHandler.saveLessons(lessons, LESSONS_FILE);
            }
        }
        return false;
    }

    public boolean cancelLesson(String lessonId) {
        List<Lesson> lessons = getAllLessons();
        for (Lesson lesson : lessons) {
            if (lesson.getId().equals(lessonId)) {
                lesson.setStatus("Cancelled");
                return FileHandler.saveLessons(lessons, LESSONS_FILE);
            }
        }
        return false;
    }

    public Lesson getLessonById(String lessonId) {
        return getAllLessons().stream()
                .filter(l -> l.getId().equals(lessonId))
                .findFirst()
                .orElse(null);
    }

    public boolean isInstructorAvailable(Instructor instructor, LocalDateTime startTime, int duration) {
        LocalDateTime endTime = startTime.plusMinutes(duration);

        return getAllLessons().stream()
                .filter(l -> l.getInstructor().getId().equals(instructor.getId()))
                .filter(l -> !l.getStatus().equals("Cancelled"))
                .noneMatch(l -> {
                    LocalDateTime lessonStart = l.getStartTime();
                    LocalDateTime lessonEnd = lessonStart.plusMinutes(l.getDuration());
                    return (startTime.isBefore(lessonEnd) && endTime.isAfter(lessonStart));
                });
    }

    public List<Instructor> getAvailableInstructors(LocalDateTime startTime, int duration) {
        return userService.getAllUsers().stream()
                .filter(u -> u instanceof Instructor)
                .map(u -> (Instructor) u)
                .filter(i -> isInstructorAvailable(i, startTime, duration))
                .collect(Collectors.toList());
    }
}
