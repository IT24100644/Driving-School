package service;

import model.Instructor;
import model.AvailabilitySlot;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class InstructorService {
    public List<Instructor> getAvailableInstructors(LocalDateTime dateTime, int duration) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime startTime = dateTime.toLocalTime();
        LocalTime endTime = startTime.plusMinutes(duration);

        return getAllInstructors().stream()
                .filter(instructor -> instructor.getAvailability().stream()
                        .anyMatch(slot -> slot.getDayOfWeek() == dayOfWeek &&
                                startTime.isAfter(slot.getStartTime()) &&
                                endTime.isBefore(slot.getEndTime())))
                .collect(Collectors.toList());
    }

    // Placeholder for getAllInstructors method
    private List<Instructor> getAllInstructors() {
        // Implementation to retrieve all instructors (e.g., from UserService or a data source)
        return null; // Replace with actual implementation
    }
}