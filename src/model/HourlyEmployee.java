package model;

import java.time.LocalDate;

public class HourlyEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;
    private double overtimeRate;

    public HourlyEmployee(int id, String name, Gender gender, LocalDate hireDate,
                          double hourlyRate, double hoursWorked, double overtimeRate) {
        super(id, name, gender, hireDate);
        setHourlyRate(hourlyRate);
        setHoursWorked(hoursWorked);
        setOvertimeRate(overtimeRate);
    }

    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = nonNegative(hourlyRate, "Hourly rate");
    }

    public double getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = nonNegative(hoursWorked, "Hours worked");
    }

    public double getOvertimeRate() { return overtimeRate; }
    public void setOvertimeRate(double overtimeRate) {
        nonNegative(overtimeRate, "Overtime rate");
        if (overtimeRate < 1) {
            throw new IllegalArgumentException("Overtime multiplier must be at least 1.");
        }
        this.overtimeRate = overtimeRate;
    }

    @Override
    public double calculateSalary() {
        double regularHours = Math.min(hoursWorked, 40);
        double overtimeHours = Math.max(0, hoursWorked - 40);
        return regularHours * hourlyRate + overtimeHours * hourlyRate * overtimeRate;
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" + super.toString() + ", hourlyRate=" + hourlyRate
                + ", hoursWorked=" + hoursWorked + ", overtimeRate=" + overtimeRate
                + ", salary=" + calculateSalary() + "}";
    }
}
