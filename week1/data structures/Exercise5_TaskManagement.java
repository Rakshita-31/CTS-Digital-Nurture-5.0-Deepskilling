/*
 * Exercise 5: Task Management System
 *
 * Linked list types:
 * - Singly Linked List: each node points only to the next node. Traversal
 *   is one-directional (head to tail).
 * - Doubly Linked List: each node points to both the next and previous node,
 *   allowing traversal in either direction at the cost of extra memory per
 *   node for the additional pointer.
 *
 * This exercise uses a singly linked list since tasks are mainly added and
 * processed in one direction (e.g., added at the end, worked through in order).
 */

class JobTask {
    int taskId;
    String taskName;
    String status;

    JobTask(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "JobTask#" + taskId + " [" + taskName + " - " + status + "]";
    }
}

// Each node wraps a JobTask and a reference to the next node in the list
class JobTaskNode {
    JobTask task;
    JobTaskNode next;

    JobTaskNode(JobTask task) {
        this.task = task;
        this.next = null;
    }
}

public class Exercise5_TaskManagement {

    private JobTaskNode head;

    // O(n) - must walk to the end of the list since we don't keep a tail pointer
    // (could be made O(1) by maintaining a tail reference if adds are frequent)
    public void addTask(JobTask task) {
        JobTaskNode newNode = new JobTaskNode(task);
        if (head == null) {
            head = newNode;
            return;
        }
        JobTaskNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    // O(n) - worst case checks every node before finding (or missing) a match
    public JobTask searchTask(int taskId) {
        JobTaskNode current = head;
        while (current != null) {
            if (current.task.taskId == taskId) {
                return current.task;
            }
            current = current.next;
        }
        return null;
    }

    // O(n) - visits each node exactly once, following next pointers
    public void traverseTasks() {
        JobTaskNode current = head;
        while (current != null) {
            System.out.println(current.task);
            current = current.next;
        }
    }

    // O(n) - locating the node to remove requires walking from head
    public void deleteTask(int taskId) {
        if (head == null) return;

        if (head.task.taskId == taskId) {
            head = head.next; // removing the head is just a pointer move
            return;
        }

        JobTaskNode current = head;
        while (current.next != null && current.next.task.taskId != taskId) {
            current = current.next;
        }

        if (current.next == null) {
            System.out.println("JobTask not found: " + taskId);
            return;
        }

        current.next = current.next.next; // skip over the node being removed
    }

    public static void main(String[] args) {
        Exercise5_TaskManagement manager = new Exercise5_TaskManagement();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n--- Task Menu ---");
            System.out.println("1. Add Task");
            System.out.println("2. Search Task");
            System.out.println("3. Traverse (View All)");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    System.out.print("Task ID: ");
                    int taskId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Task name: ");
                    String taskName = scanner.nextLine().trim();
                    System.out.print("Status: ");
                    String status = scanner.nextLine().trim();
                    manager.addTask(new JobTask(taskId, taskName, status));
                    break;

                case 2:
                    System.out.print("Enter task ID to search: ");
                    int searchId = Integer.parseInt(scanner.nextLine().trim());
                    JobTask found = manager.searchTask(searchId);
                    System.out.println(found == null ? "Task not found." : found);
                    break;

                case 3:
                    System.out.println("All tasks:");
                    manager.traverseTasks();
                    break;

                case 4:
                    System.out.print("Enter task ID to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine().trim());
                    manager.deleteTask(deleteId);
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
 * - Add: O(n) here since we walk to the tail each time (no tail pointer kept).
 *   Adding a tail reference would bring this down to O(1).
 * - Search: O(n), since a singly linked list has no random access - we must
 *   follow next pointers from the head.
 * - Traverse: O(n), inherent to visiting every node once.
 * - Delete: O(n) to locate the node, but the removal itself is O(1) once found
 *   (just relinking pointers, no shifting like an array would need).
 * - Advantages over arrays: linked lists grow and shrink dynamically without
 *   needing to resize or copy the whole structure, and deletion doesn't
 *   require shifting elements - just updating one pointer. This makes them a
 *   good fit for task lists where the number of tasks is unpredictable and
 *   items are frequently added or removed.
 */
