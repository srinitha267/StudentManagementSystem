import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystemSQL {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student_db";
        String username = "root"; 
        String password = "YourPasscode";    

        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to MySQL.");

            while (true) {
                System.out.println("\n==== Student Management Menu ====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Remove Student");
                System.out.println("4. Update Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        String insertQuery = "INSERT INTO students (id, name, course) VALUES (?, ?, ?)";
                        try (PreparedStatement pst = conn.prepareStatement(insertQuery)) {
                            pst.setInt(1, id);
                            pst.setString(2, name);
                            pst.setString(3, course);
                            pst.executeUpdate();
                            System.out.println("Student added.");
                        }
                        break;

                    case 2:
                        String selectQuery = "SELECT * FROM students";
                        try (Statement st = conn.createStatement();
                             ResultSet rs = st.executeQuery(selectQuery)) {
                            System.out.println("\n Student List:");
                            while (rs.next()) {
                                System.out.println("ID: " + rs.getInt("id") +
                                        ", Name: " + rs.getString("name") +
                                        ", Course: " + rs.getString("course"));
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter ID to remove: ");
                        int removeId = sc.nextInt();

                        String deleteQuery = "DELETE FROM students WHERE id = ?";
                        try (PreparedStatement pst = conn.prepareStatement(deleteQuery)) {
                            pst.setInt(1, removeId);
                            int rows = pst.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Student removed.");
                            } else {
                                System.out.println("Student not found.");
                            }
                        }
                        break;

                        case 4:
                        System.out.print("Enter ID of student to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        
                        System.out.print("Enter new Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter new Course: ");
                        String newCourse = sc.nextLine();
                        
                        String updateQuery = "UPDATE students SET name = ?, course = ? WHERE id = ?";
                        try (PreparedStatement pst = conn.prepareStatement(updateQuery)) {
                            pst.setString(1, newName);
                            pst.setString(2, newCourse);
                            pst.setInt(3, updateId);
                            int rows = pst.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Student updated.");
                            } else {
                                System.out.println("Student not found.");
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

