-- seed.sql
-- Insert sample data for Car Rental Application

-- SHA-256 hash of "123456":
-- 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92

-- Users
INSERT INTO app_user (full_name, age, email, password_hash, role, customer_type) VALUES
('Admin User', 35, 'admin@demo.com',
 '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
 'ADMIN', 'INDIVIDUAL'),

('Corporate User', 40, 'corp@demo.com',
 '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
 'CORPORATE', 'CORPORATE'),

('Individual User', 28, 'indi@demo.com',
 '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
 'INDIVIDUAL', 'INDIVIDUAL');

-- Vehicles
INSERT INTO vehicle (type, brand, model, value_tl, price_hour, price_day, price_week, price_month, is_active) VALUES
('CAR', 'Tesla', 'Model 3', 1500000, 500, 5000, 20000, 60000, TRUE),
('CAR', 'BMW', 'X5', 2000000, 700, 7000, 25000, 80000, TRUE),
('MOTORCYCLE', 'Yamaha', 'R1', 800000, 200, 2000, 7000, 20000, TRUE),
('MOTORCYCLE', 'Harley-Davidson', 'Street 750', 1200000, 300, 3000, 10000, 30000, TRUE),
('HELICOPTER', 'Airbus', 'H125', 5000000, 5000, 40000, 120000, 400000, TRUE),
('HELICOPTER', 'Bell', '206', 3500000, 4000, 30000, 100000, 300000, TRUE);
