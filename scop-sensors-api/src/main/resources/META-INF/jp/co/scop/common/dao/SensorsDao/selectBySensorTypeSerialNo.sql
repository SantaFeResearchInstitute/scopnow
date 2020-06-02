SELECT 
	sensors.sensor_id
FROM sensors
INNER JOIN products
ON products.product_id = sensors.product_id
WHERE products.sensor_type = /* sensorType */'000' 
AND serial_no = /* serialNo */'serialNo'
AND sensors.delete_flg = 0
AND products.delete_flg = 0
LIMIT 1
;