package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import model.CommissionEmployee;
import model.Department;
import model.Employee;
import model.Gender;
import model.HourlyEmployee;
import model.MonthlyEmployee;

/** Runnable demonstration and checks; no external test libraries required. */
public class MainTest {
    private static int checks;

    public static void main(String[] args) {
        checks = 0;
        MonthlyEmployee manager = new MonthlyEmployee(1, "Mostafa Mahmoud Sayed Shaaban",
                Gender.MALE, LocalDate.of(2020, 1, 15), 10000, 21, 10, true);
        CommissionEmployee salesperson = new CommissionEmployee(2, "Sara Ahmed",
                Gender.FEMALE, LocalDate.of(2024, 3, 1), 4000, 0.05, 20000);
        HourlyEmployee technician = new HourlyEmployee(3, "Omar Ali",
                Gender.MALE, LocalDate.of(2025, 6, 1), 100, 45, 1.5);
        Department department = new Department(1, "Operations", manager,
                Arrays.asList(manager, salesperson, technician));

        System.out.println("Employee Management System");
        System.out.println("Student: Mostafa Mahmoud Sayed Shaaban | ID: 2300170");
        System.out.println(department);
        department.printAllEmployees();
        System.out.printf(Locale.ROOT, "Total sample payroll: %.2f%n",
                department.calculateTotalPayroll());

        checkClose(11000, manager.calculateSalary(), "Monthly salary with a 10% bonus");
        checkClose(1000, salesperson.calculateCommission(), "Sales commission");
        checkClose(5000, salesperson.calculateSalary(), "Base salary plus commission");
        checkClose(4750, technician.calculateSalary(), "Regular pay plus overtime");
        checkClose(20750, department.calculateTotalPayroll(), "Polymorphic total payroll");
        check(department.findEmployee(2) == salesperson, "Find employee by ID");
        check(department.findEmployee(999) == null, "Missing employee returns null");
        check(manager.getDepartment() == department, "Manager belongs to department");

        technician.setHoursWorked(40);
        checkClose(4000, technician.calculateSalary(), "Exactly 40 hours has no overtime");
        technician.setHoursWorked(20);
        checkClose(2000, technician.calculateSalary(), "Part-time hours");
        technician.setHoursWorked(0);
        checkClose(0, technician.calculateSalary(), "Zero hours");
        technician.setHoursWorked(45);
        salesperson.setSalesAmount(0);
        checkClose(4000, salesperson.calculateSalary(), "Zero sales retains base salary");
        salesperson.setSalesAmount(20000);
        manager.setBonusPercentage(0);
        checkClose(10000, manager.calculateSalary(), "Zero bonus");
        manager.setBonusPercentage(10);

        LocalDate originalDate = manager.getHireDate();
        manager.setHireDate(LocalDate.now().minusYears(10));
        check(manager.calculateAdditionalVacation() == 2, "Ten completed years gives two days");
        manager.setHireDate(LocalDate.now().minusYears(5).plusDays(1));
        check(manager.calculateAdditionalVacation() == 0, "Before five-year anniversary");
        manager.setHireDate(LocalDate.now().plusYears(1));
        check(manager.calculateAdditionalVacation() == 0, "Future hire date gives no extra vacation");
        manager.setHireDate(originalDate);

        Department support = new Department(2, "Support", null, Collections.emptyList());
        technician.setDepartment(support);
        check(department.findEmployee(3) == null && support.findEmployee(3) == technician,
                "Transfer updates both departments");
        checkClose(16000, department.calculateTotalPayroll(), "Payroll after transfer");
        support.removeEmployee(3);
        check(technician.getDepartment() == null, "Removal clears employee department");
        technician.setDepartment(null);
        support.removeEmployee(999);
        checkClose(0, support.calculateTotalPayroll(), "Empty department payroll");

        expectFailure(() -> manager.setMonthlySalary(-1), IllegalArgumentException.class,
                "Negative salary rejected");
        expectFailure(() -> technician.setHoursWorked(Double.NaN), IllegalArgumentException.class,
                "NaN hours rejected");
        expectFailure(() -> technician.setHourlyRate(Double.POSITIVE_INFINITY),
                IllegalArgumentException.class, "Infinite rate rejected");
        expectFailure(() -> salesperson.setCommissionRate(1.1), IllegalArgumentException.class,
                "Invalid commission fraction rejected");
        expectFailure(() -> manager.setBonusPercentage(101), IllegalArgumentException.class,
                "Invalid bonus percentage rejected");
        expectFailure(() -> technician.setOvertimeRate(0.5), IllegalArgumentException.class,
                "Invalid overtime multiplier rejected");
        expectFailure(() -> manager.setVacationDays(-1), IllegalArgumentException.class,
                "Negative vacation rejected");
        expectFailure(() -> manager.setName(" "), IllegalArgumentException.class,
                "Blank name rejected");
        expectFailure(() -> manager.setGender(null), NullPointerException.class,
                "Null gender rejected");
        expectFailure(() -> department.addEmployee(null), NullPointerException.class,
                "Null employee rejected");
        expectFailure(() -> salesperson.setId(1), IllegalArgumentException.class,
                "ID changes cannot create duplicates");
        Employee duplicate = new HourlyEmployee(2, "Duplicate ID", Gender.MALE,
                LocalDate.now(), 50, 10, 1.5);
        expectFailure(() -> department.addEmployee(duplicate), IllegalArgumentException.class,
                "Duplicate employee ID rejected");
        expectFailure(() -> department.setEmployees(Arrays.asList(manager, manager)),
                IllegalArgumentException.class, "Duplicate replacement list rejected");
        check(department.getEmployees().size() == 2, "Invalid replacement leaves membership intact");
        expectFailure(() -> department.getEmployees().clear(), UnsupportedOperationException.class,
                "Employee list cannot be modified externally");

        support.setManager(technician);
        check(support.findEmployee(3) == technician, "Manager is added to department");
        department.addEmployee(technician);
        check(support.getManager() == null, "Transferring manager clears previous manager");
        department.addEmployee(technician);
        check(department.getEmployees().size() == 3, "Adding same employee twice is idempotent");
        ArrayList<Employee> replacement = new ArrayList<>(Arrays.asList(manager, salesperson));
        department.setEmployees(replacement);
        replacement.clear();
        check(department.getEmployees().size() == 2 && technician.getDepartment() == null,
                "Replacing list detaches excluded employees and copies input");
        department.removeEmployee(1);
        check(department.getManager() == null && manager.getDepartment() == null,
                "Removing manager clears both relationships");
        department.setEmployees(Arrays.asList(manager, salesperson, technician));
        department.setManager(manager);
        checkClose(20750, department.calculateTotalPayroll(), "Restored sample payroll");
        System.out.println("All " + checks + " checks passed.");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
        checks++;
    }

    private static void checkClose(double expected, double actual, String description) {
        check(Double.isFinite(actual) && Math.abs(expected - actual) < 0.000001,
                description + ": expected " + expected + ", got " + actual);
    }

    private static void expectFailure(Runnable action, Class<? extends RuntimeException> type,
                                      String description) {
        try {
            action.run();
        } catch (RuntimeException exception) {
            check(type.isInstance(exception), description + ": unexpected exception " + exception);
            return;
        }
        throw new AssertionError(description + ": expected an exception");
    }
}
