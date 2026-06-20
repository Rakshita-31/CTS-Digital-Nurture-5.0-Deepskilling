/*
 * Exercise 4: Employee Management System
 *
 * Array representation in memory:
 * An array stores its elements in contiguous memory blocks, all of the same
 * size. Because of this, the address of any element can be calculated
 * directly as (base address + index * element size), which is why array
 * access by index is O(1) - there's no need to walk through prior elements
 * to find one. The tradeoff is that the size is fixed at creation time, so
 * growing or shrinking the collection means creating a new array.
 */

class StaffMember {
    int employeeId;
    String name;
    String position;
    double salary;

    StaffMember(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "StaffMember[id=" + employeeId + ", name=" + name +
               ", position=" + position + ", salary=" + salary + "]";
    }
}

public class Exercise4_EmployeeManagement {

    private StaffMember[] employees;
    private int count; // tracks how many slots are actually filled

    public Exercise4_EmployeeManagement(int capacity) {
        employees = new StaffMember[capacity];
        count = 0;
    }

    // O(1) - direct insertion at the next free index, as long as there's room
    public void addEmployee(StaffMember e) {
        if (count == employees.length) {
            System.out.println("Array full. Cannot add more employees without resizing.");
            return;
        }
        employees[count] = e;
        count++;
    }

    // O(n) - array isn't sorted by ID, so every element may need checking
    public StaffMember searchEmployee(int employeeId) {
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == employeeId) {
                return employees[i];
            }
        }
        return null;
    }

    // O(n) - visits every filled slot exactly once
    public void traverseEmployees() {
        for (int i = 0; i < count; i++) {
            System.out.println(employees[i]);
        }
    }

    // O(n) - must locate the employee first, then shift later elements left
    // to close the gap and keep the array compact
    public void deleteEmployee(int employeeId) {
        int indexToRemove = -1;
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == employeeId) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            System.out.println("StaffMember not found: " + employeeId);
            return;
        }

        for (int i = indexToRemove; i < count - 1; i++) {
            employees[i] = employees[i + 1];
        }
        employees[count - 1] = null; // clear the now-unused last slot
        count--;
    }

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.print("Enter maximum number of employees the array should hold: ");
        int capacity = Integer.parseInt(scanner.nextLine().trim());
        Exercise4_EmployeeManagement system = new Exercise4_EmployeeManagement(capacity);

        while (true) {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Traverse (View All)");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    System.out.print("Employee ID: ");
                    int empId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Position: ");
                    String position = scanner.nextLine().trim();
                    System.out.print("Salary: ");
                    double salary = Double.parseDouble(scanner.nextLine().trim());
                    system.addEmployee(new StaffMember(empId, name, position, salary));
                    break;

                case 2:
                    System.out.print("Enter employee ID to search: ");
                    int searchId = Integer.parseInt(scanner.nextLine().trim());
                    StaffMember found = system.searchEmployee(searchId);
                    System.out.println(found == null ? "Employee not found." : found);
                    break;

                case 3:
                    System.out.println("All employees:");
                    system.traverseEmployees();
                    break;

                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine().trim());
                    system.deleteEmployee(deleteId);
                    break;

                case 5:
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }
}

/*
 * Analysis:
 * - Add: O(1) when appending to the next open slot, since the index is known
 *   directly and no shifting is needed.
 * - Search: O(n), since the array isn't indexed by employeeId - we have to
 *   check elements one at a time.
 * - Traverse: O(n), since visiting every employee inherently requires n steps.
 * - Delete: O(n), because after finding the element, all elements after it
 *   must shift left by one position to avoid leaving a gap.
 * - Limitations: arrays have a fixed size, so growth requires creating a new,
 *   larger array and copying everything over - costly if done often. Arrays
 *   are best when the number of employees is relatively stable and known in
 *   advance, or when fast indexed access matters more than frequent inserts
 *   and deletes. For a frequently changing roster, a structure like a
 *   HashMap (keyed by employeeId) or a LinkedList would handle insert/delete
 *   more efficiently.
 */
