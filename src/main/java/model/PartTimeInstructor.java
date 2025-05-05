package model;

public class PartTimeInstructor extends Instructor {
    private static final int MAX_LESSONS = 4;
    private static final double HOURLY_RATE = 25.00;

    public PartTimeInstructor() {
        super();
        setFullTime(false);
        setMaxLessonsPerDay(MAX_LESSONS);
    }

    public PartTimeInstructor(String id, String name, String email, String password) {
        super(id, name, email, password);
        setFullTime(false);
        setMaxLessonsPerDay(MAX_LESSONS);
    }

    public double calculatePay(int hoursWorked) {
        return hoursWorked * HOURLY_RATE;
    }
}
