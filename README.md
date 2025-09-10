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
**Remember to start your MySQL Server before running the command above.**
```bash
mvnw spring-boot:run 
```
You can also open this project in any IDE of your choice for easier setups. I recommend you to use IntelliJ, since it was the IDE used for building this app.

The application will be available on:
```bash
http://localhost:8080
```

## 📬 API Usage

You can import the Postman collection for quick testing:

File: SistemaFinanceiro.postman_collection.json

You need to save an user to the database and login to it first to send requests.
Exemaple request for saving a new user
```bash
POST /usuarios/salvar
Content-Type: application/json

{
	"login": "rafael",
	"senha": "123"
}
```

Example response:
```bash
"Usuário salvo(a) com sucesso!"
```
Now, a request example for login to the system:

Example request (transaction creation):
```bash
POST /sistema/metacategorias/salvar
Content-Type: application/json
Authorization: Basic <Converted Base64 code with the following structure: username:password. Postman can do this automatically in Authorization tag.>

{
    "nome": "Meta da Categoria 2",
    "limite": 400.0,
    "controle": true,
    "categoria": {
        "id": "2"
    }
}
```

Example response:
```bash
"MetaCategoria salvo(a) com sucesso!"
```

## 📖 Documentation

- 📄 [Project Requirements (PDF)](./Requistios%20Trabalho%20-%20Back-End.pdf)  
- 🧪 [Postman Collection](./SistemaFinanceiro.postman_collection.json)  
- 🗂️ [Example Bank Statement (extrato.xml)](./extrato.xml)  
