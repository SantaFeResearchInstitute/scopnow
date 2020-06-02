package jp.co.scop.sensors.domain;

/**
 * 企業検索用条件リクエスト
 */
public class CompanySearchConditionRequest {
	/** 企業ID */
	private Integer companyId;
	/** 企業名 */
    private String companyName;
	/** ジョブカテゴリ１（大分類） */
    private String jobCategoryLevel1;
	/** ジョブカテゴリ２（小分類） */
    private String jobCategoryLevel2;
    /** 創立日 */
    private String foundingDate;
    /** 従業員数 */
    private String employeeNumber;
    
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getJobCategoryLevel1() {
		return jobCategoryLevel1;
	}
	public void setJobCategoryLevel1(String jobCategoryLevel1) {
		this.jobCategoryLevel1 = jobCategoryLevel1;
	}
	public String getJobCategoryLevel2() {
		return jobCategoryLevel2;
	}
	public void setJobCategoryLevel2(String jobCategoryLevel2) {
		this.jobCategoryLevel2 = jobCategoryLevel2;
	}
	public String getFoundingDate() {
		return foundingDate;
	}
	public void setFoundingDate(String foundingDate) {
		this.foundingDate = foundingDate;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
}
