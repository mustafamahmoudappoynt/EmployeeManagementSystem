package model;

import java.time.LocalDate;
import java.util.Objects;

/** Common information shared by all employee types. */
public abstract class Employee {
    private int id;
    private String name;
    private Gender gender;
    private LocalDate hireDate;
    private Department department;

    public Employee(int id, String name, Gender gender, LocalDate hireDate) {
        setId(id);
        setName(name);
        setGender(gender);
        setHireDate(hireDate);
    }

    public int getId() { return id; }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive.");
        }
        if (department != null) {
            Employee existing = department.findEmployee(id);
            if (existing != null && existing != this) {
                throw new IllegalArgumentException("Employee ID already exists in this department.");
            }
        }
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        Objects.requireNonNull(name, "Employee name is required.");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be blank.");
        }
        this.name = name.trim();
    }

    public Gender getGender() { return gender; }

    public void setGender(Gender gender) {
        this.gender = Objects.requireNonNull(gender, "Gender is required.");
    }

    public LocalDate getHireDate() { return hireDate; }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = Objects.requireNonNull(hireDate, "Hire date is required.");
    }

    public Department getDepartment() { return department; }

    /** Assigning a department also updates its employee list. Null unassigns it. */
    public void setDepartment(Department department) {
        if (this.department == department) {
            return;
        }
        if (department == null) {
            this.department.removeEmployee(id);
        } else {
            department.addEmployee(this);
        }
    }

    // Department uses this after updating its list to avoid recursive calls.
    void assignDepartment(Department department) {
        this.department = department;
    }

    protected static double nonNegative(double value, String field) {
        if (!Double.isFinite(value) || value < 0) {
            throw new IllegalArgumentException(field + " must be finite and non-negative.");
        }
        return value;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {
        return "id=" + id + ", name='" + name + "', gender=" + gender
                + ", hireDate=" + hireDate + ", department="
                + (department == null ? "Unassigned" : department.getName());
    }
}
