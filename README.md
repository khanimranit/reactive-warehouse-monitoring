# 🚀 Reactive Warehouse Monitoring System

A reactive Spring Boot application designed to monitor warehouse sensors (Temperature & Humidity) over UDP using Spring WebFlux, Project Reactor, and the Strategy Pattern.

The system reads sensor values, evaluates them against configurable thresholds, and logs alerts in real time.

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [How It Works](#-how-it-works)
- [Running the Application](#-running-the-application)
- [Testing Sensor Messages](#-testing-sensor-messages)
- [Unit Testing](#-unit-testing)
- [Future Enhancements](#-future-enhancements)

---

## 🧭 Overview

This application listens for UDP-based sensor data (Temperature and Humidity).

It processes incoming sensor readings using a flexible **Strategy Pattern**, evaluates them against configured thresholds, and logs alerts — all using a fully **non-blocking reactive pipeline** powered by Spring WebFlux.

---

## ⭐ Features

- ✅ Reactive UDP sensor listener
- ✅ Strategy Pattern for flexible, pluggable sensor logic
- ✅ Real-time threshold evaluation
- ✅ Externalized configuration via `application.yml`
- ✅ Fully non-blocking using Project Reactor
- ✅ Unit-tested threshold logic
- ✅ Easily extendable for new sensor types
- ✅ Clean layered architecture

---

## 🏗️ Architecture

### High-Level Flow

Warehouse Sensors
|
| UDP Messages
v
Reactive UDP Listener (WebFlux + Netty)
|
v
Sensor Processor (Strategy Pattern)
|
v
Threshold Evaluator
|
v
Alert Logger



---

### 🔹 Core Components

#### 1️⃣ Reactive UDP Listener
- Listens on configurable UDP port
- Parses incoming messages
- Publishes events into a `Flux` pipeline

#### 2️⃣ Strategy Pattern
Each sensor type has its own processing logic:

SensorStrategy (interface)
|
|--- TemperatureSensor
|--- HumiditySensor



This makes the system:
- Open for extension
- Closed for modification
- Easy to add new sensor types (e.g., Pressure, CO2, Light)

#### 3️⃣ Threshold Evaluator
Reads min/max limits from configuration and determines:

- NORMAL
- WARNING
- CRITICAL

#### 4️⃣ Alert Logger
Logs alerts to console (can be extended to Kafka, DB, Email, etc.)

---

## 📁 Project Structure

eactive-warehouse-monitoring/
│
├── src/main/java/com/example/warehouse
│ ├── config/
│ ├── listener/
│ ├── model/
│ ├── service/
│ ├── strategy/
│ └── ReactiveWarehouseApplication.java
│
├── src/main/resources/
│ └── application.yml
│
├── src/test/java/
│
└── pom.xml




---

## ⚙️ Configuration

Example `application.yml`:

```yaml
server:
  port: 8080

udp:
  port: 9999

sensor:
  temperature:
    min: 5
    max: 40
  humidity:
    min: 20
    max: 70




UDP listener receives message:

TEMP:35
HUM:55


Message is parsed into SensorReading

Appropriate strategy is selected

Threshold is evaluated

Alert is logged reactively

▶️ Running the Application
1️⃣ Clone the repository
git clone https://github.com/your-username/reactive-warehouse-monitoring.git
cd reactive-warehouse-monitoring

2️⃣ Build the project
mvn clean install

3️⃣ Run the application
mvn spring-boot:run
