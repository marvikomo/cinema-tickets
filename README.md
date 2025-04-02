# Cinema Tickets Service

An implementation of a cinema ticket booking service that enforces specific business rules for ticket purchases, validates booking requests, calculates payments, and reserves seats.

---

## Business Rules Implemented

- Three ticket types with associated pricing:
    - **Adult**: £25
    - **Child**: £15
    - **Infant**: £0
- Maximum of **25 tickets per purchase** transaction
- **Infants do not require tickets or seats** (they sit on an adult's lap)
- **Child and Infant tickets require at least one Adult ticket**
- **Each Infant must be accompanied by one Adult**

---

##  Architecture & Design

The implementation follows clean architecture and SOLID principles:

1. **Separation of Concerns** – Each validation rule is encapsulated in its own class.
2. **Open/Closed Principle** – New validation rules can be added without modifying existing code.
3. **Single Responsibility Principle** – Each class has a clear, focused responsibility.
4. **Central Configuration** – Configurable values are managed via a single configuration class.
5. **Clear Exception Handling** – Custom exceptions with descriptive error messages.
6. **Comprehensive Logging** – Uses structured logging for observability and debugging.

---

### Key Components

- `TicketServiceImpl` – Main service implementation for processing ticket purchases.
- `TicketServiceConfig` – Central class for configurable settings.
- `TicketRequestValidator` – Chain of Responsibility coordinator for validations.
- `TicketCalculationService` – Handles cost and seat calculation logic.

#### Validators:

- `AccountValidator` – Validates account ID.
- `AdultPresenceValidator` – Ensures at least one adult is present with child/infant tickets.
- `InfantAdultRatioValidator` – Validates infant-to-adult ratio.
- `NonEmptyRequestValidator` – Ensures ticket request is not empty.
- `PositiveQuantityValidator` – Ensures all ticket quantities are positive.
- `TicketCountValidator` – Enforces maximum number of tickets per transaction.

---

## Logging

Uses SLF4J for structured logging with the following levels:
•	ERROR – Critical issues that prevent normal execution
•	INFO – Key business events (e.g., ticket purchases, config changes)

## Installation & Usage

1. Clone the repository
2. Build the project using `mvn clean install`
3. Run test using `mvn test`