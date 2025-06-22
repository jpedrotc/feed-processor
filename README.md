# Feed Processor - Sports Betting Feed Standardization Service

A Spring Boot microservice that acts as a standardization layer for processing sports betting feed data from multiple third-party providers, transforming different proprietary formats into a unified internal structure.

## Overview

At Sporty Group, we process real-time sports betting data from various third-party feed providers. Each provider delivers data in its own proprietary format, and this microservice normalizes these messages into a consistent internal format for use across our betting platform.

This service handles two main types of messages:
- **ODDS_CHANGE**: Updates to betting odds for specific events
- **BET_SETTLEMENT**: Final results of markets for completed events

Currently, supports the **1X2 market** (Home team win, Draw, Away team win) format.

## Decisions

This architecture was built with **flexibility** and **long-term maintainability** in mind, anticipating the need to support multiple betting feed providers, each with their own message formats and business rules.

The design centers around a few key strengths:

### Core Architecture Highlights

- **Provider-Agnostic Processing**  
  Each provider’s message structure and business logic is fully encapsulated in its own package. This modular setup allows new providers to be integrated without modifying existing code.

- **Type-Safe Message Handling**  
  Shared interfaces like `Message` and `NormalizedMessage` enforce strong typing, enabling compile-time validation and reducing the risk of runtime errors.

- **Flexible Normalization Pipeline**  
  The `MessageNormalizer<T extends Message, R extends NormalizedMessage>` interface defines a consistent contract for transforming provider-specific messages into a standardized format, while allowing flexibility in implementation.

- **Scalable Processing Workflow**  
  The `MessageProcessor` orchestrates the normalization process by dynamically resolving the appropriate normalizer for each incoming message. This supports a wide variety of message formats and providers.

- **Swappable Infrastructure**  
  The `QueuePublisher` abstraction decouples infrastructure concerns from business logic. Messaging systems such as SQS, RabbitMQ, or Kafka can be integrated or replaced without impacting the core processing logic.

---

## Supported Providers

### Provider Alpha
- **Endpoint**: `POST /provider-alpha/feed`
- **ODDS_CHANGE Format**:
  ```json
  {
    "msg_type": "odds_update",
    "event_id": "ev123",
    "values": {
      "1": 2.0,
      "X": 3.1,
      "2": 3.8
    }
  }
  ```
- **BET_SETTLEMENT Format**:
  ```json
  {
    "msg_type": "settlement",
    "event_id": "ev123",
    "outcome": "1"
  }
  ```

### Provider Beta
- **Endpoint**: `POST /provider-beta/feed`
- **ODDS_CHANGE Format**:
  ```json
  {
    "type": "ODDS",
    "event_id": "ev456",
    "odds": {
      "home": 1.95,
      "draw": 3.2,
      "away": 4.0
    }
  }
  ```
- **BET_SETTLEMENT Format**:
  ```json
  {
    "type": "SETTLEMENT",
    "event_id": "ev456",
    "result": "away"
  }
  ```
---
## Standardized Output Format

Both providers' messages are transformed into these standardized domain models:

### NormalizedOddsMessage (ODDS_CHANGE)
```json
{
  "type" : "ODDS_CHANGE",
  "eventId": "ev123",
  "homeOdds": 2.0,
  "drawOdds": 3.1,
  "awayOdds": 3.8
}
```

**Fields:**
- `type` (String): Type of the message, it is always ODDS_CHANGE
- `eventId` (String): Unique identifier for the betting event
- `homeOdds` (double): Odds for home team win
- `drawOdds` (double): Odds for draw/tie
- `awayOdds` (double): Odds for away team win

### NormalizedBetMessage (BET_SETTLEMENT)
```json
{
  "type" : "BET_SETTLEMENT",
  "eventId": "ev123",
  "winner": "home"
}
```

**Fields:**
- `type` (String): Type of the message, it is always BET_SETTLEMENT
- `eventId` (String): Unique identifier for the betting event
- `winner` (String): Final outcome ("home", "draw", or "away")

## Getting Started

### Prerequisites
- Java 21 or higher
- Gradle 8.x
- Spring Boot 3.5.2

---

### Running the Application

1. **Clone and navigate to the project directory**
   ```bash
   cd feed-processor
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

The application will start on `http://localhost:8080`

### Running Tests

```bash
./gradlew test
```

---

### Testing the Endpoints

To test the endpoints, use the curl commands below. Normalized messages will be logged to the console.

**Provider Alpha - ODDS_CHANGE and BET_SETTLEMENT:**
```bash
curl -X POST http://localhost:8080/provider-alpha/feed \
  -H "Content-Type: application/json" \
  -d '{
    "msg_type": "odds_update",
    "event_id": "ev123",
    "values": {
      "1": 2.0,
      "X": 3.1,
      "2": 3.8
    }
  }'
  
curl -X POST http://localhost:8080/provider-alpha/feed \
  -H "Content-Type: application/json" \
  -d '{
    "msg_type": "settlement",
    "event_id": "ev123",
    "outcome": "1"
  }'
```

**Provider Beta - ODDS_CHANGE and BET_SETTLEMENT:**
```bash
curl -X POST http://localhost:8080/provider-beta/feed \
  -H "Content-Type: application/json" \
  -d '{
    "type": "ODDS",
    "event_id": "ev456",
    "odds": {
      "home": 1.95,
      "draw": 3.2,
      "away": 4.0
    }
  }'
  
curl -X POST http://localhost:8080/provider-beta/feed \
  -H "Content-Type: application/json" \
  -d '{
    "type": "SETTLEMENT",
    "event_id": "ev456",
    "result": "away"
  }'
```