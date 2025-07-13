<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>Student Registration</h2>
    <form action="RegisterServlet" method="post">
        Student Number: <input type="text" name="student_number" required /><br/><br/>
        Name: <input type="text" name="name" required /><br/><br/>
        Surname: <input type="text" name="surname" required /><br/><br/>
        Email: <input type="email" name="email" required /><br/><br/>
        Phone: <input type="text" name="phone" required /><br/><br/>
        Password: <input type="password" name="password" required /><br/><br/>
        <input type="submit" value="Register" />
    </form>
    <br/>
    <a href="index.jsp">Back to Home</a>
</body>
</html>
