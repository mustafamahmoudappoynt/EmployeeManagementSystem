package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Owns membership and keeps each employee's department reference consistent. */
public class Department {
    private int id;
    private String name;
    private Employee manager;
    private List<Employee> employees = new ArrayList<>();

    public Department(int id, String name, Employee manager, List<Employee> employees) {
        setId(id);
        setName(name);
        List<Employee> initial = validatedCopy(employees);
        if (manager != null && !initial.contains(manager)) {
            initial.add(manager);
        }
        setEmployees(initial);
        setManager(manager);
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Department ID must be positive.");
        }
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        Objects.requireNonNull(name, "Department name is required.");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be blank.");
        }
        this.name = name.trim();
    }

    public Employee getManager() { return manager; }
    public void setManager(Employee manager) {
        if (manager != null) {
            addEmployee(manager);
        }
        this.manager = manager;
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(new ArrayList<>(employees));
    }

    public void setEmployees(List<Employee> employees) {
        List<Employee> replacement = validatedCopy(employees);
        for (Employee employee : new ArrayList<>(this.employees)) {
            if (!replacement.contains(employee)) {
                removeEmployee(employee.getId());
            }
        }
        for (Employee employee : replacement) {
            addEmployee(employee);
        }
        this.employees = replacement;
    }

    private static List<Employee> validatedCopy(List<Employee> employees) {
        Objects.requireNonNull(employees, "Employee list is required.");
        List<Employee> copy = new ArrayList<>(employees);
        Set<Integer> ids = new HashSet<>();
        for (Employee employee : copy) {
            Objects.requireNonNull(employee, "Employee list cannot contain null.");
            if (!ids.add(employee.getId())) {
                throw new IllegalArgumentException("Employee IDs must be unique within a department.");
            }
        }
        return copy;
    }

    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "Employee is required.");
        Employee existing = findEmployee(employee.getId());
        if (existing == employee) {
            return;
        }
        if (existing != null) {
            throw new IllegalArgumentException("Employee ID already exists in this department.");
        }
        Department previous = employee.getDepartment();
        if (previous != null) {
            previous.removeEmployee(employee.getId());
        }
        employees.add(employee);
        employee.assignDepartment(this);
    }

    public void removeEmployee(int employeeId) {
        Employee employee = findEmployee(employeeId);
        if (employee != null) {
            employees.remove(employee);
            employee.assignDepartment(null);
            if (manager == employee) {
                manager = null;
            }
        }
    }

    public Employee findEmployee(int employeeId) {
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                return employee;
            }
        }
        return null;
    }

    public double calculateTotalPayroll() {
        double total = 0;
        for (Employee employee : employees) {
            total += employee.calculateSalary();
        }
        return total;
    }

    public void printAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "', manager="
                + (manager == null ? "None" : manager.getName())
                + ", employeeCount=" + employees.size()
                + ", totalPayroll=" + calculateTotalPayroll() + "}";
    }
}
