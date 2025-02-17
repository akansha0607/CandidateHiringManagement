# Candidate Hiring Management System

## Overview
The **Candidate Hiring Management System** is a Java project designed to streamline and automate the hiring process. It enables HR teams to efficiently manage job postings, interview schedules, application reviews, and feedback collection.

## Features
- **Streamlined Hiring Process**: Automates job postings, application reviews, and interview scheduling.
- **Centralized Management**: Provides a single platform for tracking candidate progress, collecting feedback, and managing interviews.
  
## Tech Stack
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)

## Installation
### Prerequisites
- Java 17+
- PostgreSQL
- Maven

### Backend Setup
```sh
# Clone the repository
git clone https://github.com/akansha0607/CandidateHiringManagement.git
cd candidate-hiring-management/backend

# Build and run
mvn clean install
mvn spring-boot:run
```

## API Endpoints
| Endpoint                     | Method | Description                       |
|------------------------------|--------|-----------------------------------|
| `/jobs`                      | GET    | Fetch all job postings            |
| `/{id}`                      | GET    | Get job details by ID             |
| `/register`                  | POST   | Submit a candidate application    |
| `/{interviewId}`             | GET    | Retrieve interview schedules      |
| `/{interviewId}/feedback`    | POST   | Submit interview feedback         |

## Contribution Guidelines
1. Fork the repository and create a new branch.
2. Commit your changes with meaningful messages.
3. Push to your fork and submit a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

_Designed to simplify and optimize the hiring process for HR teams._
