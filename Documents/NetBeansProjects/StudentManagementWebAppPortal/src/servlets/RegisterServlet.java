package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import app.ConnectionProvider;
import app.Student;
import java.security.MessageDigest;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get input from form
        String studentNumber = request.getParameter("student_number");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try {
            // Hash password
            String hashedPassword = hashPassword(password);

            // Create student object
            Student student = new Student(studentNumber, name, surname, email, phone, hashedPassword);

            // Save to DB
            try (Connection conn = ConnectionProvider.getConnection()) {

                // Check for existing email
                PreparedStatement check = conn.prepareStatement("SELECT * FROM students WHERE email = ?");
                check.setString(1, student.getEmail());
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    out.println("<h3>Email already exists!</h3>");
                } else {
                    PreparedStatement pst = conn.prepareStatement(
                        "INSERT INTO students (student_number, name, surname, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)"
                    );
                    pst.setString(1, student.getStudentNumber());
                    pst.setString(2, student.getName());
                    pst.setString(3, student.getSurname());
                    pst.setString(4, student.getEmail());
                    pst.setString(5, student.getPhone());
                    pst.setString(6, student.getPassword());
                    pst.executeUpdate();

                    out.println("<h3>Registration successful!</h3>");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }

    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

