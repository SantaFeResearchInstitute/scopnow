package jp.co.scop.common.dto;

import java.time.LocalDate;

/**
 * 契約入所利用ベッド検索条件
 * @author ncxxsl-koseki
 *
 */
public class ContractInstitutionBedCondition {
	
	/** 企業ID */
	private Integer companyId;
	/** 契約情報（入所・SS）ID */
	private Integer contractInformationId;
	/** システム日時 */
	private LocalDate systemDate;
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getContractInformationId() {
		return contractInformationId;
	}
	public void setContractInformationId(Integer contractInformationId) {
		this.contractInformationId = contractInformationId;
	}
	public LocalDate getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(LocalDate systemDate) {
		this.systemDate = systemDate;
	}

}
