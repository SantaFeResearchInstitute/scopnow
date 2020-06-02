SELECT 
	/*%expand*/*
FROM sensors
WHERE product_id = /* productId */1 
AND serial_no = /* serialNo */'serialNo'
AND delete_flg = 0
LIMIT 1
;