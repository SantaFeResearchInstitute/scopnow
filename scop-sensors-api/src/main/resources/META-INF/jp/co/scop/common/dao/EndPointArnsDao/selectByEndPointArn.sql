select 
	/*%expand*/* 
from 
	end_point_arns epa 
where 
	epa.end_point_arn = /* endPointArn */'arn'
;