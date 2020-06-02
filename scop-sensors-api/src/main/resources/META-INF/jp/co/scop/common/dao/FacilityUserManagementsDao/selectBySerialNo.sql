SELECT
	managements.company_id AS company_id,
	managements.facility_user_id AS facility_user_id,
	managements.facility_user_management_id AS facility_user_management_id,
	sensors.serial_no AS serial_no,
	products.sensor_type AS sensor_type,
	facility_users.last_name,
	facility_users.first_name
FROM 
	facility_user_managements AS managements
	INNER JOIN
		facility_users
		ON managements.company_id = facility_users.company_id
		AND managements.facility_user_id = facility_users.facility_user_id
	INNER JOIN 
		sensors 
		ON managements.company_id = sensors.company_id
		and managements.sensor_id = sensors.sensor_id
	INNER JOIN
		products
		ON products.product_id = sensors.product_id
WHERE
	products.sensor_type = /* condition.sensorType */'000'
	AND sensors.serial_no = /* condition.serialNo */'123'
	AND managements.delete_flg = 0
	AND sensors.delete_flg = 0
	AND products.delete_flg = 0
	AND	managements.usage_period_from <= /* condition.date */'2019-01-01 00:00:00'
	AND (
		/* condition.date */'2019-01-01 00:00:00' <= managements.usage_period_to
		OR managements.usage_period_to IS NULL
	)
LIMIT 1
;