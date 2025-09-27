# Car Rental Application (Capstone Project)

This project is a **terminal-based car rental system** developed with **Java 21** and **PostgreSQL 17**.  
The goal is to practice layered architecture, database integration, and modern software development principles.

---

## 🚀 Features
- **Authentication**
    - Register & Login with email and password
    - Passwords are securely stored using SHA-256 hashing
- **Roles & Authorization**
    - `ADMIN`: Vehicle CRUD (add, list, update, delete)
    - `CUSTOMER` (Individual / Corporate): Search vehicles, start rental, view and cancel rentals
- **Vehicles**
    - Types: Car, Motorcycle, Helicopter
- **Business Rules**
    - Corporate customers must rent for **minimum 30 days**
    - If vehicle value > 2,000,000 TL → Customer must be **≥30 years old** and pay **10% deposit**
    - Prevent overlapping bookings for the same vehicle
- **Pricing**
    - Hourly, Daily, Weekly, Monthly rates
- **Search & Pagination**
    - Filter by type, brand, and price range
    - Paginated results for better navigation

---

## 🛠️ Technologies
- Java 21
- PostgreSQL 17
- JDBC (postgresql-42.7.8.jar)
- IntelliJ IDEA

---

## 📂 Project Structure
- **model** → Entity classes (`User`, `Vehicle`, `Rental`, etc.)
- **repository** → Database access (`JdbcUserRepository`, `JdbcVehicleRepository`, `JdbcRentalRepository`)
- **service** → Business logic (`AuthService`, `VehicleService`, `RentalService`)
- **ui** → Console menus (`Menu`, `AdminMenu`, `CustomerMenu`)
- **Main.java** → Application entry point

---

## 📊 Database Schema
Tables:
- **app_user**
    - `id, full_name, email, password_hash, role (enum:user_role), age, customer_type`
- **vehicle**
    - `id, type, brand, model, value_tl, price_hour, price_day, price_week, price_month, is_active`
- **rental**
    - `id, user_id, vehicle_id, start_ts, end_ts, status (enum:rental_status), deposit_amt, total_price`

---

## ▶️ How to Run
1. Install PostgreSQL and create a database named `rentals`
2. Run the SQL script to create tables (`app_user`, `vehicle`, `rental`)
3. Open the project in IntelliJ IDEA
4. Run `Main.java`
5. Use the console menus:
    - Register / Login
    - If ADMIN → manage vehicles
    - If CUSTOMER → search and rent vehicles

---

## 👩‍💻 Developer
- **Name**: Tuğba Akca
- **Project**: Capstone Project – Car Rental System

## 🗄️ Database Setup
1. Create database:
   ```sql
   CREATE DATABASE rentals;


