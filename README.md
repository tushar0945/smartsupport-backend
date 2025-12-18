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
