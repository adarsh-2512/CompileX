# ⚡ CompileX - Online Java Compiler

CompileX is a **web-based Java compiler and executor** that allows users to write, compile, and run Java code directly from the browser.  
The backend is powered by **Spring Boot**, and the frontend is built with **HTML, CSS, and JavaScript**. APIs are tested with **Postman** and the backend is developed in **IntelliJ IDEA**.

---

## ✨ Features
- 🖊️ Write Java code in the browser  
- ▶️ Compile & run code with one click  
- 📜 Get instant output or error messages  
- 🔗 REST API powered by Spring Boot  
- 🧪 Tested with Postman API  
- 🌐 Ready for deployment on Render  

---

## ⚙️ Setup & Installation

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/adarsh-2512/CompileX.git
cd CompileX

### 2️⃣ Run Backend (Spring Boot with IntelliJ)
- Open the **backend** folder in **IntelliJ IDEA**  
- Make sure you have **JDK 24** installed  
- Run the project using the **Spring Boot main class**  

👉 Backend will run at:  
http://localhost:8080

yaml
Copy
Edit

---

### 3️⃣ Run Frontend
- Open `frontend/index.html` in any modern browser  
- It will automatically call the backend APIs  

---

## 📡 API Endpoints (Spring Boot)

### 🔹 Compile & Run Java Code  
**POST** `/api/compile`  

#### Request Body (JSON)
```json
{
  "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello CompileX!\"); } }"
}
Response
json
Copy
Edit
{
  "output": "Hello CompileX!\n"
}

Testing with Postman

Import the API endpoints into Postman

Send requests by providing Java code as input

Verify the compilation and execution output

Example test:

{
  "code": "public class Test { public static void main(String[] args) { System.out.println(\"Testing CompileX!\"); } }"
}


Expected Response:

{
  "output": "Testing CompileX!\n"
}

📸 Screenshot

![UI](D:\Projects\JC\Java Compiler\UI.png)
