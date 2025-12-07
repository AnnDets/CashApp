INSERT INTO users (id, email, username, password)
VALUES (uuid_generate_v4(), 'alex.johnson@example.com', 'alexjohnson', '1234'),
       (uuid_generate_v4(), 'maria.garcia@example.com', 'mariagarcia', 'qwer'),
       (uuid_generate_v4(), 'david.smith@example.com', 'davidsmith', 'zxcv');