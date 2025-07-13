<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Registration</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    
    <div class="register-container">
        <%-- Show error if present --%>
        <% if (request.getAttribute("error") != null) { %>
            <div style="color:red; font-weight:bold;">
            <%= request.getAttribute("error") %>
            </div>
        <% } %>
        <h2>Student Registration</h2>
        
        <form action="RegisterServlet" method="post">
    <input type="text" name="student_number" placeholder="Student Number" 
        value="<%= request.getAttribute("student_number") != null ? request.getAttribute("student_number") : "" %>" required />

    <input type="text" name="name" placeholder="Name" 
        value="<%= request.getAttribute("name") != null ? request.getAttribute("name") : "" %>" required />

    <input type="text" name="surname" placeholder="Surname" 
        value="<%= request.getAttribute("surname") != null ? request.getAttribute("surname") : "" %>" required />

    <input type="email" name="email" placeholder="Email" 
        value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required />

    <input type="text" name="phone" placeholder="Phone" 
        value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>" required />

    <input type="password" name="password" placeholder="Password" required />

    <button type="submit">Register</button>
</form>
    </div>
</body>
</html>
