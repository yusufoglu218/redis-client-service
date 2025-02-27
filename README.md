# Redis Client Microservice

This microservice provides a REST API to query log messages from Redis based on a UUID.

## 📌 Project Description  
- The API retrieves log messages from Redis using the provided UUID.  
- If the log entry is **not found**, a **"Not Found" (404)** error is returned.  

## 📋 Requirements  
- Java 17+  
- Redis  
- Maven  
- Docker (optional, for running Redis)  

## 🚀 Run the Application  

```bash
mvn spring-boot:run
