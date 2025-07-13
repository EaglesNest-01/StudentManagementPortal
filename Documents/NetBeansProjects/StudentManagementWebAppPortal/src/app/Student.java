package app;

public class Student {
    private String studentNumber;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;

    public Student(String studentNumber, String name, String surname, String email, String phone, String password) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getters
    public String getStudentNumber() { return studentNumber; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }

    // Setters if needed (optional)
}
