# User Login Service

A full-stack authentication and user management application with JWT (JSON Web Token) authentication and GitHub OAuth2 integration.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Frontend Components](#frontend-components)
- [Docker Deployment](#docker-deployment)
- [Database](#database)
- [Environment Variables](#environment-variables)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

- **JWT Authentication**: Secure token-based authentication mechanism
- **GitHub OAuth2 Integration**: Login with GitHub accounts
- **User Management**: User registration, authentication, and profile management
- **RESTful API**: Clean REST API endpoints for user operations
- **PostgreSQL Database**: Persistent data storage with JPA
- **Docker Support**: Containerized deployment ready
- **Responsive UI**: React-based responsive user interface
- **Environment Configuration**: Flexible configuration using environment variables

## 🛠️ Tech Stack

### Backend

- **Framework**: Spring Boot 4.0.0
- **Language**: Java 17
- **Build Tool**: Maven
- **Authentication**:
  - JWT (JJWT 0.11.5)
  - OAuth2 (Spring Security OAuth2 Client)
- **Database**: PostgreSQL with Spring Data JPA
- **ORM**: Hibernate (via Spring Data JPA)
- **Utilities**: Lombok (for reducing boilerplate)

### Frontend

- **Library**: React 19.2.0
- **Routing**: React Router DOM 7.13.1
- **Styling**: Tailwind CSS 3.4.19
- **HTTP Client**: Axios 1.13.2
- **Build Tool**: npm/Create React App
- **Testing**: React Testing Library, Jest

### DevOps & Deployment

- **Containerization**: Docker
- **Orchestration**: Docker Compose
- **Cloud**: Ready for AWS ECR deployment

## 📁 Project Structure

```
userloginservice/
├── userloginservice/              # Backend (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── learning/project/userloginservice/
│   │   │   │       ├── UserloginserviceApplication.java
│   │   │   │       ├── config/                      # Security & Spring configs
│   │   │   │       │   └── SecurityConfig.java
│   │   │   │       ├── controller/                  # REST endpoints
│   │   │   │       │   ├── AuthenticationController.java
│   │   │   │       │   ├── GithubController.java
│   │   │   │       │   └── UserController.java
│   │   │   │       ├── dto/                         # Data Transfer Objects
│   │   │   │       │   ├── UserDto.java
│   │   │   │       │   ├── GithubUserInfo.java
│   │   │   │       │   └── OAuthUserInfo.java
│   │   │   │       ├── model/                       # JPA Entities
│   │   │   │       ├── service/                     # Business logic
│   │   │   │       ├── repository/                  # Data access layer
│   │   │   │       ├── operation/                   # Business operations
│   │   │   │       ├── util/                        # Utility classes
│   │   │   │       └── globalException/             # Exception handling
│   │   │   └── resources/
│   │   │       ├── application.yml                  # Configuration
│   │   │       ├── application.yaml
│   │   │       ├── static/
│   │   │       └── templates/
│   │   └── test/
│   │       └── java/                                # Unit tests
│   ├── pom.xml                                       # Maven dependencies
│   ├── Dockerfile
│   ├── mvnw / mvnw.cmd                              # Maven wrapper
│   └── HELP.md
│
├── web/                           # Frontend (React)
│   ├── src/
│   │   ├── App.js                                    # Main component
│   │   ├── index.js                                  # Entry point
│   │   ├── index.css
│   │   ├── commonComponents/
│   │   │   └── apiClient.jsx                         # Axios instance
│   │   ├── components/
│   │   │   ├── GithubLogin.jsx
│   │   │   ├── GithubSucces.jsx
│   │   │   ├── WelcomePage.jsx
│   │   │   └── Forbidden.jsx
│   │   ├── public/
│   │   │   └── index.html
│   │   ├── build/                                    # Production build
│   │   └── static/
│   ├── package.json                                  # Dependencies
│   ├── tailwind.config.js
│   ├── Dockerfile
│   └── README.md
│
├── docker-compose.yml              # Production Docker Compose
├── docker-compose-local.yml        # Local development Docker Compose
└── README.md                        # This file
```

## 📋 Prerequisites

### For Local Development

**Backend:**

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

**Frontend:**

- Node.js 18+ (LTS recommended)
- npm 9+

**Optional:**

- Docker & Docker Compose (for containerized development)
- GitHub OAuth Application credentials (for GitHub login)

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd userloginservice
```

### 2. Backend Setup

```bash
cd userloginservice

# Install dependencies (Maven will download automatically)
./mvnw clean install

# For Windows:
mvnw.cmd clean install
```

### 3. Frontend Setup

```bash
cd ../web

# Install dependencies
npm install

# Install Tailwind CSS dependencies
npm install -D tailwindcss
```

## ⚙️ Configuration

### Backend Configuration

Create/edit `userloginservice/src/main/resources/application.yml`:

```yaml
server:
  port: 8080

spring:
  application:
    name: userloginservice
  datasource:
    url: jdbc:postgresql://localhost:5432/userloginservice
    username: postgres
    password: your_db_password
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 2
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: ${GITHUB_CLIENT_ID}
            client-secret: ${GITHUB_CLIENT_SECRET}
            scope: user:email

# JWT Configuration
jwt:
  secret: ${JWT_SECRET:your-secret-key-change-in-production}
  expiration: 86400000 # 24 hours in milliseconds
```

### GitHub OAuth Setup

1. Go to [GitHub Developer Settings](https://github.com/settings/developers)
2. Click "New OAuth App"
3. Fill in the application details:
   - **Application name**: User Login Service
   - **Homepage URL**: `http://localhost:3000`
   - **Authorization callback URL**: `http://localhost:8080/login/oauth2/code/github`
4. Copy the `Client ID` and `Client Secret`

### Environment Variables

Create a `.env` file in the project root:

```bash
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/userloginservice
DB_USERNAME=postgres
DB_PASSWORD=db1234

# JWT Configuration
JWT_SECRET=your-super-secret-jwt-key-here

# GitHub OAuth
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret

# Server Configuration
PORT=8080
```

## 🏃 Running the Application

### Option 1: Local Development (without Docker)

**Start PostgreSQL:**

```bash
# On Windows (if using PostgreSQL service)
# Or use Docker:
docker run --name postgres-userlogin -e POSTGRES_PASSWORD=db1234 -e POSTGRES_DB=userloginservice -p 5432:5432 -d postgres
```

**Start Backend:**

```bash
cd userloginservice
./mvnw spring-boot:run
```

The backend will be available at `http://localhost:8080`

**Start Frontend (in a new terminal):**

```bash
cd web
npm start
```

The frontend will be available at `http://localhost:3000`

### Option 2: Using Docker Compose (Local)

```bash
# Build and start services
docker-compose -f docker-compose-local.yml up --build

# Or in detached mode
docker-compose -f docker-compose-local.yml up -d

# View logs
docker-compose -f docker-compose-local.yml logs -f

# Stop services
docker-compose -f docker-compose-local.yml down
```

### Option 3: Using Docker Compose (Production)

```bash
# Set environment variables
export BACKEND_ECR_PATH=your-ecr-path/backend
export FRONTEND_ECR_PATH=your-ecr-path/frontend
export JWT_SECRET=your-production-secret
export GITHUB_CLIENT_ID=your-github-client-id
export GITHUB_CLIENT_SECRET=your-github-client-secret

# Start services
docker-compose up -d
```

## 📡 API Documentation

### Authentication Endpoints

#### Login with Email/Password

```
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": { ... }
}
```

#### GitHub OAuth Login

```
GET /login/oauth2/authorization/github
```

Redirects to GitHub login page, then returns JWT token upon success.

### User Endpoints

#### Get Current User

```
GET /api/user/profile
Authorization: Bearer <JWT_TOKEN>

Response:
{
  "id": 1,
  "email": "user@example.com",
  "name": "User Name",
  "githubId": "github_username",
  ...
}
```

#### Update User

```
PUT /api/user/profile
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "name": "New Name",
  "email": "newemail@example.com"
}
```

#### Logout

```
POST /api/auth/logout
Authorization: Bearer <JWT_TOKEN>
```

## 🎨 Frontend Components

### Main Components

- **WelcomePage**: Landing page with login options
- **GithubLogin**: GitHub OAuth login component
- **GithubSucces**: Success page after GitHub login
- **Forbidden**: 403 Forbidden page
- **apiClient**: Configured Axios instance for API calls

### Navigation

Routes are managed with React Router DOM:

- `/` - Welcome page
- `/login` - Login page
- `/github-login` - GitHub login callback
- `/dashboard` - User dashboard (protected)
- `/forbidden` - Forbidden access page

## 🐳 Docker Deployment

### Building Docker Images

**Backend:**

```bash
cd userloginservice
docker build -t userloginservice-backend:latest .
```

**Frontend:**

```bash
cd web
docker build -t userloginservice-frontend:latest .
```

### Pushing to ECR (AWS)

```bash
# Tag images
docker tag userloginservice-backend:latest $ECR_REGISTRY/userloginservice-backend:v1.0.0
docker tag userloginservice-frontend:latest $ECR_REGISTRY/userloginservice-frontend:v1.0.0

# Push to ECR
docker push $ECR_REGISTRY/userloginservice-backend:v1.0.0
docker push $ECR_REGISTRY/userloginservice-frontend:v1.0.0
```

## 🗄️ Database

### Database Setup

The application uses PostgreSQL with automatic schema creation through Hibernate.

**Database Connection Details:**

- **Host**: localhost (local) or RDS endpoint (production)
- **Port**: 5432
- **Database**: userloginservice
- **Username**: postgres
- **Password**: (set via environment variable)

### Schema Auto-Creation

Spring JPA automatically creates tables on first run with:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

## 🌍 Environment Variables

| Variable               | Description                | Default        | Required |
| ---------------------- | -------------------------- | -------------- | -------- |
| `PORT`                 | Server port                | 8080           | No       |
| `DB_URL`               | PostgreSQL connection URL  | localhost:5432 | No       |
| `DB_USERNAME`          | Database username          | postgres       | No       |
| `DB_PASSWORD`          | Database password          | db1234         | No       |
| `JWT_SECRET`           | JWT signing secret         | (none)         | **Yes**  |
| `GITHUB_CLIENT_ID`     | GitHub OAuth client ID     | (none)         | **Yes**  |
| `GITHUB_CLIENT_SECRET` | GitHub OAuth client secret | (none)         | **Yes**  |

## 👨‍💻 Development

### Running Tests

**Backend:**

```bash
cd userloginservice
./mvnw test
```

**Frontend:**

```bash
cd web
npm test
```

### Building for Production

**Backend:**

```bash
cd userloginservice
./mvnw clean package
# JAR file: target/JwtAuthentication.jar
```

**Frontend:**

```bash
cd web
npm run build
# Build directory: build/
```

### Code Style

- Backend: Follow Spring Boot conventions
- Frontend: React best practices with functional components
- Use meaningful variable and function names
- Add comments for complex logic

## 📝 Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Commit your changes: `git commit -am 'Add your feature'`
3. Push to the branch: `git push origin feature/your-feature`
4. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Support

For issues or questions:

1. Check existing GitHub issues
2. Create a new issue with detailed description
3. Include steps to reproduce for bugs
4. Add relevant logs and screenshots

## 🔐 Security Notes

- Always use strong JWT secrets in production
- Keep GitHub OAuth credentials secure
- Use environment variables for sensitive data
- Enable HTTPS in production
- Regularly update dependencies
- Implement rate limiting on authentication endpoints
- Use CORS policies appropriately

---

**Happy Coding! 🚀**
