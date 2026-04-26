# Hotel Search Service

Spring Boot app for hotel availability searches. Uses hexagonal architecture, Kafka for async processing, and PostgreSQL to persist searches.

## Requirements

Just Docker and Docker Compose. The app compiles inside Docker.

## Running the app

```bash
docker-compose up --build
```

That's it. Starts the app on port 8080, PostgreSQL, and Kafka.

## Endpoints

### POST /search

Registers a new search and publishes it to Kafka. The consumer picks it up and saves it to the DB.

```json
{
  "hotelId": "1234aBc",
  "checkIn": "2023-12-29",
  "checkOut": "2023-12-31",
  "ages": [30, 29, 1, 3]
}
```

Returns `201` with the generated search ID:

```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### GET /count?searchId={searchId}

Given a searchId, returns how many identical searches exist.

```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000",
  "search": {
    "hotelId": "1234aBc",
    "checkIn": "2023-12-29",
    "checkOut": "2023-12-31",
    "ages": [30, 29, 1, 3]
  },
  "count": 5
}
```

Note: `[30, 29]` and `[29, 30]` are treated as different searches.

## Swagger

Once running, docs are at:

```
http://localhost:8080/swagger-ui.html
```

## Coverage report (JaCoCo)

```bash
./mvnw verify
```

Report generated at `target/site/jacoco/index.html`. Build enforces 80% minimum on lines, branches and methods.

## Architecture notes

Hexagonal architecture — no modules, just packages:

```
domain/          → models, exceptions, port interfaces
application/     → use cases
infrastructure/  → REST controllers, Kafka adapter, JPA adapter
```

A few things worth mentioning:
- `HotelSearch` is an immutable record — validation runs in the compact constructor
- The HTTP request returns immediately after publishing to Kafka, the consumer saves to DB asynchronously
- Virtual threads enabled for better throughput under load
