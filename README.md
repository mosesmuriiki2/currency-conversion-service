# Currency Conversion Microservices

This project consists of two microservices:
1. **Rate Service** - Fetches currency exchange rates from an external API
2. **Main Service** - Handles currency conversion requests using rates from the Rate Service

## Architecture

The system uses a microservices architecture with the following components:

- **Rate Service**: Connects to an external exchange rate API to fetch current rates
- **Main Service**: Provides currency conversion functionality and stores conversion history
- **PostgreSQL Database**: Stores conversion records
- **Docker**: Containerizes all services for easy deployment

## Prerequisites

- Java 17
- Maven
- Docker and Docker Compose
- Exchange Rate API Key (from https://www.exchangerate-api.com/)

## Getting an API Key

1. Visit [ExchangeRate-API](https://www.exchangerate-api.com/) and sign up for a free account
2. After registration, you'll be provided with an API key on your dashboard
3. Copy this API key for use in the `.env` file

## Setup and Running

### Environment Variables

Create a `.env` file in the root directory with the following content:

```
EXCHANGE_RATE_API_KEY=your_api_key_here
```

This API key will be passed to the Rate Service container through Docker Compose.

### Building the Application

1. Build the main-service:

```bash
cd main-service
mvn clean package -DskipTests
```

2. Build the rate-service:

```bash
cd rate-service
mvn clean package -DskipTests
```

### Running with Docker Compose

From the root directory, run:

```bash
docker-compose up -d
```

This will start:
- PostgreSQL database on port 5432
- Rate Service on port 8067
- Main Service on port 8068

### Running on Ubuntu

The steps to run on Ubuntu are similar to any other platform:

1. Ensure Docker and Docker Compose are installed:

```bash
sudo apt update
sudo apt install docker.io docker-compose
sudo systemctl enable --now docker
```

2. Clone the repository and navigate to the project directory

3. Create the `.env` file with your API key as described above

4. Build and run the services:

```bash
# Build the services (if not using pre-built images)
cd main-service
mvn clean package -DskipTests
cd ../rate-service
mvn clean package -DskipTests
cd ..

# Start all services
docker-compose up -d
```

5. Verify all services are running:

```bash
docker-compose ps
```

## Testing Locally

### Testing Without Docker

You can run and test each service locally without Docker:

1. Start a local PostgreSQL instance or use an existing one

2. Configure the Rate Service:
   - Set the environment variable: `EXCHANGE_RATE_API_KEY=your_api_key_here`
   - Run the service: `cd rate-service && mvn spring-boot:run`

3. Configure the Main Service:
   - Update application properties with your database connection details
   - Run the service: `cd main-service && mvn spring-boot:run`

### Testing the API Endpoints

Once the services are running (either with Docker or locally), you can test them:

## API Usage

### Authentication

Both services use Basic Authentication:
- Username: `user`
- Password: `password`

### Rate Service Endpoints

**Get Exchange Rate**
```
GET /api/rate?from={fromCurrency}&to={toCurrency}
```

Example:
```bash
curl -X GET "http://localhost:8067/api/rate?from=USD&to=EUR" -u user:password
```

Response:
```json
{
  "from": "USD",
  "to": "EUR",
  "rate": 0.85
}
```

### Main Service Endpoints

**Convert Currency**
```
POST /api/convert
```

Request Body:
```json
{
  "from": "USD",
  "to": "EUR",
  "amount": 100
}
```

Example:
```bash
curl -X POST "http://localhost:8068/api/convert" \
  -H "Content-Type: application/json" \
  -u user:password \
  -d '{"from":"USD","to":"EUR","amount":100}'
```

Response:
```json
{
  "from": "USD",
  "to": "EUR",
  "amount": 100,
  "convertedAmount": 85,
  "rate": 0.85
}
```

## Logging

- Main Service logs are available at `logs/main-service.log`
- Rate Service logs are available in the console output
- Both services log at DEBUG level for the application packages

## Testing

To run the tests:

```bash
# For main-service
cd main-service
mvn test

# For rate-service
cd rate-service
mvn test
```

## Troubleshooting

1. If services fail to connect, ensure all containers are running:
   ```bash
   docker-compose ps
   ```

2. Check logs for any errors:
   ```bash
   docker-compose logs main-service
   docker-compose logs rate-service
   ```

3. Ensure your Exchange Rate API key is valid and properly set in the .env file

4. If the database connection fails, you may need to wait a few seconds for PostgreSQL to fully initialize

5. If you encounter permission issues on Ubuntu:
   ```bash
   sudo chmod 666 /var/run/docker.sock
   ```

6. To verify the API key is correctly passed to the Rate Service:
   ```bash
   docker-compose logs rate-service | grep EXCHANGE_RATE_API_KEY
   ```