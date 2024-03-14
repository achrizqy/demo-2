import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class LibrarySystem {
    private static Map<Integer, Map<String, String>> bookList;
    private static Map<String, Map<String, String>> userStudent;

    static {
        bookList = new HashMap<>();
        bookList.put(1, Map.of("id", "388c-e681-9152", "title", "Book 1", "author", "Author 1", "category", "Sejarah", "stock", "4"));
        bookList.put(2, Map.of("id", "ed90-be30-5cdb", "title", "Book 2", "author", "Author 2", "category", "Sejarah", "stock", "6"));
        bookList.put(3, Map.of("id", "d95e-0c4a-9523", "title", "Book 3", "author", "Author 3", "category", "Fiksi", "stock", "3"));

        userStudent = new HashMap<>();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 3) {
            menu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    Student student = new Student();
                    student.borrowOrLogout();
                    break;
                case 2:
                    Admin admin = new Admin(userStudent);
                    String username, password;
                    scanner.nextLine(); // Consume newline
                    while (true) {
                        System.out.print("Enter your username (admin): ");
                        username = scanner.nextLine();
                        System.out.print("Enter your password (admin): ");
                        password = scanner.nextLine();
                        if (!username.equals("admin") || !password.equals("admin")) {
                            System.out.println("Invalid credentials for admin.");
                        } else {
                            break;
                        }
                    }
                    int adminOption = 0;
                    while (adminOption != 3) {
                        adminMenu();
                        adminOption = scanner.nextInt();
                        switch (adminOption) {
                            case 1:
                                admin.addStudent();
                                break;
                            case 2:
                                admin.displayStudents();
                                break;
                            case 3:
                                System.out.println("Logging out from admin account.");
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Thank you. Exiting program.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void menu() {
        System.out.println("");
        System.out.println("   Library System    ");
        System.out.println("1. Login as Student");
        System.out.println("2. Login as Admin");
        System.out.println("3. Exit");
        System.out.print("Choose option (1-3): ");
    }

    private static void adminMenu() {
        System.out.println("");
        System.out.println("   Admin Menu   ");
        System.out.println("1. Add Student");
        System.out.println("2. Display Registered Students");
        System.out.println("3. Logout");
        System.out.print("Choose option (1-3): ");
    }

    public static Map<Integer, Map<String, String>> getBookList() {
        return bookList;
    }

    public static Map<String, Map<String, String>> getUserStudent() {
        return userStudent;
    }
}

class Student {
    public void borrowOrLogout() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (option != 3) {
            studentMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    borrowBook();
                    break;
                case 3:
                    System.out.println("Kembali ke menu awal.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void studentMenu() {
        System.out.println("");
        System.out.println("   Student Menu    ");
        System.out.println("1. Pinjam Buku");
        System.out.println("3. Kembali ke Menu Awal");
        System.out.print("Input: ");
    }

    private void borrowBook() {
        Map<Integer, Map<String, String>> bookList = LibrarySystem.getBookList();
        System.out.println("|| No. || Id Buku || Nama Buku || Author || Category || Stock ||");
        for (Map.Entry<Integer, Map<String, String>> entry : bookList.entrySet()) {
            Map<String, String> book = entry.getValue();
            System.out.println("|| " + entry.getKey() + " || " + book.get("id") + " || " + book.get("title") + " || " + book.get("author") + " || " + book.get("category") + " || " + book.get("stock") + " ||");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input Id buku yang ingin dipinjam (input 3 untuk back): ");
        int bookId = scanner.nextInt();
        if (bookId == 3) {
            return;
        }
        Map<String, String> selectedBook = bookList.get(bookId);
        if (selectedBook == null) {
            System.out.println("Invalid book id.");
            return;
        }
        int stock = Integer.parseInt(selectedBook.get("stock"));
        if (stock <= 0) {
            System.out.println("Buku tidak tersedia.");
        } else {
            stock--;
            selectedBook.put("stock", String.valueOf(stock));
            System.out.println("Buku berhasil dipinjam.");
        }
    }
}

class Admin {
    private Map<String, Map<String, String>> userStudent;

    Admin(Map<String, Map<String, String>> userStudent) {
        this.userStudent = userStudent;
    }

    public void addStudent() {
        Scanner scanner = new Scanner(System.in);
        String name, nim, faculty, program;
        while (true) {
            System.out.print("Enter student name: ");
            name = scanner.nextLine();
            System.out.print("Enter student NIM: ");
            nim = scanner.nextLine();
            if (nim.length() != 15) {
                System.out.println("NIM must be 15 digits.");
                continue;
            }
            System.out.print("Enter student faculty: ");
            faculty = scanner.nextLine();
            System.out.print("Enter student program: ");
            program = scanner.nextLine();
            break;
        }
        userStudent.put(nim, Map.of("name", name, "faculty", faculty, "program", program));
        System.out.println("Student successfully registered.");
    }

    public void displayStudents() {
        System.out.println("List of Registered Students:");
        for (Map.Entry<String, Map<String, String>> entry : userStudent.entrySet()) {
            String nim = entry.getKey();
            Map<String, String> studentInfo = entry.getValue();
            System.out.println("Name: " + studentInfo.get("name") + ", Faculty: " + studentInfo.get("faculty") + ", NIM: " + nim + ", Program: " + studentInfo.get("program"));
        }
    }
}
