# Employee Management System

Name: Mostafa Mahmoud Sayed Shaaban

Student ID: 2300170

## Description

A Java project based on the employee class diagram. It includes monthly, hourly,
and commission employees, and a department that manages employees and calculates
its total payroll.

## How to Run

You need Java JDK 8 or later.

Open the project in your IDE, set `src` as the source folder, and run `Main.java`.

Or run these commands from the project folder in PowerShell:

```powershell
New-Item -ItemType Directory -Path out -Force | Out-Null
javac -d out src/model/*.java src/test/MainTest.java src/Main.java
java -cp out Main
```
