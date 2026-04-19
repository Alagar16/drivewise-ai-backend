# Drivewise AI - Railway Deployment Guide

## Prerequisites
- Railway.app account (https://railway.app)
- GitHub repository with the code
- Environment variables configured

## Deployment Steps

### 1. Connect GitHub Repository
- Go to Railway.app dashboard
- Click "New Project"
- Select "Deploy from GitHub repo"
- Connect your GitHub account
- Select the `backend` repository

### 2. Configure Build Settings
Railway will automatically detect this is a Maven Spring Boot project and use the appropriate builder.

### 3. Set Environment Variables
In your Railway project settings, configure:

```
PORT=8080
SPRING_PROFILES_ACTIVE=production
JAVA_OPTS=-Xmx512m
```

### 4. Deploy
- Railway will automatically build using `mvnw clean package`
- It will run the application using the `Procfile`
- Your app will be available at the generated Railway URL

## Railway Configuration

### Procfile
The `Procfile` tells Railway how to start your application:
```
web: java -Dserver.port=${PORT:8080} -Dspring.profiles.active=production -jar target/drivewise-backend-0.0.1-SNAPSHOT.jar
```

### railway.json
Optional configuration file that tells Railway to use Nixpacks builder.

### .railwayignore
Lists files that should be excluded from deployment to reduce build time.

## Build Command
Railway automatically runs:
```bash
./mvnw clean package -DskipTests
```

## Troubleshooting

### Profile Not Found Warning
✅ **Fixed** - Added production and development profiles in `pom.xml`

### Port Configuration
Railway automatically assigns a PORT environment variable. The application respects this through:
```
server.port=${PORT:8080}
```

### Java Version
The application uses Java 17, which is fully supported by Railway.

## Health Check Endpoint
After deployment, test your endpoint:

```bash
GET https://your-railway-url/api/cars/health
```

Should return:
```
Drivewise AI backend is running!
```

## Test Recommendation Endpoint

```bash
POST https://your-railway-url/api/cars/recommend
Content-Type: application/json

{
  "budget": 1500000,
  "monthlyDistance": "high",
  "drivingPattern": "Mixed",
  "roadReality": "rough",
  "travelGroup": "family",
  "ownershipMindset": "safety",
  "fuelType": "Diesel",
  "vehicleType": "SUV"
}
```

## Logs
View deployment logs in Railway dashboard:
- Click on your project
- Go to "Deployments" tab
- Click on any deployment to see logs

## Common Issues

### Build Timeout
- Railway has a 30-minute build timeout
- Check for infinite loops or long-running processes

### Out of Memory
- Railway provides sufficient memory for Spring Boot
- If needed, adjust in railway.json

### CORS Issues
- The API has `@CrossOrigin(origins = "*")` enabled
- If you need to restrict, change in `CarController.java`

## Next Steps
- Deploy Angular frontend to Vercel/Netlify
- Update frontend API_BASE_URL to point to Railway URL
- Test end-to-end integration

