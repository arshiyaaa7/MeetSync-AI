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

---

# Architecture

The backend follows a clean layered architecture using Controller, Service, and Repository layers to maintain separation of concerns and improve maintainability. Controllers handle API requests, Services contain business logic and AI workflows, while Repositories manage database interactions using Spring Data JPA.

<img width="1087" height="536" alt="Screenshot 2026-06-08 121113" src="https://github.com/user-attachments/assets/499dcef4-3f9c-4daa-aa5d-3673564997d6" />

---
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

<img width="2879" height="1383" alt="image" src="https://github.com/user-attachments/assets/7332e006-8195-4620-85f3-4f98a302d446" />


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
