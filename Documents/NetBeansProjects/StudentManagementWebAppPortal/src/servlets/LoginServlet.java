/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;
/*
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
*/

import java.io.*;
import java.sql.*;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 *
 * @author Jeremy
 */
@WebServlet("/login") //Servlet path to be used for form action
public class LoginServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.sendRedirect("login.jsp");
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
          // 1. Get data from the form
        String studentId = request.getParameter("studentId");
        String password = request.getParameter("password");

        try {
            // 2. Connect to PostgreSQL
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/StudentInfo",
                "admin", "admin"
            );   
        
         // 3. Query user by email
            String query = "SELECT * FROM students WHERE student_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // 4. Retrieve hashed password from DB
                String hashedPasswordFromDB = rs.getString("password");

                // 5. Hash the input password the same way
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
                String hashedInputPassword = Base64.getEncoder().encodeToString(hashedBytes);

                // 6. Compare passwords    
             if (hashedInputPassword.equals(hashedPasswordFromDB)) {
                    // Successful login
                    HttpSession session = request.getSession();
                    session.setAttribute("student_number", rs.getString("name"));
                    response.sendRedirect("dashboard.jsp");
                } else {
                    // Incorrect password
                    request.setAttribute("error", "Incorrect password.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }

            } else {
                // student_number not found
                request.setAttribute("error", "Student not found.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
             } catch (Exception e)   {
            e.printStackTrace();
            request.setAttribute("error", "Server error occurred.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }//catch implemented in case server error occurs
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
