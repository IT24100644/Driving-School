package model;

public class Student extends User {
    private String licenseNumber;

    public Student() {}

    public Student(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() { return "STUDENT"; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
}
