# Employee Management System

**Full Name:** Mostafa Mahmoud Sayed Shaaban

**Student ID:** 2300170

**Repository:** https://github.com/mustafamahmoudappoynt/EmployeeManagementSystem

## Description

A Java console project implementing the assignment's employee class diagram.
The abstract `Employee` class stores common information. `CommissionEmployee`,
`MonthlyEmployee`, and `HourlyEmployee` inherit from it and override
`calculateSalary()`. `Gender` contains `MALE` and `FEMALE`.
`Department` manages employees, a manager, employee lookup, removal, printing,
and total payroll through polymorphism. All diagram attributes are private,
with public constructors, getters, setters, and the specified methods.

## Structure

```text
EmployeeManagementSystem/
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── Employee.java
│   │   ├── CommissionEmployee.java
│   │   ├── MonthlyEmployee.java
│   │   ├── HourlyEmployee.java
│   │   ├── Department.java
│   │   └── Gender.java
│   └── test/
│       └── MainTest.java
├── .gitignore
└── README.md
```

## Run

Install a JDK (Java 8 or later) and ensure `java` and `javac` are on your PATH.
From the project folder in PowerShell, create the output folder, compile, and run:

```powershell
New-Item -ItemType Directory -Path out -Force | Out-Null
javac -d out src/model/*.java src/test/MainTest.java src/Main.java
java -cp out Main
```

Run the demo and checks directly using:

```shell
java -cp out test.MainTest
```

In an IDE, mark `src` as the source folder and run `Main` or `test.MainTest`.
No external dependencies or build tools are required.

The sample includes one employee of each type and prints a total payroll of
`20750.00`. It then checks salary boundaries, vacation anniversaries, validation,
employee transfers, manager removal, and protection of department membership.
Failed checks throw `AssertionError`; successful execution prints the check count.

Verified with a Java 8 JDK using `javac -Xlint:all`: compilation produced no
warnings, and all 41 checks passed.

## Assumptions for unspecified behavior

The diagram gives method names but does not define calculation policies:

- Commission salary = base salary + sales amount × commission rate.
  `commissionRate` is a fraction: `0.05` means 5%.
- Monthly salary = monthly salary + monthly salary × bonus percentage / 100.
  `bonusPercentage` uses percentage points: `10` means 10%.
- Hourly salary uses regular pay for the first 40 hours of the supplied period.
  Remaining hours earn hourly rate × `overtimeRate`; `1.5` means time-and-a-half.
- Additional vacation is one day per five completed years since the hire date,
  measured using the current date. It is returned separately from `vacationDays`.
- Health insurance is stored as information; no unspecified deduction is applied.
- Total payroll sums each employee's supplied salary period. A real payroll
  system would align hourly and monthly periods before combining them.

Each employee belongs to at most one department. Adding or assigning an employee
updates both sides and transfers them out of any previous department. A manager
is a member and is counted once in payroll. Removing or transferring the manager
clears that department's manager reference. Removing an employee preserves the
Java object, which can be reassigned. Java models the diagram's ownership through
department membership; enum constants are shared immutable values.

Department IDs and employee IDs must be positive; employee IDs must be unique
within a department. Names must be nonblank. Monetary inputs and hours must be
finite and nonnegative. Commission rates are 0–1, bonus percentages are 0–100,
and overtime multipliers are at least 1. Hire dates may be future dates; these
produce zero additional vacation. Department employee lists are copied on input
and exposed as unmodifiable snapshots.

The hourly constructor in the diagram lists `hireDate` twice. This implementation
accepts it once in the common employee parameter group. No interfaces are shown
in the supplied diagram.

## GitHub submission

Push the source and README to the public repository above. Build output is ignored.
Submit your full name, student ID, and repository URL through the assignment's
Google Form: https://forms.gle/ZzB92XLEKaUrzQBM7.

Review and understand the implementation and its documented assumptions before
submission, in accordance with the assignment's originality requirements.
