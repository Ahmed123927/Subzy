# Subzy – SaaS Subscription & Billing Platform

## 📌 Overview

Subzy is a multi-tenant SaaS platform designed for managing subscriptions, billing, and recurring payments. The system is built with a clean architecture using Spring Boot and integrates Stripe for secure online payments and automated invoicing.

---

## 🚀 Core Features

### 🔹 Subscription Management

* Create, update, and cancel subscriptions.
* Support for multiple plans (Monthly / Yearly / Trial).
* Auto-renewing subscriptions.

### 🔹 Billing & Invoicing

* Automatic invoice generation after each successful renewal.
* Invoice status tracking (paid / pending / failed).
* Optional email notifications.

### 🔹 Stripe Integration

* Stripe Checkout & Payment Intents.
* Webhooks for handling payment success and failure events.
* Stores all Stripe transaction IDs securely in the database.

### 🔹 Authentication & Authorization

* JWT-based login and session handling.
* Multi-tenant architecture (each tenant has isolated data).
* Role-based access for Admin endpoints.

### 🔹 Admin Dashboard

Using Swagger or any UI client (like React):

* View real-time subscription data.
* Revenue analytics (MRR, ARR, Active Users).
* Daily new subscription counts.
* Failed payment monitoring.

### 🔹 API Documentation

* Fully documented using **Swagger / OpenAPI**.

### 🔹 Subscription Plans

Subzy provides a flexible subscription model that allows each company (tenant) to define and customize their own pricing plans:

* **Basic Plan** – Monthly recurring billing.
* **Pro Plan** – Monthly or yearly billing with added features.
* **Enterprise Plan** – Custom pricing with advanced capabilities.
* **Free Trial** – Optional trial period for new users.

Each plan contains:

* Name
* Price
* Currency
* Billing interval (monthly / yearly)
* Trial period (optional)
* Stripe price ID

---

### 🔹 Tenant / Company Management

Subzy is built as a **multi-tenant SaaS platform** where companies can register and manage their entire subscription workflow.

Each company (tenant) can:

* Register an account for their business.
* Define their own subscription plans.
* Onboard customers to subscribe to their plans.
* Manage their customers' subscriptions.
* View customer billing history and invoices.
* Track revenue metrics for their company only.

Key tenant features:

* **Isolated data per company** – using `tenantId` for each record.
* **Separate subscription plans per company**.
* **Admin panel for each company** to track metrics like:

  * Active customers
  * Monthly recurring revenue (MRR)
  * Number of active plans
  * Failed payments

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot 3** (Web, Data JPA, Security)
* **PostgreSQL**
* **JWT Authentication**
* **Stripe API**
* **Lombok**
* **Swagger / OpenAPI 3**

---


## ⚙️ How It Works

### 1️⃣ User Registration & Login

* User registers an account.
* System issues a signed JWT token.

### 2️⃣ Creating a Subscription

* User selects a plan.
* Backend creates a Stripe Checkout Session.
* User completes payment → Stripe triggers a Webhook → Subscription is activated.

### 3️⃣ Recurring Billing

* Stripe auto-renews subscriptions based on the plan.
* Webhook events handle:

  * Invoice creation
  * Subscription status updates
  * Transaction logging

---

## 🧪 Example API Endpoints

### 🔹 Authentication

```
POST /api/auth/register
POST /api/auth/login
```

### 🔹 Subscriptions

```
POST   /api/subscriptions/create
GET    /api/subscriptions/{id}
GET    /api/subscriptions/user/{userId}
POST   /api/subscriptions/cancel/{id}
```

### 🔹 Stripe Payments

```
POST /api/payments/create-checkout-session
POST /api/payments/webhook
```

### 🔹 Admin Metrics

```
GET /api/admin/metrics/revenue
GET /api/admin/metrics/subscriptions
```

---

## 🧰 Configuration

Example `application.yml` setup:

```
stripe:
  secret-key: YOUR_SECRET_KEY
  webhook-secret: YOUR_WEBHOOK_SECRET
jwt:
  secret: YOUR_JWT_SECRET
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/subzy
    username: postgres
    password: yourpassword
```

---

## 📦 Database Schema (Simplified)

* **users** (id, name, email, password, tenantId)
* **subscriptions** (id, userId, plan, status, renewAt)
* **invoices** (id, subscriptionId, amount, status, stripePaymentId)
* **payment_logs** (id, eventType, rawJson)

---

## 🧩 Future Enhancements

* Support for coupons and discount codes.
* Email notifications (SendGrid or Mailgun).
* Multi-currency support.
* Full React admin dashboard.

---

## 👨‍💻 Author

**Ahmed Hussein** – Java Backend Developer


