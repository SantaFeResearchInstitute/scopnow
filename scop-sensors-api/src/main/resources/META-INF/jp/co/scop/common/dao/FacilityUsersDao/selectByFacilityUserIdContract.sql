SELECT DISTINCT
	 fu.company_id,
	 fu.facility_user_id,
	 fu.last_name,
	 contract.group_id,
	 contract.group_name,
	 contract.bed_master_id,
	 contract.bed_name
FROM facility_users fu
INNER JOIN
(
	SELECT
	 ci.company_id,
	 ci.facility_user_id,
	 fu.last_name,
	 g.group_id,
	 g.group_name,
	 bm.bed_master_id,
	 bm.bed_name
	FROM contract_information ci
	INNER JOIN contract_institution_bed cib 
 		ON cib.company_id = ci.company_id
       AND cib.contract_information_id = ci.contract_information_id
       AND ci.company_id = /*cond.companyId*/1
       AND ci.facility_user_id = /*cond.facilityUserId*/1
       AND cib.delete_flg = 0
       AND ci.delete_flg = 0
       AND (
         (cib.use_start_date < /* cond.localDate */'2020-02-14' AND cib.use_end_date > /* cond.localDate */'2020-02-14')
         OR
         (cib.use_start_date < /* cond.localDate */'2020-02-14' AND cib.use_end_date IS NULL)
         OR
		 (cib.use_start_date = /* cond.localDate */'2020-02-14' AND cib.use_start_category = /*cond.timeZoneFlg*/1)
		 OR
		 (cib.use_end_date = /* cond.localDate */'2020-02-14' AND cib.use_end_category = /*cond.timeZoneFlg*/1)
		 OR
		 (cib.use_start_date = /* cond.localDate */'2020-02-14' AND cib.use_start_category = /*cond.am*/1 AND /*cond.pm*/2 = /*cond.timeZoneFlg*/2)
		 OR
		 (cib.use_end_date = /* cond.localDate */'2020-02-14' AND cib.use_end_category = /*cond.pm*/2 AND /*cond.am*/1 = /*cond.timeZoneFlg*/1)
        )
	 INNER JOIN group_bed_connection gbc 
		ON 	gbc.company_id = cib.company_id
		AND gbc.bed_master_id = cib.bed_master_id
		AND	gbc.company_id = /*cond.companyId*/1
		AND	gbc.delete_flg = 0
     INNER JOIN bed_master bm 
 		ON 	bm.company_id = cib.company_id
		AND bm.bed_master_id = cib.bed_master_id
		AND bm.delete_flg = 0
	 INNER JOIN groups g 
	 	ON 	g.company_id = gbc.company_id
    	AND g.group_id = gbc.group_id
	    AND g.delete_flg = 0
	 INNER JOIN facility_users fu 
	 	ON 	fu.company_id = ci.company_id
		AND fu.facility_user_id = ci.facility_user_id
		AND fu.delete_flg = 0
	WHERE 
		fu.company_id = /*cond.companyId*/1
		AND fu.facility_user_id = /*cond.facilityUserId*/1
	UNION
	SELECT
	 cdc.company_id,
	 cdc.facility_user_id,
	 fu.last_name,
	 g.group_id,
	 g.group_name,
	 "",
	 "daycare"
	FROM contract_day_care cdc
	INNER JOIN groups g 
		ON	g.company_id = cdc.company_id
    	AND g.group_id = cdc.belong_group_id
	    AND g.delete_flg = 0
  	    AND g.company_id = /*cond.companyId*/1
	 INNER JOIN facility_users fu 
	 	ON 	fu.company_id = cdc.company_id
	    AND fu.facility_user_id = cdc.facility_user_id
	    AND fu.delete_flg = 0
	WHERE 
		fu.company_id = /*cond.companyId*/1
		AND fu.facility_user_id = /*cond.facilityUserId*/1
		AND cdc.delete_flg = 0
		AND cdc.period_from <= /* cond.localDate */'2020-02-14'
		AND (
			cdc.period_to >= /* cond.localDate */'2020-02-14'
			OR
			cdc.period_to IS NULL
		)
) contract ON fu.company_id = contract.company_id
          AND fu.facility_user_id = contract.facility_user_id
WHERE
	