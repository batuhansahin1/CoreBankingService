# 🚀 Distributed Digital Wallet Ecosystem

A fault-tolerant, highly scalable digital wallet application built with **Java 21** and **Spring Boot 3**. This project demonstrates the practical application of **Domain-Driven Design (DDD)** principles and distributed transaction management within a microservices architecture.

## 📌 Architectural Overview
Traditional monolithic financial systems often struggle with scalability and database lock bottlenecks. This ecosystem solves these challenges by implementing a decoupled microservices architecture with a strict **Database-per-Service** pattern, ensuring complete data isolation across domains.

## 🏗️ Microservices Boundaries & Data Models

The business logic is strictly isolated into three distinct bounded contexts to prevent cross-domain contamination. Each service manages its own dedicated database schema.

### 🔐 1. [Identity Service](https://github.com/batuhansahin1/IdentitiyService)
Manages user registration, authentication, and JSON Web Token (JWT) generation.

<img width="992" height="904" alt="identityER" src="https://github.com/user-attachments/assets/b162baa2-076f-4d01-a38c-bd92ead3fe41" />


### 🏦 2. [Core Banking Service](https://github.com/batuhansahin1/CoreBankingService)
Handles core financial operations, account balances, and central database validations. *Intentionally isolated from the external network.*

<img width="1280" height="721" alt="banking" src="https://github.com/user-attachments/assets/758ded90-ef80-442a-8d4a-264bc256a28a" />


### 💸 3. [Transfer Service](https://github.com/batuhansahin1/TransferService)
Manages fund transfers, and initiates distributed transaction workflows.

<img width="992" height="904" alt="transferER" src="https://github.com/user-attachments/assets/feff7964-07a4-4a87-83c2-8130bc522218" />


---




## 🛠️ Technology Stack
* **Language & Framework:** Java 21, Spring Boot 3.x
* **Architecture:** Microservices, Domain-Driven Design (DDD)
* **Databases:** PostgreSQL (Relational)
* **Message Broker:** RabbitMQ (Asynchronous Event-Driven Communication)
* **Routing & Discovery:** Spring Cloud API Gateway, Spring Cloud Eureka

## ⚙️ Distributed Transaction Management

To ensure eventual data consistency across isolated domains without relying on synchronous, performance-degrading network locks, the system employs the **Choreography-based Saga Pattern** orchestrated via **RabbitMQ**.

### ✅ 1. The Happy Path (Successful Transaction)
When a valid transfer request is initiated, the system processes the transaction asynchronously.


<img width="651" height="549" alt="sagaHappyPath" src="https://github.com/user-attachments/assets/8618c133-a4f3-4316-aecc-0e7e1cede965" />


* **Flow:** The Transfer Service creates a `PENDING` transaction and publishes a `TransferInitiatedEvent`. The Core Banking Service consumes this, successfully updates the balances, and publishes a `TransferCompletedEvent`. The Transfer Service then finalizes the state to `SUCCESS`.

### ❌ 2. The Compensation Path (Fault Tolerance & Rollbacks)
The system is designed to gracefully handle business rule violations (e.g., insufficient funds) and prevent orphaned data.

<img width="648" height="486" alt="SagaCompensation" src="https://github.com/user-attachments/assets/50f53f0b-d0de-4677-aa0d-0ebea1ef9a59" />


* **Flow:** If the Core Banking Service detects insufficient funds upon consuming the `TransferInitiatedEvent`, it rejects the operation and publishes a `TransferFailedEvent`. The Transfer Service catches this event and immediately rolls back the local transaction state to `FAILED`.


<img width="886" height="210" alt="rabbitMqQueue" src="https://github.com/user-attachments/assets/fa3ed625-edfb-4315-892e-546eb7387ea4" />


## 🛡️ Security, Authentication & JWT Validation

The ecosystem enforces a strict, stateless security perimeter to protect financial data and endpoints:

* **Spring Security & Stateless JWT Validation:** Distributed endpoints (such as fund transfers) are secured using **Spring Security**. Upon successful authentication, the *Identity Service* issues a cryptographically signed **JSON Web Token (JWT)**. Peripheral services utilize customized Spring Security filter chains to intercept incoming requests, extract the token from the `Authorization: Bearer` header, and validate its signature, expiration, and claims statelessly.
* **Centralized API Gateway Routing:** External clients never communicate with the core business services directly. The **Spring Cloud API Gateway** acts as the single entry point (Reverse Proxy), ensuring that internal services like the *Core Banking Service* remain hidden within an isolated virtual network while routing authorized traffic seamlessly.
* **Infrastructure & Service Registry Security (Eureka Server):** To ensure microservices can securely discover one another, the **Eureka Server** is isolated from the external network. Furthermore, to prevent rogue services from registering to the network and manipulating traffic, service registration processes to the Eureka Server are secured using **HTTP Basic Authentication**.

<img width="908" height="261" alt="eureka" src="https://github.com/user-attachments/assets/046814f7-eb7b-4b8f-9fff-0709b4d78c06" />


---

## 👨‍💻 Author

**Batuhan Şahin**  
*Computer Engineering Student | Fullstack Developer*  
[LinkedIn Profile](https://www.linkedin.com/in/batuhansahin1/)
