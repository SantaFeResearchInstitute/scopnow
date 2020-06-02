select count(alarm_uuid)
from alarms
where sensor_type = /* cond.sensorType */'000'
and serial_no = /* cond.serialNumber */'0001'
and alert_code = /* cond.alarmCode */0
and create_date >= /* cond.localDateTime */'2019-05-18 07:48:27';