select 
	fum.company_id,
	fum.facility_user_id,
	fum.facility_user_management_id,
	p.product_id,
	p.sensor_type,
	s.sensor_id,
	s.serial_no
from 
	facility_user_managements fum
	INNER JOIN 
		sensors s
		ON 
		fum.company_id = s.company_id
		AND 
		fum.sensor_id = s.sensor_id
		AND
		s.delete_flg = 0
	INNER JOIN
		products p
		ON 
		s.product_id = p.product_id
		AND
		p.delete_flg = 0
where 
	p.sensor_type = /* cond.sensorType */'020'
	and 
	fum.company_id = /* cond.companyId */1
	and 
	fum.usage_period_from <= /* cond.systemDate */'2019-01-01 00:00:00'
	and 
	(
		/* cond.systemDate */'2019-01-01 00:00:00' <= fum.usage_period_to
		or 
		fum.usage_period_to is null
	)
	and
	fum.delete_flg = 0
;
