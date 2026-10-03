# Indomaret Master Data API

Backend REST API untuk manajemen master data Indomaret (Provinsi, Cabang, Toko) menggunakan Spring Boot.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1.1 |
| Security | Spring Security (HTTP Basic Auth) |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL |
| Build Tool | Maven |
| Utilities | Lombok |

## Prerequisites

- Java 17+
- Maven 3.8+
- MySQL (e.g. via XAMPP)

## Database Setup

1. Start your MySQL server.
2. Run the initialization script to create the database, tables, and seed data:

```sql
-- from the project root:
mysql -u root -p < db_init.sql
```

The script will:
- Create database `db_indomaret`
- Create tables: `provinces`, `branches`, `stores`, `users`
- Insert sample data for all tables
- Insert a default admin user (password is BCrypt-hashed)

## Configuration

Database connection is configured in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db_indomaret?userSSL=false&serverTimezone=UTC
    username: root
    password:        # set your MySQL root password here if applicable
```

Adjust `username` and `password` to match your local MySQL setup.

## Running the Application

```bash
./mvnw spring-boot:run
```

The server starts on **http://localhost:8080** by default.

## Authentication

All `/api/**` endpoints require **HTTP Basic Authentication**.

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `password123` |

The `admin` user is seeded by `db_init.sql`. The password is stored as a BCrypt hash in the `users` table.

Example with `curl`:
```bash
curl -u admin:password123 http://localhost:8080/api/stores
```

## API Documentation

### Province Endpoints

#### List All Provinces
```
GET /api/provinces
```
Returns all active and non-deleted provinces.

**Response:**
```json
{
  "status": 200,
  "message": "Berhasil mengambil data provinsi",
  "data": [
    { "id": 1, "name": "DKI Jakarta", "isActive": true, ... }
  ]
}
```

---

### Branch Endpoints

#### List All Branches
```
GET /api/branches
```
Returns all active and non-deleted branches, including their province.

**Response:**
```json
{
  "status": 200,
  "message": "Berhasil mengambil data cabang",
  "data": [
    { "id": 1, "name": "Cabang Jakarta Pusat", "province": { "name": "DKI Jakarta" }, ... }
  ]
}
```

---

#### Update Branch
```
PUT /api/branches/{id}
```
Updates the name (and optionally other fields) of an existing branch. Records the authenticated user as `updated_by`.

**Request Body:**
```json
{
  "name": "Nama Cabang Baru"
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Branch berhasil diupdate",
  "data": {
    "id": 1,
    "name": "Nama Cabang Baru",
    ...
  }
}
```

---

#### Delete Branch (Soft Delete)
```
DELETE /api/branches/{id}
```
Soft-deletes a branch by setting `is_deleted = true` and recording `deleted_at`. Records the authenticated user as `updated_by`.

**Response:**
```json
{
  "status": 200,
  "message": "Branch berhasil dihapus (soft-delete)",
  "data": null
}
```

---

### Store Endpoints

#### Search Stores
```
GET /api/stores?province={provinceName}
```
Returns a list of active stores. Optionally filter by province name (partial match, case-insensitive). Omit the query param to return all stores.

**Query Parameters:**

| Parameter | Required | Description |
|---|---|---|
| `province` | No | Province name filter (partial match) |

**Response:**
```json
{
  "status": 200,
  "message": "Berhasil mengambil data toko",
  "data": [
    {
      "id": 1,
      "name": "Indomaret Thamrin",
      "isWhitelisted": false,
      ...
    }
  ]
}
```

---

#### Update Store Whitelist Status
```
PUT /api/stores/{id}/whitelist?status={true|false}
```
Enables or disables the whitelist flag for a store. Records the authenticated user as `updated_by`.

**Query Parameters:**

| Parameter | Required | Description |
|---|---|---|
| `status` | Yes | `true` to whitelist, `false` to remove |

**Response:**
```json
{
  "status": 200,
  "message": "Status whitelist berhasil diubah",
  "data": {
    "id": 2,
    "name": "Indomaret Sudirman",
    "isWhitelisted": true,
    ...
  }
}
```

---

## Project Structure

```
src/main/java/com/indomaret/masterdata/
├── config/
│   └── SecurityConfig.java          # Spring Security configuration
├── controller/
│   ├── BranchController.java        # GET, PUT, DELETE /api/branches
│   ├── ProvinceController.java      # GET /api/provinces
│   └── StoreController.java         # GET /api/stores, PUT /api/stores/{id}/whitelist
├── dto/
│   ├── ApiResponse.java             # Generic API response wrapper
│   └── BranchRequest.java           # Request DTO for branch update
├── entity/
│   ├── Branch.java
│   ├── Province.java
│   ├── Store.java
│   └── User.java
├── repository/
│   ├── BranchRepository.java
│   ├── ProvinceRepository.java
│   ├── StoreRepository.java
│   └── UserRepository.java
├── exception/
│   └── GlobalExceptionHandler.java  # Returns 404 instead of 500 for not-found errors
└── service/
    ├── BranchService.java
    ├── StoreService.java
    └── UserDetailsServiceImpl.java  # Spring Security UserDetailsService
```
