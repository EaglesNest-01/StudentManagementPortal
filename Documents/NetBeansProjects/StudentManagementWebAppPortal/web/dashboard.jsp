<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WELCOME!</title>
        <link rel="stylesheet" type="text/css" href="css/styles.css">
    </head>
    <body>
        <div class="welcome-container">
            <h1>Welcome to Student Wellness Resources</h1>

            <%
                String studentName = (String) session.getAttribute("studentName");
                if (studentName != null) {
            %>
                <h2><%= studentName %></h2>
            <%
                }
            %>

            <p>Your well-being is our priority. Explore services designed to support your mental, physical, and academic health.</p>

            <div class="welcome-buttons">
                <a href="wellnessresources.jsp" class="button">Explore Wellness Services</a>
                <a href="register.jsp" class="button">Register for Support</a>
            </div>
        </div>
    </body>
</html>

