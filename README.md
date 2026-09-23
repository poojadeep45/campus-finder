# Campus Finder

A Spring Boot REST API for discovering and comparing universities/campuses by
location, programs, fees, facilities, and admission requirements.

## Tech Stack

- Java 17, Spring Boot 4.1.1
- Spring Web, Spring Data JPA, Spring Security (JWT)
- MySQL (production/dev) or H2 (quick local testing)
- Lombok, Jakarta Bean Validation
- springdoc-openapi 3.x (Swagger UI)
- Maven

## Project Structure

```
com.example.campusfinder
├── config          # SecurityConfig, OpenApiConfig, JpaAuditingConfig
├── security        # JwtUtil, JwtAuthenticationFilter, CustomUserDetailsService, UserPrincipal
├── entities        # User, Role, Campus, Program, Facility, Favorite, Review
├── repository      # Spring Data JPA repositories
├── dto
│   ├── request     # RegisterRequest, LoginRequest, CampusRequest, ProgramRequest, ReviewRequest
│   └── response    # AuthResponse, CampusResponse, CampusDetailsResponse, ProgramResponse,
│                   # ReviewResponse, FavoriteResponse, UserResponse, StatisticsResponse,
│                   # ErrorResponse, PageResponse
├── mapper          # Entity <-> DTO conversion
├── service / service.impl
├── controller
└── exception       # Custom exceptions + GlobalExceptionHandler
```

## Architecture

Requests flow through a strict layered architecture:

```
Client
  -> JwtAuthenticationFilter   (reads and validates JWT)
  -> Spring Security           (role-based access check)
  -> Controller                (HTTP mapping, DTOs only)
  -> Service                   (business logic, transactions)
  -> Mapper                    (Entity <-> DTO conversion)
  -> Repository                (Spring Data JPA)
  -> Database                  (MySQL or H2)
```

JPA entities are never returned directly from a controller — every response is
built through a mapper into a dedicated response DTO.

## Setup

### Option A — MySQL (default)

1. Make sure MySQL is running locally.
2. Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
   The database `campus_finder_db` is created automatically on first run.
3. Set your own JWT secret (see below) before running.
4. Run:
   ```bash
   mvn spring-boot:run
   ```

### Option B — H2 in-memory (no MySQL install needed)

Runs with an in-memory database that resets and reseeds from `data.sql` on
every start — useful for quick local testing or demos.

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

H2 console: `http://localhost:8080/h2-console`
JDBC URL: `jdbc:h2:mem:campus_finder_db`, user `sa`, no password.

### JWT Secret

`app.jwt.secret` in `application.properties` must be a long, random,
Base64-encoded string — at least 256 bits once decoded, since tokens are
signed with HS256. Generate one with:

```bash
openssl rand -base64 32
```

```properties
app.jwt.secret=<your generated value>
app.jwt.expiration-ms=86400000
```

Never commit a real production secret to a public repository. For anything
beyond local development, read it from an environment variable instead:

```properties
app.jwt.secret=${JWT_SECRET:fallback-dev-value-only}
```

## API Documentation

Once running, Swagger UI is available at:
```
http://localhost:8080/swagger-ui.html
```
Click **Authorize** and paste a JWT to test protected endpoints directly from
the docs page.

## Authentication Flow

### 1. Register
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "email": "student1@example.com",
    "password": "password123",
    "fullName": "Ali Raza"
  }'
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "userId": 1,
  "username": "student1",
  "role": "USER"
}
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{ "username": "student1", "password": "password123" }'
```

### 3. Use the token
Every protected endpoint requires:
```
Authorization: Bearer <token>
```

**Getting an admin account:** register a normal user, then manually update
their `role` column to `ADMIN` in the database. There is no public
promote-to-admin endpoint — this is intentional, to prevent privilege
escalation through the registration endpoint.

## Sample Requests

### Campuses (public read, admin write)

```bash
# Browse all (paginated)
curl "http://localhost:8080/api/campuses?page=0&size=10&sortBy=campusName&direction=asc"

# Full details (includes programs, facilities, reviews, average rating)
curl http://localhost:8080/api/campuses/1

# Search / filter
curl "http://localhost:8080/api/campuses/search?city=Karachi&program=Computer%20Science&minFee=100000&maxFee=250000&page=0&size=10"

