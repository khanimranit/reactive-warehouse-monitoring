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

```
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
```

---

### 🔹 Core Components

#### 1️⃣ Reactive UDP Listener
- Listens on configurable UDP port
- Parses incoming messages
- Publishes events into a `Flux` pipeline

#### 2️⃣ Strategy Pattern

Each sensor type has its own processing logic:

```
SensorStrategy (interface)
        |
        |--- TemperatureSensor
        |--- HumiditySensor
```

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

```
reactive-warehouse-monitoring/
│
├── src/main/java/com/example/warehouse
│   ├── config/
│   ├── listener/
│   ├── model/
│   ├── service/
│   ├── simulator/
    ├── strategy/
│   └── ReactiveWarehouseApplication.java
│
├── src/main/resources/
│   └── application.yml
│
├── src/test/java/
│
└── pom.xml
```

---

## ⚙️ Configuration

Example `application.yml`:

```yaml
warehouse:
  sensors:
    temperature:
      port: 3344
      threshold: 35
      simulation:
        min-value: 30
        max-value: 39
        interval-ms: 5000
    humidity:
      port: 3355
      threshold: 50
      simulation:
        min-value: 40
        max-value: 54
        interval-ms: 7000
```

---

## 🔄 How It Works

1. UDP listener receives message:

```
TEMP:35
HUM:55
```

2. Message is parsed into `SensorReading`
3. Appropriate strategy is selected
4. Threshold is evaluated
5. Alert is logged reactively

---

## ▶️ Running the Application

### 1️⃣ Clone the repository

```bash
git clone https://github.com/your-username/reactive-warehouse-monitoring.git
cd reactive-warehouse-monitoring
```

### 2️⃣ Build the project

```bash
mvn clean install
```

### 3️⃣ Run the application

```bash
mvn spring-boot:run
```


---
