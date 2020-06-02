select
  /*%expand*/*
from
  scop_now_app_logins
where
  device_token_uuid = /* deviceTokenUuid */'a'
  and
  end_point_arn_uuid = /* endPointArnUuid */'a'
