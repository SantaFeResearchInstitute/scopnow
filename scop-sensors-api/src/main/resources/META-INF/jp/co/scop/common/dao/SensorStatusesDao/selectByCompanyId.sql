select 
	/*%expand*/*
from 
	sensor_statuses 
where 
	sensor_type = /* sensorType */'000' 
	and 
	serial_no = /* serialNo */'ABC012'
	and
	delete_flg = 0