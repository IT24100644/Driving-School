package service;

import model.Progress;
import util.FileHandler;

import java.util.List;
import java.util.stream.Collectors;

public class ProgressService {
    private static final String PROGRESS_FILE = "progress.txt";

    public boolean saveProgress(Progress progress) {
        List<Progress> allProgress = getAllProgress();

        // Remove existing progress for this student if it exists
        allProgress.removeIf(p -> p.getStudentId().equals(progress.getStudentId()));

        allProgress.add(progress);
        return FileHandler.saveProgress(allProgress, PROGRESS_FILE);
    }

    public List<Progress> getAllProgress() {
        return FileHandler.loadProgress(PROGRESS_FILE);
    }

    public Progress getProgressByStudentId(String studentId) {
        return getAllProgress().stream()
                .filter(p -> p.getStudentId().equals(studentId))
                .findFirst()
                .orElse(new Progress(studentId));
    }

    public List<Progress> getProgressByInstructor(List<String> studentIds) {
        return getAllProgress().stream()
                .filter(p -> studentIds.contains(p.getStudentId()))
                .collect(Collectors.toList());
    }

    public boolean addLessonFeedback(String studentId, String lessonId, String feedback) {
        Progress progress = getProgressByStudentId(studentId);
        progress.addCompletedLesson(lessonId, feedback);
        return saveProgress(progress);
    }

    public boolean updateSkill(String studentId, String skill, boolean mastered) {
        Progress progress = getProgressByStudentId(studentId);
        progress.markSkill(skill, mastered);
        return saveProgress(progress);
    }

    public boolean deleteProgress(String studentId) {
        List<Progress> allProgress = getAllProgress();
        boolean removed = allProgress.removeIf(p -> p.getStudentId().equals(studentId));
        if (removed) {
            return FileHandler.saveProgress(allProgress, PROGRESS_FILE);
        }
        return false;
    }
}
