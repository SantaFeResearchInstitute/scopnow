SELECT 
	*
FROM company
WHERE 
    1=1
	/*%if condition.companyName != "" */
	  AND companyName like /* @infix(condition.companyName) */'test'
	/*%end*/
	/*%if condition.jobCategoryLevel1 != "" */
	  AND jobCategoryLevel1 = /* condition.jobCategoryLevel1 */'01'
	/*%end*/
	/*%if condition.jobCategoryLevel2 != "" */
	  AND jobCategoryLevel2 = /* condition.jobCategoryLevel2 */'02'
	/*%end*/
	/*%if condition.foundingDate != "" */
	  AND foundingDate = /* condition.foundingDate */'1970-01-01'
	/*%end*/
	/*%if condition.employeeNumber != "" */
	  AND employeeNumber = /* condition.employeeNumber */'1'
	/*%end*/
