# Drivewise AI Backend - Car Recommendation System

## Overview
Drivewise AI is a Spring Boot backend application that provides intelligent car recommendations based on user preferences, driving patterns, budget, and usage requirements.

## Architecture

### Project Structure
```
src/main/java/com/drivewise/drivewisebackend/
├── controller/
│   └── CarController.java          # REST API endpoints
├── service/
│   └── CarService.java             # Business logic and recommendation engine
└── model/
    ├── Car.java                    # Car data model
    ├── UserInput.java              # User input request model
    └── CarResponse.java            # Recommendation response model
```

## API Endpoints

### 1. Get Car Recommendations
**Endpoint:** `POST /api/cars/recommend`

**Request Body:**
```json
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

**Request Parameters:**
- `budget` (int): Maximum budget in rupees
- `monthlyDistance` (string): "low", "medium", or "high"
- `drivingPattern` (string): "City", "Highway", or "Mixed"
- `roadReality` (string): "smooth" or "rough"
- `travelGroup` (string): "solo" or "family"
- `ownershipMindset` (string): "cost", "comfort", "safety", or "performance"
- `fuelType` (string): "Petrol", "Diesel", or "EV"
- `vehicleType` (string): "HatchBack", "SUV", or "Sedan"

**Response:**
```json
[
  {
    "name": "Mahindra XUV500",
    "price": 1200000,
    "fuelType": "Diesel",
    "vehicleType": "SUV",
    "reason": "excellent ground clearance (210mm) for rough roads and high safety rating (4.5/5) perfect for family"
  },
  {
    "name": "Kia Sonet",
    "price": 1100000,
    "fuelType": "Petrol",
    "vehicleType": "SUV",
    "reason": "good ground clearance (200mm) for rough terrain and good safety rating for family travel"
  },
  {
    "name": "Hyundai Creta",
    "price": 1150000,
    "fuelType": "Diesel",
    "vehicleType": "SUV",
    "reason": "matches your preferred Diesel fuel type and excellent ground clearance (205mm) for rough roads"
  }
]
```

### 2. Health Check
**Endpoint:** `GET /api/cars/health`

**Response:**
```
Drivewise AI backend is running!
```

## Recommendation Algorithm

### Scoring System
The recommendation engine uses a multi-factor scoring system:

1. **Base Score:** 100 points for every car

2. **Distance Score** (Monthly Travel Distance)
   - High distance: Mileage × 3
   - Medium distance: Mileage × 2
   - Low distance: Mileage × 1

3. **Road Score** (Road Reality)
   - Rough roads: Ground Clearance × 0.5
   - Smooth roads: Ground Clearance × 0.2

4. **Travel Group Score** (Family vs Solo)
   - Family: Safety Rating × 50
   - Solo: Safety Rating × 10

5. **Fuel Type Bonus:**
   - Matching fuel type: +30 points

6. **Vehicle Type Bonus:**
   - Matching vehicle type: +25 points

7. **Ownership Mindset Score:**
   - **Safety:** Safety Rating × 40
   - **Cost:** (1,000,000 - Price) / 10,000
   - **Performance:** Mileage × 2
   - **Comfort:** (Safety Rating × 20) + (Mileage × 1.5)

### Final Recommendations
- All cars are filtered by budget
- Cars are scored using the algorithm above
- Top 3 cars (by score) are returned with dynamic reasons

## In-Memory Dataset

### Available Cars

#### Hatchbacks
1. **Maruti Swift**
   - Price: ₹5,00,000
   - Fuel: Petrol
   - Safety Rating: 3.5/5
   - Ground Clearance: 170mm
   - Mileage: 20.5 km/l

2. **Hyundai i20**
   - Price: ₹5,50,000
   - Fuel: Petrol
   - Safety Rating: 3.8/5
   - Ground Clearance: 165mm
   - Mileage: 19.0 km/l

3. **Tata Nexon EV**
   - Price: ₹8,00,000
   - Fuel: EV
   - Safety Rating: 4.2/5
   - Ground Clearance: 200mm
   - Mileage: 150 km/charge

#### SUVs
1. **Mahindra XUV500**
   - Price: ₹12,00,000
   - Fuel: Diesel
   - Safety Rating: 4.5/5
   - Ground Clearance: 210mm
   - Mileage: 16.5 km/l

2. **Kia Sonet**
   - Price: ₹11,00,000
   - Fuel: Petrol
   - Safety Rating: 4.3/5
   - Ground Clearance: 200mm
   - Mileage: 18.2 km/l

3. **BYD Atto 3**
   - Price: ₹15,00,000
   - Fuel: EV
   - Safety Rating: 4.8/5
   - Ground Clearance: 220mm
   - Mileage: 180 km/charge

4. **Hyundai Creta**
   - Price: ₹11,50,000
   - Fuel: Diesel
   - Safety Rating: 4.4/5
   - Ground Clearance: 205mm
   - Mileage: 17.0 km/l

#### Sedans
1. **Honda Accord**
   - Price: ₹18,00,000
   - Fuel: Petrol
   - Safety Rating: 4.6/5
   - Ground Clearance: 155mm
   - Mileage: 16.0 km/l

2. **Skoda Superb**
   - Price: ₹22,00,000
   - Fuel: Diesel
   - Safety Rating: 4.9/5
   - Ground Clearance: 160mm
   - Mileage: 15.5 km/l

3. **Tesla Model 3**
   - Price: ₹35,00,000
   - Fuel: EV
   - Safety Rating: 5.0/5
   - Ground Clearance: 150mm
   - Mileage: 200 km/charge

## Running the Application

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Test Health Endpoint
```bash
curl http://localhost:8080/api/cars/health
```

### Test Recommendation Endpoint
```bash
curl -X POST http://localhost:8080/api/cars/recommend \
  -H "Content-Type: application/json" \
  -d '{
    "budget": 1500000,
    "monthlyDistance": "high",
    "drivingPattern": "Mixed",
    "roadReality": "rough",
    "travelGroup": "family",
    "ownershipMindset": "safety",
    "fuelType": "Diesel",
    "vehicleType": "SUV"
  }'
```

## Features

✅ **Modular Architecture:**
- Separate controllers, services, and models for clean code
- Easy to extend with new cars or logic

✅ **Intelligent Scoring:**
- Multi-factor scoring based on user preferences
- Weighted scoring for different use cases

✅ **Dynamic Reasons:**
- Generates user-readable explanations for recommendations
- Combines multiple factors into coherent sentences

✅ **CORS Enabled:**
- Ready for Angular frontend integration
- Accepts requests from any origin

✅ **Budget Filtering:**
- Automatically filters cars within user budget

✅ **Flexible Configuration:**
- Server port configurable via environment variables
- Default port: 8080

## Configuration

Edit `application.properties`:
```properties
server.port=${PORT:8080}
spring.application.name=drivewise-ai
```

## Future Enhancements

- Database integration (PostgreSQL/MongoDB)
- User authentication
- Saved recommendations/wishlist
- Real-time car price updates
- Advanced filtering options
- Machine learning-based recommendations
- User reviews and ratings

## Technologies Used
- Spring Boot 3.x
- Spring Web MVC
- Java 17
- Maven

## License
MIT License

