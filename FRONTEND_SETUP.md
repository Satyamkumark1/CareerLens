# CareerLens React Frontend Setup Guide

## Backend Configuration ✅

Your Spring Boot backend is now configured with:
- ✅ CORS enabled for React (localhost:3000)
- ✅ Swagger/OpenAPI documentation
- ✅ JWT authentication
- ✅ All necessary endpoints

## Frontend Setup Instructions

### 1. Create React Project

```bash
# Navigate to your project root (outside the Spring Boot project)
cd /Users/cashify/Desktop

# Create React app
npx create-react-app careerlens-frontend
cd careerlens-frontend

# Install additional dependencies
npm install axios react-router-dom @mui/material @emotion/react @emotion/styled
npm install @mui/icons-material @mui/x-date-pickers
npm install @tanstack/react-query
npm install react-hook-form @hookform/resolvers yup
```

### 2. Project Structure

```
careerlens-frontend/
├── public/
├── src/
│   ├── components/
│   │   ├── auth/
│   │   │   ├── LoginForm.jsx
│   │   │   ├── RegisterForm.jsx
│   │   │   └── ProtectedRoute.jsx
│   │   ├── dashboard/
│   │   │   ├── Dashboard.jsx
│   │   │   └── ProfileCard.jsx
│   │   ├── internships/
│   │   │   ├── InternshipList.jsx
│   │   │   ├── InternshipCard.jsx
│   │   │   └── InternshipFilters.jsx
│   │   ├── recommendations/
│   │   │   ├── RecommendationList.jsx
│   │   │   ├── RecommendationCard.jsx
│   │   │   └── FeedbackForm.jsx
│   │   └── common/
│   │       ├── Header.jsx
│   │       ├── Sidebar.jsx
│   │       └── LoadingSpinner.jsx
│   ├── services/
│   │   ├── api.js
│   │   ├── authService.js
│   │   ├── internshipService.js
│   │   └── recommendationService.js
│   ├── hooks/
│   │   ├── useAuth.js
│   │   └── useApi.js
│   ├── context/
│   │   └── AuthContext.jsx
│   ├── utils/
│   │   ├── constants.js
│   │   └── helpers.js
│   ├── App.js
│   ├── index.js
│   └── index.css
├── package.json
└── README.md
```

### 3. Environment Configuration

Create `.env` file in the frontend root:

```env
REACT_APP_API_BASE_URL=http://localhost:8080
REACT_APP_API_VERSION=/api
```

### 4. Start Development

```bash
# Start React frontend
npm start

# In another terminal, start Spring Boot backend
cd /Users/cashify/Desktop/CareerLens
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## API Endpoints Reference

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh token

### Student Profile
- `POST /api/students/add` - Create student profile
- `GET /api/students/profile/{id}` - Get student profile
- `PUT /api/students/profile` - Update student profile

### Internships
- `GET /api/internships` - List internships (with filters)
- `GET /api/internships/{id}` - Get internship details
- `GET /api/internships/categories` - Get categories
- `GET /api/internships/locations` - Get locations

### Applications
- `GET /api/applications` - Get user applications
- `POST /api/applications` - Apply to internship
- `DELETE /api/applications/{id}` - Withdraw application

### Recommendations
- `GET /api/recommendations` - Get personalized recommendations
- `POST /api/recommendations/feedback` - Submit feedback
- `POST /api/recommendations/{id}/viewed` - Mark as viewed
- `POST /api/recommendations/applied/{internshipId}` - Mark as applied

## Development URLs

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console

## Next Steps

1. Set up the basic React project structure
2. Implement authentication flow
3. Create API service layer
4. Build dashboard and internship components
5. Integrate recommendation system

Would you like me to help you create specific components or services?
