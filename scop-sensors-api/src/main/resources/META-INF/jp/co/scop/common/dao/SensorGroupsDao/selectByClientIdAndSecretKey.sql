SELECT 
	company_id,
	sensor_group_id,
	client_id,
	secret_key,
	sensor_group_name
FROM
	sensor_groups
WHERE 
	client_id = /* clientId */'1'
	AND secret_key = /* secretKey */'2'
	AND delete_flg = 0
;
	