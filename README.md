# **SmartSupport – Backend (Spring Boot)**

SmartSupport is a **scalable customer support / helpdesk backend system** built using **Java Spring Boot**.
It provides **secure, role-based REST APIs** for managing users, support tickets, conversations, and authentication.

This repository contains the **backend service only**.
The **frontend is developed separately using React**.

---

## 🌐 Frontend Links

* **Frontend GitHub Repository**
  [https://github.com/tushar0945/SmartSupport-Frontend](https://github.com/tushar0945/SmartSupport-Frontend)

* **Frontend Live Demo**
  [https://smartsupport.netlify.app/](https://smartsupport.netlify.app/)

---

## 🚀 Tech Stack

* Java 17
* Spring Boot
* Spring Security + JWT Authentication
* Spring Data JPA (Hibernate)
* MySQL (local) / Cloud Database (production)
* Maven
* Docker

---

## 🧩 Features

* Secure user authentication using JWT
* Role-based access control

  * USER
  * Staff
  * ADMIN
* Ticket management system

  * Create support tickets
  * Assign tickets to agents
  * Update ticket status (Open, In Progress, Resolved, Closed)
* Ticket conversation system (messages/replies)
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
│           
├── pom.xml
├── Dockerfile
├── .gitignore
└── README.md
```

---

## 🔐 Configuration

* Environment-based configuration using `application.properties`
* Sensitive values managed via environment variables

---

## ☁️ Deployment

* Backend deployed on Render
* Database hosted on cloud (MySQL)
* Environment variables configured in Render dashboard

---

## 🔒 Security Notes

* JWT-based authentication
* Role-based API protection
* CORS restricted to frontend domain

---

## 👨‍💻 Author

**Tushar Patil**
Backend Developer | Spring Boot | React

---

## 📌 Project Status

✅ Backend ready for production deployment
