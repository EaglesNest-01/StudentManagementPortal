<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    // Redirect if session is missing or student is not logged in
    if (session == null || session.getAttribute("studentName") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String studentName = (String) session.getAttribute("studentName");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome</title>
    <br>
        <link rel="stylesheet" type="text/css" href="css/styles.css">
    </head>
    <body>
        <div class="welcome-container">
            <h1>Welcome</h1>
            <h1><%= studentName %></h1>

            <form action="index.jsp" method="get">
                <button type="submit">Logout</button>
            </form>
        </div>
    </body>
</html>
