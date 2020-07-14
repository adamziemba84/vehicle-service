DELETE FROM user_roles;
DELETE FROM users;

-- INSERT INTO users (id, first_name, last_name, email, password, roles, enabled, last_login, created_at, updated_at) VALUES
--   (1, 'John', 'Kowalski', 'john@test.com', 'demo', 'ADMIN_ROLE',1, DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW()), DATEADD('DAY', -5, NOW())),
--   (2, 'Ben', 'Klock', 'ben@test.com', 'demo', 'ADMIN_ROLE', 1, DATEADD('DAY', -45, NOW()), DATEADD('DAY', -15, NOW()), DATEADD('DAY', -15, NOW()))
--   ;

INSERT INTO users (id, first_name, last_name, email, password, enabled, last_login, created_at, updated_at) VALUES
  (1, 'John', 'Kowalski', 'john@test.com', 'demo',1, DATEADD('DAY', -25, NOW()), DATEADD('DAY', -5, NOW()), DATEADD('DAY', -5, NOW())),
  (2, 'Ben', 'Klock', 'ben@test.com', 'demo', 1, DATEADD('DAY', -45, NOW()), DATEADD('DAY', -15, NOW()), DATEADD('DAY', -15, NOW()))
  ;

INSERT INTO user_roles (user_id, roles) VALUES
  (1, 'demo')
  ;