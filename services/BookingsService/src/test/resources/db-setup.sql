-- db-setup.sql

--
-- Insert testing data
-- Insert 1
INSERT INTO activity (activityId, type, name, description, price, creationDate, modificationDate)
VALUES (1, 'FOOTBALL', 'Football', 'Football activity', 25, '2024-01-01 08:00:00', '2024-01-01 08:00:00');

-- Insert 2
INSERT INTO activity (activityId, type, name, description, price, creationDate, modificationDate)
VALUES (2, 'TENNIS', 'Tennis', 'Tennis Activity', 15.50, '2023-01-02 10:00:00', '2024-01-02 10:00:00');

-- Insert 3
INSERT INTO activity (activityId, type, name, description, price, creationDate, modificationDate)
VALUES (3, 'POOL', 'Swimming Pool', 'Swimming Pool activity', 7.20, '2024-01-03 12:00:00', '2024-01-02 12:00:00');
