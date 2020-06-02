SELECT 
	sensor_groups.company_id,
	sensor_groups.sensor_group_id, 
	sensor_groups.client_id,
	sensor_groups.secret_key
FROM 
	sensor_maker_authentications 
INNER JOIN sensor_groups 
	ON sensor_maker_authentications.company_id = sensor_groups.company_id
	AND sensor_maker_authentications.sensor_group_id = sensor_groups.sensor_group_id
WHERE auth_key = /* authKey */'1'