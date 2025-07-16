 1. Database Setup (PostgreSQL)

First, you need to set up the database where student data will be stored.

1.  Install Software: Download and install PostgreSQL and its management tool, pgAdmin, from the [official website](https://www.postgresql.org/download/). During setup, create and remember a password for the default `postgres` user.
2.  Create Database: Open pgAdmin and connect to your server. In the browser tree, right-click Databases \> Create\> Database and name it "StudentInfo".
3.  Create Table: Select the `StudentInfo` database, open the Query Tool, and run the following SQL script to create the "students" table:
    sql
    CREATE TABLE students (
        student_number TEXT PRIMARY KEY,
        name TEXT NOT NULL,
        surname TEXT NOT NULL,
        email TEXT UNIQUE NOT NULL,
        phone TEXT UNIQUE NOT NULL,
        password TEXT NOT NULL
    );

2. Java Backend Setup

The Java backend handles user registration, data validation, and database interaction.

 Key Dependency

Before running the project, make sure you have the PostgreSQL JDBC Driver JAR file in your project's build path (e.g., in the `/WEB-INF/lib` folder for a web application).

 Core Components & Configuration

The backend is built from several key Java classes:

ConnectionProvider.java (Database Utility)
    This utility class manages the connection to your PostgreSQL database.
    ACTION REQUIRED: You must edit this file and replace "your_password" with the actual password you set for the "postgres" user.

    java
    // Inside ConnectionProvider.java
    conn = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/StudentInfo",
        "postgres",        // your DB username
        "your_password"    // <-- UPDATE THIS
    );
    

    Student.java (Data Model)
    A simple Java object (POJO) that acts as a container to hold the data for a single student (student number, name, email, etc.). It's used to pass data cleanly between the servlet and other layers.

    RegisterServlet.java (Controller)
    This is the main engine for the registration process. It's responsible for:

    1.  Receiving data from the registration HTML form.
    2.  Hashing the user's password using SHA-256 for security.
    3.  Validating that the student number, email, and phone number are unique.
    4.  Using `ConnectionProvider` to talk to the database.
    5.  Inserting the new student record on success.
    6.  Creating an `HttpSession` to log the user in.
    7.  Redirecting the user to a dashboard on success or back to the registration page with an error on failure.

    web.xml (Servlet Mapping)
    This configuration file tells the web server how to handle incoming requests. It maps the URL pattern `/RegisterServlet` to the `servlets.RegisterServlet` class, ensuring that any requests to that URL are processed by your servlet.

    xml
    <servlet-mapping>
        <servlet-name>RegisterServlet</servlet-name>
        <url-pattern>/RegisterServlet</url-pattern>
    </servlet-mapping>


    LoginServlet

A LoginServlet was developed to handle and  secure user authentication using both the student ID and password. It is located within the source folder of the project alongside RegisterServlet. 

When a user attempts to log in, the servlet tries to receive the corresponding user record from the PostgreSQL database and hashes the entered password using the SHA-256 algorithm that is encoded with hexidecimal encoding. This hashed value is then compared to the stored hashed password from the database, and if the credentials are valid, an HttpSession is created and the user will be redirected to the dashboard.jsp page. 

However, if the login fails, a clear error message will appear, whether or not the student Id is incorrect, the password is incorrect or both. Additionally, the session management ensures that access to the dashboard is restricted to authenticated users only.

We linked the servlet to the login page form for accurate authentication.
 
