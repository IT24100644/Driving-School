package model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends User implements Serializable {
    private int experienceYears;
    private boolean isFullTime;
    private List<String> certifications;
    private List<AvailabilitySlot> availability;
    private boolean isFemale;
    private int maxLessonsPerDay;

    public Instructor() {
        this.certifications = new ArrayList<>();
        this.availability = new ArrayList<>();
    }

    public Instructor(String id, String name, String email, String password) {
        super(id, name, email, password);
        this.certifications = new ArrayList<>();
        this.availability = new ArrayList<>();
    }

    // Getters and Setters
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

    public boolean isFullTime() { return isFullTime; }
    public void setFullTime(boolean fullTime) { isFullTime = fullTime; }

    public List<String> getCertifications() { return certifications; }
    public void setCertifications(List<String> certifications) { this.certifications = certifications; }

    public List<AvailabilitySlot> getAvailability() { return availability; }
    public void setAvailability(List<AvailabilitySlot> availability) { this.availability = availability; }

    public boolean isFemale() { return isFemale; }
    public void setFemale(boolean female) { isFemale = female; }

    public int getMaxLessonsPerDay() { return maxLessonsPerDay; }
    public void setMaxLessonsPerDay(int maxLessonsPerDay) { this.maxLessonsPerDay = maxLessonsPerDay; }

    public void addCertification(String certification) {
        if (!certifications.contains(certification)) {
            certifications.add(certification);
        }
    }

    public void removeCertification(String certification) {
        certifications.remove(certification);
    }

    public void addAvailabilitySlot(AvailabilitySlot slot) {
        availability.add(slot);
    }

    public void removeAvailabilitySlot(AvailabilitySlot slot) {
        availability.remove(slot);
    }

    @Override
    public String getRole() {
        return "INSTRUCTOR";
    }
}

