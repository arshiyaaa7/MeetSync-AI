# MeetSync AI - Meeting Intelligence Service

- Live Deployment URL: https://meetsync-ai-production.up.railway.app
- Swagger/OpenAPI URL: https://meetsync-ai-production.up.railway.app/swagger-ui/index.html

# Overview

AI-powered backend service for meeting analysis, action item extraction, overdue tracking, and automated reminder workflows.

Built using:

* Java 21
* Spring Boot
* PostgreSQL
* Groq AI
* Discord Webhooks
* JWT Authentication

# Features

## Authentication

* JWT-based authentication
* Protected APIs
* Swagger authorization support
* Login / Register / Logout APIs

## Meeting Intelligence

AI-generated:

* summaries
* action items
* decisions
* follow-up suggestions

All outputs include transcript citations.

## Action Item Management

Supports:

* create action items
* update status
* fetch action items
* filter by:

  * status
  * assignee
  * meetingId

Supported statuses:

* PENDING
* IN_PROGRESS
* COMPLETED

## Overdue Detection

An action item becomes overdue when:

```text id="r8yo6n"
status != COMPLETED
AND
dueDate < current time
```

Endpoint:

```http id="jlwmz7"
GET /api/action-items/overdue
```

## Reminder Workflow

Implemented using Spring Scheduled Jobs.

Workflow:

1. detect overdue action items
2. send Discord reminders
3. persist reminder history

Runs every 60 seconds.

## External Integration

Integrated with:

* Discord Webhook API

Used actively in reminder notifications.

---

# Tech Stack

| Technology      | Purpose        |
| --------------- | -------------- |
| Java 21         | Backend        |
| Spring Boot     | API Framework  |
| Spring Security | Authentication |
| PostgreSQL      | Database       |
| Groq API        | AI Analysis    |
| Discord Webhook | Notifications  |
| Swagger/OpenAPI | API Docs       |
| Render          | Deployment     |

---

# Environment Variables

```env id="jlwm9u"
DB_URL=

DB_USERNAME=

DB_PASSWORD=

GROQ_API_KEY=

DISCORD_WEBHOOK_URL=

JWT_SECRET=
```

---

## Swagger Authorization

1. Login
2. Copy token
3. Click "Authorize"
4. Paste token only

Correct:

```text id="u91sqt"
eyJhbGciOi...
```

Wrong:

```text id="xmc8wn"
Bearer eyJhbGciOi...
```

---

# API Endpoints

| Method | Endpoint                        |
| ------ | ------------------------------- |
| POST   | `/api/auth/register`            |
| POST   | `/api/auth/login`               |
| POST   | `/api/auth/logout`              |
| POST   | `/api/meetings`                 |
| POST   | `/api/action-items`             |
| PATCH  | `/api/action-items/{id}/status` |
| GET    | `/api/action-items`             |
| GET    | `/api/action-items/overdue`     |
| GET    | `/api/health`                   |

---

# Technical Decisions

## Database

Selected:

* PostgreSQL (Supabase)

Why:

* strong relational support
* reliable persistence
* easy Spring Boot integration

---

## Authentication

Selected:

* JWT Authentication

Why:

* stateless
* scalable
* Swagger-friendly

---

## AI Provider

Selected:

* Groq API

Why:

* fast inference
* simple REST integration
* structured JSON output

---

## External Integration

Selected:

* Discord Webhook API

Why:

* simple setup
* real-time notifications
* lightweight integration

---

# AI Approach

## Prompt Design

The AI is instructed to:

* use transcript-only information
* avoid hallucinations
* return JSON-only responses
* include citations for every generated item

---

## Hallucination Prevention

Implemented:

* strict prompt constraints
* low temperature (`0.1`)
* transcript grounding
* citation enforcement

---

## Output Validation

Validation includes:

* DTO validation
* JSON parsing validation
* citation validation

---

## Known Limitations

* large transcripts may increase latency
* AI quality depends on transcript clarity

---

# Logging & Traceability

Implemented:

* structured logging
* request trace IDs
* MDC logging context
* centralized exception handling

---

# Testing

## Validation Testing

Tested:

* invalid emails
* blank fields
* invalid statuses
* invalid UUIDs

---

## AI Testing

Tested:

* very small transcripts
* very large transcripts
* ambiguous statements
* missing assignees

Verified:

* unsupported outputs were not generated

---

## Scheduler Testing

Tested:

* overdue detection
* Discord reminder delivery
* duplicate reminder prevention

---

# Changelog

## Version 1.0.0

Implemented:

* JWT authentication
* Groq AI integration
* transcript analysis
* action item management
* overdue detection
* Discord reminders
* logging & trace IDs
* Swagger documentation
* validation & exception handling

---

# Submission Checklist

| Requirement               | Status |
| ------------------------- | ------ |
| Public GitHub repository  | [✔]    |
| Public deployment         | [✔]    |
| Swagger documentation     | [✔]    |
| Authentication            | [✔]    |
| Global exception handling | [✔]    |
| Unified API responses     | [✔]    |
| Trace IDs                 | [✔]    |
| Meeting analysis endpoint | [✔]    | 
| Transcript citations      | [✔]    |
| Hallucination prevention  | [✔]    |
| Action item management    | [✔]    |
| Overdue detection         | [✔]    |
| Scheduled reminder job    | [✔]    |
| Third-party integration   | [✔]    |
| Reminder notifications    | [✔]    |
| Input validation          | [✔]    |

---

Completed By Arshiya Shaikh
