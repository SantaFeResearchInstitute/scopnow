select 
	cib.contract_institution_bed_id,
	cib.company_id,
	cib.contract_information_id,
	cib.bed_master_id,
	bm.bed_name,
	cib.use_start_date,
	cib.use_start_category,
	cib.use_end_date,
	cib.use_end_category,
	gbc.group_id
from 
	contract_institution_bed cib
inner join 
	bed_master bm
	on 
	cib.company_id = bm.company_id
	and
	cib.bed_master_id = bm.bed_master_id
	and
	bm.delete_flg = 0
inner join
	group_bed_connection gbc
	on
	bm.company_id = gbc.company_id 
	and
	bm.bed_master_id = gbc.bed_master_id
	and
	gbc.delete_flg = 0
where 
	cib.company_id = /* cond.companyId */1
	and
	cib.contract_information_id = /* cond.contractInformationId */3
	and
	cib.delete_flg = 0
	and
	cib.use_start_date <= /* cond.systemDate */'2019-01-01'
	and
	(
		cib.use_end_date >= /* cond.systemDate */'2019-01-01'
		or
		cib.use_end_date is null
	)
	order by cib.use_start_date asc, cib.use_start_category asc
;