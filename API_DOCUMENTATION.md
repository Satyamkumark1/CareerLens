# CareerLens Backend API Documentation

## Overview
CareerLens is a Spring Boot-based backend for an internship recommendation engine. It provides RESTful APIs for user registration, login, internship management, student profiles, and admin operations. JWT authentication is used for secure access.

---

## Base URLs
- Development: `http://localhost:8080`
- Production: `https://api.careerlens.com`

---

## Authentication
- **JWT Bearer Token**: After login, use the token in the `Authorization` header for protected endpoints.
  - Example: `Authorization: Bearer <token>`

---

## Main Endpoints

### 1. User Registration
- **POST** `/api/auth/register`
- **Body:**
```json
{
  "email": "user@example.com",
  "password": "yourpassword",
  "firstName": "First",
  "lastName": "Last"
}
```
- **Response:**
  - `201 Created` on success

### 2. User Login
- **POST** `/api/auth/login`
- **Body:**
```json
{
  "email": "user@example.com",
  "password": "yourpassword"
}
```
- **Response:**
```json
{
  "token": "<JWT token>",
  "user": { ... }
}
```

### 3. Get User Profile
- **GET** `/api/user/get/{userId}`
- **Headers:** `Authorization: Bearer <token>`
- **Response:** User profile data

### 4. Internship Listing
- **GET** `/api/internships`
- **Response:** List of internships

### 5. Student Profile
- **POST** `/api/student/profile`
- **Headers:** `Authorization: Bearer <token>`
- **Body:** Student profile details
- **Response:** Created profile

### 6. Admin Endpoints
- **POST** `/api/admin/create`
  - Create a new admin user
- **GET** `/api/admin/users`
  - List all users
- **GET** `/api/admin/admins`
  - List all admins
- **DELETE** `/api/admin/user/{id}`
  - Delete a user by ID

---

## CORS
- CORS is enabled for `http://localhost:3000` (React dev server).

---

## Swagger/OpenAPI
- API docs available at `/swagger-ui.html` or `/swagger-ui/index.html` when running locally.

---

## How to Connect Frontend
1. **Start Backend**: `mvn spring-boot:run` or run from IDE.
2. **Start Frontend**: `npm start` in your React project.
3. **API Calls**: Use Axios or Fetch in React to call endpoints above.
4. **Authentication**: Save JWT token after login and send it in `Authorization` header for protected requests.

---

## Example React API Call
```js
axios.post('http://localhost:8080/api/auth/login', { email, password })
  .then(res => localStorage.setItem('token', res.data.token));
```

---

## Contact
- For support: support@careerlens.com

---

## License
MIT License
