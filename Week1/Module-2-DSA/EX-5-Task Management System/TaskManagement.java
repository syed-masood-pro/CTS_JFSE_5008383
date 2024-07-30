import java.util.Scanner;

public class TaskManagement  {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean x = true;
        TaskList_SLL taskList = new TaskList_SLL();

        while (x) {
            System.out.println("\n\tTask Management System");
            System.out.println("\t\tEnter Options");
            System.out.println("1. Add Task\n2. Search Task\n3. Traverse Task\n4. Delete Task\n5. Exit");
            int op = sc.nextInt();

            switch (op) {
                case 1: {
                    Task t = getDetails();
                    taskList.addTask(t);
                    break;
                }
                case 2: {
                    System.out.print("Enter TaskId: ");
                    int tid = sc.nextInt();
                    Task t = taskList.searchTask(tid);
                    if (t != null) {
                        System.out.println(t);
                    } else {
                        System.out.println("Task not found");
                    }
                    break;
                }
                case 3: {
                    taskList.traverse();
                    break;
                }
                case 4: {
                    System.out.print("Enter TaskId to Delete: ");
                    int tid = sc.nextInt();
                    boolean deleted = taskList.deleteTask(tid);
                    if (deleted) {
                        System.out.println("Task deleted successfully");
                    } else {
                        System.out.println("Task not found");
                    }
                    break;
                }
                case 5: {
                    x = false;
                    break;
                }
                default: {
                    System.out.println("Enter Valid options");
                    break;
                }
            }
        }
        sc.close();
    }

    public static Task getDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter TaskId: ");
        int tid = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter Task Name: ");
        String tname = sc.nextLine();
        System.out.print("Enter Task Status: ");
        String ts = sc.nextLine();
        return new Task(tid, tname, ts);
    }

    static class Task {
        private int taskId;
        private String taskName;
        private String status;

        public Task(int taskId, String taskName, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.status = status;
        }

        public int getTaskId() {
            return taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "Task ID: " + taskId + ", Task Name: " + taskName + ", Status: " + status;
        }
    }

    static class Node {
        Task task;
        Node next;

        public Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }
    
    static class TaskList_SLL {
        private Node head;

        public TaskList_SLL() {
            this.head = null;
        }

        public void addTask(Task t) {
            Node newNode = new Node(t);
            if (head == null) {
                head = newNode;
            } else {
                Node curr = head;
                while (curr.next != null) {
                    curr = curr.next;
                }
                curr.next = newNode;
            }
        }

        public Task searchTask(int taskId) {
            Node curr = head;
            while (curr != null) {
                if (curr.task.getTaskId() == taskId) {
                    return curr.task;
                }
                curr = curr.next;
            }
            return null;
        }

        public void traverse() {
            Node curr = head;
            while (curr != null) {
                System.out.println(curr.task);
                curr = curr.next;
            }
        }

        public boolean deleteTask(int taskId) {
            if (head == null) {
                return false;
            }
            if (head.task.getTaskId() == taskId) {
                head = head.next;
                return true;
            }
            Node curr = head;
            while (curr.next != null && curr.next.task.getTaskId() != taskId) {
                curr = curr.next;
            }
            if (curr.next == null) {
                return false;
            }
            curr.next = curr.next.next;
            return true;
        }
    }
}
