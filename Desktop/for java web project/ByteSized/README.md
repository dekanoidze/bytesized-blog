# ByteSized Blog Platform

A monolithic Spring Boot blog application built for learning Java web development. Users can browse posts on a Thymeleaf-powered website, create new posts through a secured form, and manage content through a REST API with role-based access control.

## Tech Stack

- **Java 25**
- **Spring Boot 4.0.5**
- **Spring MVC** + **Thymeleaf**
- **Spring Data JPA** + **Hibernate**
- **Spring Security** (HTTP Basic, BCrypt)
- **Spring Cache** (`@Cacheable` / `@CacheEvict`)
- **Spring Boot Actuator**
- **H2** (development) / **PostgreSQL** (production)
- **JUnit 5** + **Mockito**

## Features

- Paginated blog post listing on the home page
- Create posts with optional cover image upload
- REST API for listing and deleting posts
- Input validation on post creation
- Global API error handling
- **Security:** role-based endpoints with BCrypt-encoded passwords
- **Caching:** API post list cached and evicted on create/delete
- **Monitoring:** `/actuator/health` endpoint exposed
- **Scheduling:** hourly background task logs total post count
- **Testing:** unit tests for services and REST controller
- OpenAPI/Swagger UI for API documentation

## Prerequisites

- JDK 25 (or compatible JDK)
- Git
- Maven Wrapper is included (`mvnw` / `mvnw.cmd`) — no separate Maven install required

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/dekanoidze/bytesized-blog.git
cd "Desktop/for java web project/ByteSized"
```

### 2. Run the application

**Windows (PowerShell):**

```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-25.0.1.8-hotspot"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

The app starts on **http://localhost:8080** with the `dev` profile (in-memory H2 database).

### 3. Run tests

```powershell
.\mvnw.cmd test
```

## Demo Accounts

| Username | Password   | Role  | Permissions                          |
|----------|------------|-------|--------------------------------------|
| `author` | `author123` | USER  | Create posts (`/posts/new`, `POST /posts`) |
| `admin`  | `admin123`  | ADMIN | Delete posts (`DELETE /api/posts/{id}`)    |

Authentication uses **HTTP Basic Auth**. The browser will prompt for credentials when accessing secured pages.

## Endpoints

### Public

| Method | Path               | Description              |
|--------|--------------------|--------------------------|
| GET    | `/`                | Home page (post list)    |
| GET    | `/api/posts`       | List all posts (JSON)    |
| GET    | `/actuator/health` | Application health check |

### Secured

| Method | Path              | Role  | Description        |
|--------|-------------------|-------|--------------------|
| GET    | `/posts/new`      | USER  | Create post form   |
| POST   | `/posts`          | USER  | Submit new post    |
| DELETE | `/api/posts/{id}` | ADMIN | Delete a post      |

### Developer Tools (dev profile)

| Path                    | Description        |
|-------------------------|--------------------|
| `/swagger-ui/index.html`| API documentation  |
| `/h2-console`           | H2 database console |

**H2 console credentials:**
- JDBC URL: `jdbc:h2:mem:bytesizeddb`
- Username: `sa`
- Password: *(empty)*

## Project Structure

```
src/main/java/ge/tsu/ByteSized/
├── config/          # Security, cache, web MVC configuration
├── controller/      # Web (Thymeleaf) and REST controllers
├── dto/             # Request/response data transfer objects
├── exception/       # Global exception handling
├── model/           # JPA entities (BlogPost, Comment)
├── repository/      # Spring Data JPA repositories
├── scheduler/       # Scheduled background tasks
└── service/         # Business logic

src/main/resources/
├── templates/       # Thymeleaf HTML pages
├── static/          # CSS and images
└── application*.yml # Configuration profiles

src/test/java/       # Unit tests (JUnit 5 + Mockito)
```

## Configuration Profiles

| Profile | Database   | Usage                          |
|---------|------------|--------------------------------|
| `dev`   | H2 (memory)| Local development (default)    |
| `prod`  | PostgreSQL | Production deployment          |

Active profile is set in `application.yml` (`spring.profiles.active: dev`).

## Example: Delete a Post (Admin)

**PowerShell:**

```powershell
$user = "admin"
$pass = "admin123"
$pair = "$user`:$pass"
$base64 = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes($pair))
Invoke-WebRequest -Uri "http://localhost:8080/api/posts/1" -Method DELETE -Headers @{ Authorization = "Basic $base64" }
```

## Author

**Luka Dekanoidze** — [GitHub](https://github.com/dekanoidze)

## License

This project was created as a university coursework assignment.
