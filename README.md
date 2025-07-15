# 🧾 ToDa REST API – Microservices Architecture

Questa è un'applicazione ToDo basata su microservizi, sviluppata in **Java** con **Spring Boot**, che implementa gestione utenti, autenticazione JWT e operazioni CRUD.

## 🧩 Architettura

L'applicazione è composta da due microservizi indipendenti:

### 1. ✅ ToDo Service
- Gestione completa delle attività (CRUD)
- Ogni ToDo è associato a un utente
- Accessibile solo con token JWT valido

### 2. 👤 User Service
- Registrazione e login utenti
- Generazione e validazione di JWT
- Gestione profilo utente

Ogni servizio ha un database dedicato e comunica con l'altro tramite richieste HTTP RESTful.

---

## 🔐 Sicurezza
- Autenticazione tramite **JWT**
- Il client riceve un token al login, che va incluso nelle richieste al ToDo Service
- Validazione dei token lato backend

---

## 🧾 Requisiti

- Java 17
- Maven
- MySQL (2 database)
- Postman o client REST
- **File `.env` separato** per ogni microservizio (con configurazioni come URL DB, secret JWT, porte, ecc.)

---

## 🚀 Tecnologie principali
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security + JWT
- MySQL
- REST API
- Docker (facoltativo, in sviluppo)
