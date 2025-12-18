# SmartSupport – Backend (Spring Boot)

SmartSupport is a **customer support / helpdesk backend system** built using **Java Spring Boot**. It provides secure, role-based APIs for managing users, support tickets, conversations, and authentication.

This repository contains the **backend service only**. The frontend is built separately using **React**.

---

## 🚀 Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Security + JWT**
* **Spring Data JPA (Hibernate)**
* **MySQL** (local) / Cloud DB (production)
* **Maven**
* **Docker** (for deployment)

---

## 🧩 Features

* User authentication using JWT
* Role-based access control

  * USER
  * AGENT
  * ADMIN
* Ticket management system

  * Create ticket
  * Assign ticket
  * Update ticket status
* Ticket conversation (replies/messages)
* Secure REST APIs
* CORS configuration for frontend integration

---

## 📁 Project Structure

```
SMARTSUPPORT/
├── src/
│   └── main/
│       ├── java/com/smartsupport/
│       │   ├── controller/
│       │   ├── service/
│       │   ├── repository/
│       │   ├── entity/
│       │   ├── dto/
│       │   ├── security/
│       │   └── SmartSupportApplication.java
│       └── resources/
│           ├── application.properties
│           └── application-local.properties (local only)
├── pom.xml
├── Dockerfile
├── .gitignore
└── README.md
```

---

## 🔐 Configuration

### `application.properties` (Committed to GitHub)

All sensitive values are injected using **environment variables**:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

spring.web.cors.allowed-origins=${FRONTEND_URL}
```

---

### `application-local.properties` (Local Only – NOT pushed)

```properties
DB_URL=jdbc:mysql://localhost:3306/smartsupport
DB_USERNAME=root
DB_PASSWORD=your_password

JWT_SECRET=local-secret-key
FRONTEND_URL=http://localhost:5173
```

---

## ▶️ Run Locally

### 1️⃣ Start MySQL and create database

```sql
CREATE DATABASE smartsupport;
```

### 2️⃣ Run backend with local profile

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Backend will start at:

```
http://localhost:8080
```

---

## 🐳 Docker (Used for Deployment)

### Build JAR

```bash
mvn clean package
```

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## ☁️ Deployment

* Backend deployed on **Render**
* Database hosted on **cloud MySQL / PostgreSQL**
* Environment variables configured in Render dashboard

---

## 🔒 Security Notes

* No secrets are committed to GitHub
* JWT-based authentication
* Role-based API protection
* CORS restricted to frontend domain

---

## 👨‍💻 Author

**Tushar Patil**
Backend Developer | Spring Boot | React

---

## 📌 Status

✅ Backend ready for production deployment

---

If you have questions or want to extend this project, feel free to reach out.
