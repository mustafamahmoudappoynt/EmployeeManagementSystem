package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MonthlyEmployee extends Employee {
    private double monthlySalary;
    private int vacationDays;
    private double bonusPercentage;
    private boolean hasHealthInsurance;

    public MonthlyEmployee(int id, String name, Gender gender, LocalDate hireDate,
                           double monthlySalary, int vacationDays, double bonusPercentage,
                           boolean hasHealthInsurance) {
        super(id, name, gender, hireDate);
        setMonthlySalary(monthlySalary);
        setVacationDays(vacationDays);
        setBonusPercentage(bonusPercentage);
        setHasHealthInsurance(hasHealthInsurance);
    }

    public double getMonthlySalary() { return monthlySalary; }
    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = nonNegative(monthlySalary, "Monthly salary");
    }

    public int getVacationDays() { return vacationDays; }
    public void setVacationDays(int vacationDays) {
        if (vacationDays < 0) {
            throw new IllegalArgumentException("Vacation days cannot be negative.");
        }
        this.vacationDays = vacationDays;
    }

    public double getBonusPercentage() { return bonusPercentage; }
    public void setBonusPercentage(double bonusPercentage) {
        nonNegative(bonusPercentage, "Bonus percentage");
        if (bonusPercentage > 100) {
            throw new IllegalArgumentException("Bonus percentage must be between 0 and 100.");
        }
        this.bonusPercentage = bonusPercentage;
    }

    public boolean getHasHealthInsurance() { return hasHealthInsurance; }
    public void setHasHealthInsurance(boolean hasHealthInsurance) {
        this.hasHealthInsurance = hasHealthInsurance;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary + monthlySalary * bonusPercentage / 100.0;
    }

    /** One extra vacation day per five completed years of service. */
    public int calculateAdditionalVacation() {
        long years = Math.max(0, ChronoUnit.YEARS.between(getHireDate(), LocalDate.now()));
        return (int) (years / 5);
    }

    @Override
    public String toString() {
        return "MonthlyEmployee{" + super.toString() + ", monthlySalary=" + monthlySalary
                + ", vacationDays=" + vacationDays + ", bonusPercentage=" + bonusPercentage
                + ", hasHealthInsurance=" + hasHealthInsurance
                + ", additionalVacation=" + calculateAdditionalVacation()
                + ", salary=" + calculateSalary() + "}";
    }
}
