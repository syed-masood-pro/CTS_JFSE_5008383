import java.util.Scanner;

public class EmployeeManagement  {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmpManageSystem ems = new EmpManageSystem(10);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Traverse Employees");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // Add Employee
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Employee Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Employee Position: ");
                    String position = sc.nextLine();
                    System.out.print("Enter Employee Salary: ");
                    double salary = sc.nextDouble();
                    ems.addEmployee(new Employee(id, name, position, salary));
                    System.out.println("Employee added successfully!");
                    break;

                case 2:
                    System.out.print("Enter Employee ID to search: ");
                    int searchId = sc.nextInt();
                    Employee emp = ems.searchEmployee(searchId);
                    if (emp != null) {
                        System.out.println(emp);
                    } else {
                        System.out.println("Employee not found");
                    }
                    break;

                case 3:
                    System.out.println("All Employees:");
                    ems.traverseEmployees();
                    break;

                case 4:
                    System.out.print("Enter Employee ID to delete: ");
                    int deleteId = sc.nextInt();
                    boolean deleted = ems.deleteEmployee(deleteId);
                    if (deleted) {
                        System.out.println("Employee deleted successfully");
                    } else {
                        System.out.println("Employee not found");
                    }
                    break;

                case 5:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }

    // Nested Employee class
    static class Employee {
        private int empId;
        private String name;
        private String pos;
        private double salary;

        public Employee(int empId, String name, String pos, double salary) {
            this.empId = empId;
            this.name = name;
            this.pos = pos;
            this.salary = salary;
        }

        public int getEmployeeId() {
            return empId;
        }

        public String getName() {
            return name;
        }

        public String getPosition() {
            return pos;
        }

        public double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "Employee ID: " + empId + ", Name: " + name + ", Position: " + pos + ", Salary: " + salary;
        }
    }

    // Nested EmpManageSystem class
    static class EmpManageSystem {
        private Employee[] employees;
        private int size;

        public EmpManageSystem(int capacity) {
            employees = new Employee[capacity];
            size = 0;
        }

        public boolean addEmployee(Employee employee) {
            if (size >= employees.length) {
                return false; // if the array is full
            }
            employees[size++] = employee;
            return true;
        }

        public Employee searchEmployee(int employeeId) {
            for (int i = 0; i < size; i++) {
                if (employees[i].getEmployeeId() == employeeId) {
                    return employees[i];
                }
            }
            return null;
        }

        public void traverseEmployees() {
            for (int i = 0; i < size; i++) {
                System.out.println(employees[i]);
            }
        }

        public boolean deleteEmployee(int employeeId) {
            for (int i = 0; i < size; i++) {
                if (employees[i].getEmployeeId() == employeeId) {
                    for (int j = i; j < size - 1; j++) {
                        employees[j] = employees[j + 1];
                    }
                    employees[--size] = null;
                    return true;
                }
            }
            return false; // if the emp not found
        }
    }
}
