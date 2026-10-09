# Employee Management System

Name: Mostafa Mahmoud Sayed Shaaban

Student ID: 2300170

## Description

A Java project based on the employee class diagram. It includes monthly, hourly,
and commission employees, and a department that manages employees and calculates
its total payroll.

## Classes

- `Employee`: An abstract class containing the information shared by all employees:
  ID, name, gender, hire date, and department. Each employee type has its own
  implementation of `calculateSalary()`.
- `MonthlyEmployee`: Stores the monthly salary, vacation days, bonus percentage,
  and health insurance status. Its salary includes the monthly salary and bonus.
  It also calculates one additional vacation day for every five completed years
  of service.
- `HourlyEmployee`: Calculates salary from the hourly rate and hours worked.
  Hours above 40 are paid using the overtime multiplier.
- `CommissionEmployee`: Calculates commission from the sales amount and
  commission rate, then adds it to the base salary.
- `Department`: Stores the department information, manager, and employee list.
  It provides methods to add, remove, find, and print employees, and calculate
  the total payroll.
- `Gender`: An enum containing `MALE` and `FEMALE`.

## Implementation

The three employee types inherit from `Employee`. Their attributes are private
and accessed through getters and setters. The department calculates total payroll
by calling `calculateSalary()` for each employee, using the calculation defined
by that employee's type.

An employee can belong to one department at a time. Transferring an employee
updates both departments, and removing an employee clears their department
reference. The manager is included in the employee list and counted once in payroll.
Setters also check for invalid values, such as negative salaries or hours worked.

## Demo and Testing

`Main.java` calls `MainTest`, which creates a monthly employee, an hourly employee,
a commission employee, and a department using sample data. It prints their details
and the total payroll, which is `20750.00` for the sample.

The demo includes 41 checks covering salary calculations, additional vacation,
employee transfers, manager removal, and invalid input. All checks passed.
