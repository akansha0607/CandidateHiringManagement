# Candidate Hiring Management System

## Overview
The **Candidate Hiring Management System** is a Java-based backend project designed to **streamline and automate the hiring process**.  
It empowers HR teams to efficiently manage **job postings**, **candidate applications**, **interview scheduling**, and **feedback collection**, all from a centralized platform.


## Features

### 👤 User Management
- Register and manage users (Admin, HR, Candidate, Interviewer)
- JWT-based authentication and authorization using **Spring Security**

### 💼 Job Management
- Create, update, and delete job postings  
- Retrieve job listings and filter by role, department, or location  

### 📄 Application Management
- Candidates can apply for available job roles  
- Fetch applications based on **status** (*Submitted, Reviewed, Hired, Rejected*)  

### 📅 Interview Management
- Schedule, update, and cancel interviews  
- Supports different interview types (*Technical, HR, Managerial*)  
- Kafka-based event publishing for interview updates  

### 💬 Interview Feedback
- Submit feedback for scheduled interviews  
- Fetch all feedback associated with a specific interview  

### ⚡ Performance Optimization
- **Redis Caching** for frequently accessed data (e.g., job listings, candidate details)  
- **Kafka** ensures asynchronous, event-driven data flow across modules  
  
## Tech Stack
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io/)
[![Apache Kafka](https://img.shields.io/badge/Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)

## Installation
#### ✅ Prerequisites
Ensure the following are installed:
- Java 17+
- PostgreSQL
- Maven
- Redis
- Kafka

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
| Endpoint | Method | Description |
|-----------|--------|-------------|
| `/hiring/job/manage/create/job` | POST | Create a new job posting |
| `/hiring/job/manage/jobs` | GET | Fetch all job postings |
| `/hiring/job/manage/{id}` | GET | Get job details by ID |
| `/hiring/application/manage/{jobId}/apply` | POST | Apply for a job |
| `/hiring/application/manage/{applicationId}/status` | PUT | Update application status |
| `/hiring/application/manage/get/application/by/status?status={status}` | GET | Get applications by status |
| `/hiring/interview/manage/schedule` | POST | Schedule an interview |
| `/hiring/interview/manage/{interviewId}` | GET | Get interview details |
| `/hiring/interview/manage/{interviewId}` | PUT | Update interview details |
| `/hiring/interview/manage/{interviewId}` | DELETE | Cancel an interview |
| `/hiring/interview/manage/{interviewId}/feedback` | POST | Submit interview feedback |
| `/hiring/interview/manage/{interviewId}/feedback` | GET | Get feedback for an interview |

## Contribution Guidelines

1. **Fork** this repository.  
2. **Create a new branch** for your feature or fix:  
   ```bash
   git checkout -b feature/your-feature-name
   
## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

_Designed to simplify and optimize the hiring process for HR teams._
