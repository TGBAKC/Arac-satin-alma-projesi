-- schema.sql
-- Database schema for Car Rental Application

CREATE TYPE user_role     AS ENUM ('ADMIN','INDIVIDUAL','CORPORATE');
CREATE TYPE customer_type AS ENUM ('INDIVIDUAL','CORPORATE');
CREATE TYPE vehicle_type  AS ENUM ('CAR','HELICOPTER','MOTORCYCLE');
CREATE TYPE rental_status AS ENUM ('ACTIVE','CANCELLED','COMPLETED');

CREATE TABLE app_user (
  id             BIGSERIAL PRIMARY KEY,
  full_name      VARCHAR(120) NOT NULL,
  age            INT NOT NULL CHECK (age >= 18),
  email          VARCHAR(180) NOT NULL UNIQUE,
  password_hash  VARCHAR(128) NOT NULL,
  role           user_role NOT NULL,
  customer_type  customer_type NOT NULL
);

CREATE TABLE vehicle (
  id           BIGSERIAL PRIMARY KEY,
  type         vehicle_type NOT NULL,
  brand        VARCHAR(80) NOT NULL,
  model        VARCHAR(80) NOT NULL,
  value_tl     BIGINT NOT NULL CHECK (value_tl > 0),
  price_hour   BIGINT NOT NULL,
  price_day    BIGINT NOT NULL,
  price_week   BIGINT NOT NULL,
  price_month  BIGINT NOT NULL,
  is_active    BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE rental (
  id            BIGSERIAL PRIMARY KEY,
  user_id       BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
  vehicle_id    BIGINT NOT NULL REFERENCES vehicle(id) ON DELETE CASCADE,
  start_ts      TIMESTAMP NOT NULL,
  end_ts        TIMESTAMP NOT NULL,
  status        rental_status NOT NULL DEFAULT 'ACTIVE',
  deposit_amt   BIGINT NOT NULL DEFAULT 0,
  total_price   BIGINT NOT NULL DEFAULT 0,
  CHECK (start_ts < end_ts)
);
