# 🛠️ ServiciosYa - Backend API

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-brightgreen?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-blue?style=for-the-badge&logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

**ServiciosYa** is a RESTful API designed to connect service providers (plumbers, electricians, etc.) with customers in real-time. This project handles the core business logic, secure authentication, and data management for the platform.

---

## 🚀 Key Features

* **🔐 Secure Authentication:** Stateless authentication using **JWT (JSON Web Tokens)** with role-based access control (Admin, Provider, Client).
* **📅 Booking Engine:** Logic to schedule appointments, manage availability, and prevent double-booking.
* **⭐ Reputation System:** Algorithm to calculate and update provider ratings based on user feedback.
* **🐳 Containerized:** Fully dockerized environment for consistent deployment.

---

## 🛠️ Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3 (Web, Security, Data JPA)
* **Database:** PostgreSQL
* **Tools:** Docker, Docker Compose, Maven, Git
* **Architecture:** Hexagonal / Layered Architecture

---

## ⚡ How to Run Locally

You can run the entire application (Database + API) with a single command using Docker.

### Prerequisites
* Docker & Docker Compose installed.
* Git.

### Steps
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Lochi011/serviciosya-backend.git](https://github.com/Lochi011/serviciosya-backend.git)
    cd serviciosya-backend
    ```

2.  **Run with Docker Compose:**
    ```bash
    docker-compose up -d
    ```

3.  **Access the API:**
    The server will start at `http://localhost:8080`.

    > **📝 API Documentation:**
    > If Swagger/OpenAPI is enabled, verify endpoints at: `http://localhost:8080/swagger-ui.html`

---

## 📂 Project Structure

```text
src/main/java/com/serviciosya
├── config       # Security & App configuration
├── controllers  # REST Controllers (API Layer)
├── services     # Business Logic
├── repositories # Database Access (JPA)
├── models       # JPA Entities
└── dtos         # Data Transfer Objects
