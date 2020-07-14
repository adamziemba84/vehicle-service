DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM cars;
DELETE FROM models;
DELETE FROM auctions;
DELETE FROM manufacturers;

INSERT INTO users (id, first_name, last_name, email, password, enabled, last_login, created_at, updated_at) VALUES
  (1, 'John', 'Kowalski', 'john@test.com', 'demo',1, DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW()), DATEADD('DAY', -5, NOW())),
  (2, 'Ben', 'Klock', 'ben@test.com', 'demo', 1, DATEADD('DAY', -45, NOW()), DATEADD('DAY', -15, NOW()), DATEADD('DAY', -15, NOW()))
  ;

INSERT INTO user_roles (user_id, roles) VALUES
  (1, 'demo')
  ;


-- Main Domain

INSERT INTO auctions (id, name, created_at, updated_at) VALUES
  (1, 'low', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (2, 'high', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW()))
;

INSERT INTO manufacturers (id, name, created_at, updated_at) VALUES
  (1, 'Seat', DATEADD('DAY', -20, NOW()), DATEADD('DAY', -1, NOW())),
  (2, 'Fiat', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -7, NOW())),
  (3, 'Toyota', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (4, 'Toyota', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (5, 'Lexus', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (6, 'Ford', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (7, 'Opel', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (8, 'Skoda', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (9, 'Dacia', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (10, 'Aston Martin', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (11, 'BMW', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (12, 'Bentley', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (13, 'Audi', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW()))
;

INSERT INTO models (id, manufacturer_id, name, created_at, updated_at) VALUES
  (1, 2, 'Ibiza', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (2, 1, 'Leon', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (3, 2, 'Panda', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (4, 2, 'Tipo', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (5, 3, 'Corolla', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (6, 3, 'Auris', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW()))
;

INSERT INTO cars (id, auction_id, manufacturer_id, model_id, car_type, manufacture_year, mileage, fuel_type, vin, basic_price, engine_size, country, created_at, updated_at) VALUES
  (1, 1, 1, 1, 3, 2014, 14000, 1, '2BXJBWC19BV000956', 22000, 1396, 'DE', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (2, 1, 1, 2, 3, 2012, 140000, 2, '3FADP4FJ4BM176902', 25000, 1596, 'DE', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (3, 1, 2, 3, 1, 2004, 280000, 2, '2C4RDGCG9DR595701', 5000, 1999, 'IT', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (4, 1, 2, 4, 1, 2010, 100300, 1, '1FTPX14V28FB73839', 15000, 1100, 'GB', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (5, 1, 3, 5, 1, 2012, 15200, 2, '3D4PG5FV2AT249806', 17000, 1233, 'PL', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (6, 2, 3, 6, 1, 2011, 113000, 2, 'YV1MS390092449965', 13000, 1366, 'PL', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (7, 2, 3, 5, 1, 2008, 114000, 1, '4S3BMBK68A3282446', 8000, 1396, 'IT', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (8, 2, 3, 6, 1, 2009, 204000, 2, '1G8MC35B48Y178859', 9000, 1569, 'DE', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (9, 2, 3, 6, 1, 2008, 214000, 2, '5XYZU3LB7FG210754', 8500, 1298, 'DE', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW())),
  (10, 2, 3, 6, 1, 2018, 12000, 1, 'JT6HF10U4X0079166', 35500, 1198, 'DE', DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW()))
  ;