#CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20),
#                                   lastName VARCHAR(20), age INT);
INSERT INTO users(name, lastName, age) VALUES('Alex', 'Fucker', 33);
SELECT * FROM users;
#UPDATE users set name = 'Steve', age = 55 where id = 1;
#DELETE FROM users;
#DROP TABLE users;
#SELECT id, name, lastName, age FROM users;