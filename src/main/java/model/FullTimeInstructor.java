package model;

public class FullTimeInstructor extends Instructor {
    private static final int MAX_LESSONS = 8;
    private static final double BASE_SALARY = 60000.00;

    public FullTimeInstructor() {
        super();
        setFullTime(true);
        setMaxLessonsPerDay(MAX_LESSONS);
    }

    public FullTimeInstructor(String id, String name, String email, String password) {
        super(id, name, email, password);
        setFullTime(true);
        setMaxLessonsPerDay(MAX_LESSONS);
    }

    public double calculateSalary() {
        return BASE_SALARY + (getExperienceYears() * 100);
    }
}
