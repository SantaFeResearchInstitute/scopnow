package jp.co.scop.sensors.domain;

/**
 * 企業情報レスポンスクラス
 */
public class CompanyResponse {
	/** 企業ID */
	public Integer companyId;
	/** 企業名 */
	public String companyName;
	/** ジョブカテゴリ１（大分類） */
	public String jobCategoryLevel1;
	/** ジョブカテゴリ２（小分類） */
	public String jobCategoryLevel2;
    /** 創立日 */
    private String foundingDate;
	/** 従業員数 */
    private String employeeNumber;
	/** 更新日 */
	private String updateDate;
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
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
