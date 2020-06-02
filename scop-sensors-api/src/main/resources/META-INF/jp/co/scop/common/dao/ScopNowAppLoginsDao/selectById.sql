select
  /*%expand*/*
from
  scop_now_app_logins
where
  company_id = /* companyId */1
  and
  staff_id = /* staffId */1
  and
  device_token_uuid = /* deviceTokenUuid */'a'
  and
  scop_now_app_login_uuid = /* scopNowAppLoginUuid */'a'
