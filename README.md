# StaffSphere
# 👨‍💼 Employee Management System

A desktop-based **Employee Management System** built using **Java Swing, JDBC, MySQL, and Maven**. The application provides a simple and user-friendly interface for managing employee information, attendance records, and user authentication.

This project was developed to demonstrate the practical implementation of **Java GUI development, database connectivity, CRUD operations, authentication, and relational database management**.

---

## ✨ Features

### 🔐 User Authentication

* Login system for existing users
* New account registration
* Login ID restricted to **exactly 5 digits**
* Password confirmation during account creation
* Duplicate Login ID prevention
* Logout functionality
* Database-backed authentication

### 👤 Employee Management

* Add new employee records
* View employee details
* Search employees using Employee ID
* Update employee information
* Store important employee details such as:

  * Employee ID
  * Name
  * Address
  * Date of Birth
  * Email
  * Phone
  * Job Post
  * Aadhar Number

The application stores these employee fields in the `employee` table.

### 📅 Attendance Management

* Select employees from the database
* Record first-half attendance
* Record second-half attendance
* Store attendance date
* View attendance records
* Search attendance records for a particular employee

The attendance module connects employee records with attendance data using Employee ID.

### 🖥️ Desktop GUI

* Built with Java Swing
* Multiple dedicated windows for different operations
* Menu-based navigation
* Simple and intuitive interface
* Custom backgrounds and UI assets

---

## 🛠️ Technologies Used

| Technology      | Purpose                             |
| --------------- | ----------------------------------- |
| ☕ Java          | Core application development        |
| 🖼️ Java Swing  | Graphical User Interface            |
| 🗄️ MySQL       | Database management                 |
| 🔌 JDBC         | Java–MySQL connectivity             |
| 📦 Maven        | Dependency and project management   |
| 💻 NetBeans     | Development environment             |
| 🧩 Git & GitHub | Version control and project hosting |

---

## 🏗️ Project Architecture

```text
Employee Management System
│
├── Authentication
│   ├── Login
│   └── Account Registration
│
├── Employee Management
│   ├── Add Employee
│   ├── View Employee
│   └── Update Employee
│
├── Attendance Management
│   ├── Take Attendance
│   ├── View Attendance
│   └── Search Employee Attendance
│
└── Database
    ├── logindata
    ├── employee
    └── attendance
```

---

## 📂 Project Structure

```text
EmployeeManagementNetBeans/
│
├── pom.xml
├── database_setup.sql
├── README.md
│
└── src/
    └── main/
        └── java/
            └── Employee_Management/
                │
                ├── Main.java
                ├── LoginPage.java
                ├── RegisterPage.java
                ├── ConnectionClass.java
                ├── HomePage.java
                │
                ├── Add_Employee.java
                ├── View_Employee.java
                ├── View_Employee_Data.java
                ├── Update_Details_Data.java
                │
                ├── Employee_Attendance.java
                ├── View_Attendance.java
                ├── View_Attendance_single.java
                │
                └── ResourceUtil.java
```

---

# 🗄️ Database Setup

The application uses a MySQL database named:

```text
employee_management
```

The application connects to MySQL using:

```text
Host:     localhost
Port:     3306
Username: root
Database: employee_management
```

## 1. Create the Database

Open **MySQL Workbench** and execute:

```sql
CREATE DATABASE IF NOT EXISTS employee_management;

USE employee_management;
```

## 2. Create the Login Table

```sql
CREATE TABLE IF NOT EXISTS logindata (
    username CHAR(5) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);
```

## 3. Create the Employee Table

```sql
CREATE TABLE IF NOT EXISTS employee (
    Eid VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    Address VARCHAR(255),
    Dob VARCHAR(30),
    Email VARCHAR(100),
    Phone VARCHAR(20),
    Post VARCHAR(100),
    Aadhar VARCHAR(20)
);
```

## 4. Create the Attendance Table

```sql
CREATE TABLE IF NOT EXISTS attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Eid VARCHAR(20) NOT NULL,
    name VARCHAR(100),
    email VARCHAR(100),
    first_half VARCHAR(20),
    second_half VARCHAR(20),
    date DATE,
    FOREIGN KEY (Eid) REFERENCES employee(Eid)
);
```

---

# 🔌 Database Connection

The application uses **JDBC** to communicate with MySQL.

The connection is handled through:

```text
ConnectionClass.java
```

Example configuration:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/employee_management"
        + "?useSSL=false"
        + "&serverTimezone=UTC"
        + "&allowPublicKeyRetrieval=true";

private static final String USER = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";
```

> ⚠️ **Security Note:** Never commit your real database password to a public GitHub repository. Replace the password with an environment variable or configuration file that is excluded using `.gitignore`.

---

# 🚀 How to Run

## Prerequisites

Make sure you have installed:

* Java JDK 8 or higher
* Apache NetBeans
* MySQL Server
* MySQL Workbench
* Maven

---

## 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
```

