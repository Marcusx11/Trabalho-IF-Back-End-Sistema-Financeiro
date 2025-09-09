# Financial System Backend (IFMG Project)

[![Java](https://img.shields.io/badge/Java-11%2B-blue?logo=java)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-C71A36?logo=apache-maven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

Backend implementation of a **Financial Management System** developed as part of an academic project at **IFMG – Campus Formiga**.  
It provides core financial operations such as user management, transaction recording, and account statement handling, exposing these features through RESTful APIs.

---

## 📌 Features

- User registration and authentication  
- Financial transactions management (income/expenses)  
- Account statement generation (with XML example)  
- REST API integration (tested with Postman)  
- Validation and error handling for safe operations  

---

## 🛠️ Tech Stack

- **Language:** Java  
- **Build Tool:** Maven (via `mvnw` wrapper)  
- **API Testing:** Postman (`SistemaFinanceiro.postman_collection.json`)  
- **Docs & Requirements:** `Requistios Trabalho - Back-End.pdf`  

---

## 📂 Project Structure

- ├── .mvn/ # Maven wrapper
- ├── mvnw, mvnw.cmd # Maven executables
- ├── pom.xml # Maven dependencies & build config
- ├── src/ # Java source code
- ├── Requistios Trabalho - Back-End.pdf # Requirements document
- ├── SistemaFinanceiro.postman_collection.json # Postman API collection
- └── extrato.xml # Example of account statement


---

## 🚀 Getting Started

### Prerequisites
- Java JDK **11+**
- Maven **3.6+** (or just use the included wrapper)
- (Optional) Postman for API testing

### Installation & Run

Clone the repository:
```bash
git clone https://github.com/Marcusx11/Trabalho-IF-Back-End-Sistema-Financeiro.git
```
```bash
cd "Trabalho-IF-Back-End-Sistema-Financeiro"
```

Build and run:
```bash
mvnw clean install
```
**Remember to start your MySQL Server.**
```bash
mvnw spring-boot:run   # if Spring Boot is used
```

The application will be available on:
```bash
http://localhost:8080
```

## 📬 API Usage

You can import the Postman collection for quick testing:

File: SistemaFinanceiro.postman_collection.json

Example request (transaction creation):
```bash
POST /api/transactions
Content-Type: application/json

{
  "type": "EXPENSE",
  "amount": 120.50,
  "description": "Utility bill"
}
```

Example response:
```bash
{
  "id": 1,
  "type": "EXPENSE",
  "amount": 120.50,
  "description": "Utility bill",
  "date": "2025-09-09T20:00:00"
}
```

## 📖 Documentation

- 📄 [Project Requirements (PDF)](./Requistios%20Trabalho%20-%20Back-End.pdf)  
- 🧪 [Postman Collection](./SistemaFinanceiro.postman_collection.json)  
- 🗂️ [Example Bank Statement (extrato.xml)](./extrato.xml)  
