package model;

import java.time.LocalDate;

public class CommissionEmployee extends Employee {
    private double baseSalary;
    private double commissionRate;
    private double salesAmount;

    public CommissionEmployee(int id, String name, Gender gender, LocalDate hireDate,
                              double baseSalary, double commissionRate, double salesAmount) {
        super(id, name, gender, hireDate);
        setBaseSalary(baseSalary);
        setCommissionRate(commissionRate);
        setSalesAmount(salesAmount);
    }

    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double baseSalary) {
        this.baseSalary = nonNegative(baseSalary, "Base salary");
    }

    public double getCommissionRate() { return commissionRate; }
    public void setCommissionRate(double commissionRate) {
        nonNegative(commissionRate, "Commission rate");
        if (commissionRate > 1) {
            throw new IllegalArgumentException("Commission rate must be between 0 and 1.");
        }
        this.commissionRate = commissionRate;
    }

    public double getSalesAmount() { return salesAmount; }
    public void setSalesAmount(double salesAmount) {
        this.salesAmount = nonNegative(salesAmount, "Sales amount");
    }

    public double calculateCommission() {
        return salesAmount * commissionRate;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + calculateCommission();
    }

    @Override
    public String toString() {
        return "CommissionEmployee{" + super.toString() + ", baseSalary=" + baseSalary
                + ", commissionRate=" + commissionRate + ", salesAmount=" + salesAmount
                + ", salary=" + calculateSalary() + "}";
    }
}
