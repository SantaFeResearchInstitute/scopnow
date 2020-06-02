package jp.co.scop.common.dto;

import java.time.LocalDate;

/**
 * 利用者の契約検索条件Dto
 * @author ncxxsl-koseki
 *
 */
public class FacilityUserContractConditionDto {

	/** 企業ID */
	private Integer companyId;
	/** 利用者ID */
	private Integer facilityUserId;
	/** 日付 */
	private LocalDate localDate;
    /** 午前を表す定数 */
    private Integer am;
    /** 午後を表す定数 */
    private Integer pm;
    /** 現在が午前か午後かを表すフラグ */
    private Integer timeZoneFlg;
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getFacilityUserId() {
		return facilityUserId;
	}
	public void setFacilityUserId(Integer facilityUserId) {
		this.facilityUserId = facilityUserId;
	}
	public LocalDate getLocalDate() {
		return localDate;
	}
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	public Integer getAm() {
		return am;
	}
	public void setAm(Integer am) {
		this.am = am;
	}
	public Integer getPm() {
		return pm;
	}
	public void setPm(Integer pm) {
		this.pm = pm;
	}
	public Integer getTimeZoneFlg() {
		return timeZoneFlg;
	}
	public void setTimeZoneFlg(Integer timeZoneFlg) {
		this.timeZoneFlg = timeZoneFlg;
	}
}