Navigate into the project:

```bash
cd EmployeeManagementNetBeans
```

---

## 2. Configure MySQL

Start your MySQL Server and create the:

```text
employee_management
```

database.

Execute the SQL commands provided above.

---

## 3. Configure Database Credentials

Open:

```text
src/main/java/Employee_Management/ConnectionClass.java
```

Set your local MySQL username and password.

For example:

```java
private static final String USER = "root";
private static final String PASSWORD = "YOUR_PASSWORD";
```

---

## 4. Open in NetBeans

Open NetBeans and select:

```text
File → Open Project
```

Select the project folder.

Because the project uses Maven, NetBeans will automatically read:

```text
pom.xml
```

and download the required dependencies.

---

## 5. Run the Application

Run:

```text
Employee_Management.Main
```

The application starts with the **Login Page**.

---

# 🔑 Application Flow

```text
                    ┌──────────────┐
                    │    START     │
                    └──────┬───────┘
                           ↓
                  ┌─────────────────┐
                  │   Login Page    │
                  └────────┬────────┘
                           │
             ┌─────────────┴─────────────┐
             ↓                           ↓
      Existing Account             New User
             │                           │
             ↓                           ↓
       Enter Login ID              Create Account
       + Password                  5-Digit ID
             │                           │
             └─────────────┬─────────────┘
                           ↓
                    ┌─────────────┐
                    │  Home Page  │
                    └──────┬──────┘
                           │
       ┌───────────────────┼───────────────────┐
       ↓                   ↓                   ↓
    Profile             Manage             Attendance
       │                   │                   │
       ↓                   ↓                   ↓
Add/View Employee     Update Details     Take/View Attendance
```

---

# 🔐 Login System

Each account uses a **5-digit Login ID**.

### Example

```text
Login ID: 12345
Password: ********
```

Valid:

```text
12345
```

Invalid:

```text
1234
123456
12ABC
ABC12
```

The Login ID is stored as the `username` field in the `logindata` table.

---

# 👨‍💼 Employee Module

The employee module allows users to create and manage employee profiles.

### Employee information

```text
Employee ID
Name
Address
Date of Birth
Email
Phone
Job Post
Aadhar Number
```

The existing Add Employee screen inserts these details into the `employee` database table.

---

# 📅 Attendance Module

The attendance module allows attendance to be recorded for individual employees.

Each attendance record contains:

```text
Employee ID
Employee Name
Email
First Half
Second Half
Date
```

Attendance can be recorded as:

```text
Present
Absent
```

---

# 📸 Screenshots

Add screenshots of your application here to make the GitHub repository more attractive.

Example:

```markdown
## 📸 Screenshots

### Login Page

![Login Page](screenshots/login.png)

### Create Account

![Create Account](screenshots/register.png)

### Home Page

![Home Page](screenshots/home.png)

### Employee Profile

![Employee Profile](screenshots/employee-profile.png)

### Attendance

![Attendance](screenshots/attendance.png)
```

Recommended screenshot folder:

```text
screenshots/
├── login.png
├── register.png
├── home.png
├── employee-profile.png
└── attendance.png
```

---

# 🔮 Future Improvements

Some possible enhancements for future versions:

* 🔒 Password hashing using BCrypt
* 👥 Role-based access control
* 🗑️ Complete Delete Employee functionality
* 🔍 Advanced employee search and filtering
* 📊 Attendance statistics and reports
* 📈 Dashboard with employee/attendance analytics
* 📄 Export employee data to PDF/Excel
* 📧 Email notifications
* 📝 Input validation for phone, email, Aadhar and dates
* 🔐 Forgot Password functionality
* 🌐 Migration from desktop application to a web-based system
* 🧪 Unit and integration testing

---

# 🧑‍💻 Learning Outcomes

This project demonstrates practical experience with:

* Object-Oriented Programming in Java
* Java Swing GUI development
* Event-driven programming
* JDBC database connectivity
* SQL and relational databases
* CRUD operations
* Authentication
* Exception handling
* Maven project management
* Git/GitHub workflow

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository.
2. Create a new branch.

```bash
git checkout -b feature/new-feature
```

3. Make your changes.
4. Commit your changes.

```bash
git commit -m "Add new feature"
```

5. Push the branch.

```bash
git push origin feature/new-feature
```

6. Open a Pull Request.

---

# 📄 License

This project is intended for educational and portfolio purposes.

You are free to study, modify, and improve the project.

---

# ⭐ Support

If you find this project useful, consider giving the repository a ⭐ on GitHub.

---

## 👨‍💻 Author

**Aniket Kumar Anand**

Built with ❤️ using **Java + Swing + MySQL + JDBC + Maven**.

