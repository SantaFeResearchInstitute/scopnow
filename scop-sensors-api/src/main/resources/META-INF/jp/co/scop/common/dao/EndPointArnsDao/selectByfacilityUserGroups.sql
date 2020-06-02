select 
	end_point_arns.end_point_arn, 
	device_tokens.device_token, 
	scop_now_app_logins.staff_id
from 
	scop_now_app_logins 
inner join 
	end_point_arns 
	on scop_now_app_logins.end_point_arn_uuid = end_point_arns.end_point_arn_uuid
	and end_point_arns.delete_flg = 0
inner join 
	device_tokens 
	on scop_now_app_logins.device_token_uuid = device_tokens.device_token_uuid
	and device_tokens.delete_flg = 0
inner join 
	belongs 
	on scop_now_app_logins.company_id = belongs.company_id
	and scop_now_app_logins.staff_id = belongs.staff_id
	and belongs.delete_flg = 0
where 
	scop_now_app_logins.end_point_arn_uuid is not null
	and scop_now_app_logins.delete_flg = 0
	and belongs.display_date is not null 
	and belongs.company_id = /* companyId */1
	and belongs.group_id = /* groupId */1
;