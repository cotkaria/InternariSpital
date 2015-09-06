-- Count patients that have had more than one stay
USE InternariSpital
SELECT patient_Id FROM Hospitalization_Records GROUP BY patient_Id HAVING COUNT(*) > 1
SELECT COUNT(DISTINCT patient_Id) FROM Hospitalization_Records WHERE patient_Id IN (SELECT patient_Id FROM Hospitalization_Records GROUP BY patient_Id HAVING COUNT(*) > 1)