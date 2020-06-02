select
	contract_day_care_id,
	company_id,
	facility_user_id,
	contract_no,
	office_id,
	belong_group_id,
	in_use_flg,
	period_from,
	period_to
from
	contract_day_care
where
	in_use_flg = 1
	and
	delete_flg = 0
	and
	facility_user_id = /* cond.facilityUserId */1
	and
	company_id = /* cond.companyId */1
	and
	period_from <= /* cond.systemDate */'2019-01-01'
	and
	(
		period_to >= /* cond.systemDate */'2019-01-01'
		OR 
		period_to is null
	)