# Create (admin only)
curl -X POST http://localhost:8080/api/campuses \
  -H "Authorization: Bearer <admin_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "universityName": "IBA Karachi",
    "campusName": "Main Campus",
    "description": "Top business school in Pakistan",
    "city": "Karachi",
    "province": "Sindh",
    "country": "Pakistan",
    "averageTuitionFee": 350000,
    "admissionRequirements": "IBA Aptitude Test",
    "facilityNames": ["Library", "Sports Complex", "Wifi"]
  }'

# Update / delete (admin only)
curl -X PUT http://localhost:8080/api/campuses/1 -H "Authorization: Bearer <admin_token>" -H "Content-Type: application/json" -d '{ ... }'
curl -X DELETE http://localhost:8080/api/campuses/1 -H "Authorization: Bearer <admin_token>"
```

### Programs

```bash
curl "http://localhost:8080/api/programs?campusId=1"

curl -X POST http://localhost:8080/api/programs \
  -H "Authorization: Bearer <admin_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "programName": "BS Artificial Intelligence",
    "degreeType": "BS",
    "duration": "4 years",
    "tuitionFee": 190000,
    "eligibilityCriteria": "Intermediate with Mathematics",
    "campusId": 1
  }'
```

### Favorites (authenticated users)

```bash
curl -X POST http://localhost:8080/api/favorites/1 -H "Authorization: Bearer <token>"
curl http://localhost:8080/api/favorites -H "Authorization: Bearer <token>"
curl -X DELETE http://localhost:8080/api/favorites/1 -H "Authorization: Bearer <token>"
```

### Reviews (owners edit their own; admins can delete any)

```bash
curl -X POST http://localhost:8080/api/reviews/campus/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{ "rating": 5, "comment": "Great faculty and campus facilities." }'

curl "http://localhost:8080/api/reviews/campus/1?page=0&size=10"

curl -X PUT http://localhost:8080/api/reviews/3 -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{ "rating": 4, "comment": "Updated opinion." }'

curl -X DELETE http://localhost:8080/api/reviews/3 -H "Authorization: Bearer <token>"
```

### Admin

```bash
curl http://localhost:8080/api/admin/statistics -H "Authorization: Bearer <admin_token>"
curl http://localhost:8080/api/admin/users -H "Authorization: Bearer <admin_token>"
curl -X PATCH "http://localhost:8080/api/admin/users/2/status?enabled=false" -H "Authorization: Bearer <admin_token>"
```

### User Profile

```bash
curl http://localhost:8080/api/users/me -H "Authorization: Bearer <token>"
curl -X PUT "http://localhost:8080/api/users/me?fullName=New%20Name&email=new@example.com" -H "Authorization: Bearer <token>"
```

## Authorization Rules Summary

| Route | Access |
|---|---|
| `POST /api/auth/**` | Public |
| `GET /api/campuses/**` | Public |
| `GET /api/programs/**` | Public |
| `GET /api/reviews/campus/**` | Public |
| `POST/PUT/DELETE /api/campuses/**` | `ROLE_ADMIN` |
| `POST/PUT/DELETE /api/programs/**` | `ROLE_ADMIN` |
| `/api/admin/**` | `ROLE_ADMIN` |
| Everything else | Any authenticated user |

## Error Response Format

```json
{
  "timestamp": "2026-09-16T12:00:00",
  "status": 404,
  "message": "Campus not found with id: 10",
  "path": "/api/campuses/10"
}
```
Validation errors additionally include a `validationErrors` map of
field → message.

## Testing

### Unit tests
```bash
mvn test
```
Includes `AuthServiceImplTest` (Mockito-based) covering successful
registration and the duplicate-username rejection path.

### Manual/API testing with Postman
1. Create an environment with `base_url`, `token`, `admin_token` variables.
2. On the Login request, add a post-response script to auto-save the token:
   ```javascript
   const data = pm.response.json();
   pm.environment.set("token", data.token);
   ```
3. Suggested order: register -> login -> public GET -> attempt an admin
   write as a regular user (expect 403) -> promote that user to ADMIN in the
   database -> retry the write (expect 201) -> programs -> reviews ->
   favorites -> admin statistics.

## Notes

- Passwords are hashed with BCrypt and never returned in any response
  (`User.password` is marked `@JsonIgnore`).
- JPA entities are never exposed through controllers; all responses go
  through a mapper into a dedicated DTO.
- If you change `app.jwt.secret`, all previously issued tokens become
  invalid immediately — this is expected.