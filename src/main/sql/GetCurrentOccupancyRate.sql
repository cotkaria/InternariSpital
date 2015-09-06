-- Get current occupancy rate
USE InternariSpital
SELECT COUNT(*) FROM Beds
SELECT COUNT(*) FROM Beds WHERE OCCUPANCY = 1