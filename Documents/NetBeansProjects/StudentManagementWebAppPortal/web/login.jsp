<%-- 
    Document   : login
    Created on : 13 Jul 2025, 13:20:43
    Author     : rmara
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        
    </head>
    <body>
        <div class="login-container">
            <h2>Student Wellness Login</h2>
            <%-- Display error message if there is one --%>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p style="color: red;"><%= error %></p>
<%
    }
%>
            <form action="login" method="post">
                <input type="text" placeholder="Student ID" name ="studentId" required>
                <input type="password" placeholder="Password" name="password" required>
                <button type="submit">Login</button>
            </form>
            <div class="note">
                Access your personalized wellness resources.
            </div>
        </div>
    </body>
</html>
