<%-- 
    Document   : register
    Created on : 13 Jul 2025, 13:15:03
    Author     : rmara
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
        <!-- Link to your CSS file in the css directory -->
        <link rel="stylesheet" type="text/css" href="css/styles.css">
    </head>
    <body>
            <div class="register-container">
                 <h2>Student Registration</h2>
                     <form onsubmit="event.preventDefault(); window.location.href='dashboard.jsp';">
                        <input type="text" placeholder="Name" required>
                        <input type="text" placeholder="Surname" required>
                        <input type="text" placeholder="Student number" required>
                        <input type="text" placeholder="Year" required>
                        <input type="email" placeholder="Email" required>
                        <input type="password" placeholder="Password" required>
                        <button type="submit">Register</button>
                    </form>
             </div>
    </body>
</html>
