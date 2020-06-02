SELECT *
FROM products
WHERE 
sensor_type = /* sensorType */'020'
AND
delete_flg = 0
LIMIT 1
;