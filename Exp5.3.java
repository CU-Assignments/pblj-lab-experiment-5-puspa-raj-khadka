Menu-based Java application that allows you to add employee details, display all employees, and exit. The employee details will be stored in a file, and the program will
read the file to display the stored employee information.

Steps:
1. Create an Employee class with fields like name, id, designation, and salary.
2. Create a menu with three options:
Add an Employee
Display All Employees
Exit
3. Store Employee Data in a File: Serialize the employee objects and store them in a file.
4. Read Employee Data from the File: Deserialize the employee objects from the file and display the details.
5. Handle Exceptions: Handle file I/O exceptions.



---Implementation
  
Employee Class: This class contains details like name, id, designation, and salary. It implements Serializable to allow serialization of Employee objects.
addEmployee(): This method takes input from the user for an employee's details, creates an Employee object, and saves it to a file using ObjectOutputStream.
saveEmployeeToFile(): This method appends employee details to a file. The file is opened in append mode (true parameter in FileOutputStream).
displayAllEmployees(): This method reads all employee objects from the file and prints their details.
readEmployeesFromFile(): This method reads the employee objects from the file using ObjectInputStream and stores them in a list. 
The loop continues until the end of the file is reached (IOFException).




Test Cases:

Test Case 1: Add a new employee and display all employees.
Steps: Select option 1 to add a new employee, then select option 2 to display all employees.
Input:
Employee Name: John Doe
Employee ID: 101
Designation: Software Engineer
Salary: 50000
  
Expected Output:
Employee added successfully!
Employee ID: 101, Name: John Doe, Designation: Software Engineer, Salary: 50000.0

Test Case 2: Try adding multiple employees and display all of them.
Steps: Add multiple employees (using option 1) and then display all employees (using option 2).
Expected Output:
Employee added successfully!
Employee ID: 101, Name: John Doe, Designation: Software Engineer, Salary: 50000.0
Employee added successfully!
Employee ID: 102, Name: Jane Smith, Designation: Manager, Salary: 75000.0




  import java.io.*;
import java.util.*;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String designation;
    private double salary;

    public Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee ID: " + id + ", Name: " + name + ", Designation: " + designation + ", Salary: " + salary;
    }
}

public class EmployeeManagement {
    private static final String FILE_NAME = "employees.ser";

    public static void addEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Designation: ");
        String designation = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();

        Employee employee = new Employee(id, name, designation, salary);
        saveEmployeeToFile(employee);
        System.out.println("Employee added successfully!\n");
    }

    private static void saveEmployeeToFile(Employee employee) {
        List<Employee> employees = readEmployeesFromFile();
        employees.add(employee);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(employees);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void displayAllEmployees() {
        List<Employee> employees = readEmployeesFromFile();
        if (employees.isEmpty()) {
            System.out.println("No employees found.\n");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
            System.out.println();
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Employee> readEmployeesFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Employee>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> displayAllEmployees();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.\n");
            }
        }
    }
}
