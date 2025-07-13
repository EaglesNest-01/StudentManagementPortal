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

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String studentNumber = request.getParameter("student_number");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try {
            String hashedPassword = hashPassword(password);
            Student student = new Student(studentNumber, name, surname, email, phone, hashedPassword);

            try (Connection conn = ConnectionProvider.getConnection()) {

                if (exists(conn, "student_number", student.getStudentNumber())) {
                    sendBackWithError(request, response, "Student number already exists.", student);
                    return;
                }

                if (exists(conn, "email", student.getEmail())) {
                    sendBackWithError(request, response, "Email already exists.", student);
                    return;
                }

                if (exists(conn, "phone", student.getPhone())) {
                    sendBackWithError(request, response, "Phone number already exists.", student);
                    return;
                }

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

                HttpSession session = request.getSession();
                session.setAttribute("studentName", student.getName());

                response.sendRedirect("dashboard.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private boolean exists(Connection conn, String field, String value) throws SQLException {
        String query = "SELECT 1 FROM students WHERE " + field + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void sendBackWithError(HttpServletRequest request, HttpServletResponse response, String errorMsg, Student student)
            throws ServletException, IOException {

        request.setAttribute("error", errorMsg);
        request.setAttribute("student_number", student.getStudentNumber());
        request.setAttribute("name", student.getName());
        request.setAttribute("surname", student.getSurname());
        request.setAttribute("email", student.getEmail());
        request.setAttribute("phone", student.getPhone());

        request.getRequestDispatcher("register.jsp").forward(request, response);
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