package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import app.ConnectionProvider;
import java.security.MessageDigest;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String student_number = request.getParameter("student_number");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try (Connection conn = ConnectionProvider.getConnection()) {
            String hashedPassword = hashPassword(password);

            // Check for duplicate email
            PreparedStatement check = conn.prepareStatement("SELECT * FROM students WHERE email = ?");
            check.setString(1, email);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                out.println("<h3>Email already exists!</h3>");
            } else {
                PreparedStatement pst = conn.prepareStatement(
                    "INSERT INTO students (student_number, name, surname, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)"
                );
                pst.setString(1, student_number);
                pst.setString(2, name);
                pst.setString(3, surname);
                pst.setString(4, email);
                pst.setString(5, phone);
                pst.setString(6, hashedPassword);
                pst.executeUpdate();

                out.println("<h3>Registration successful!</h3>");
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

