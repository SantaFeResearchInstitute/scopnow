package jp.co.scop.common.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

public class FacilityUserSearchConditionDto {
	
	/** 企業ID */
	private Integer companyId;
	/** グループID */
	private Integer groupId;
	/** 利用者ID */
	private Integer facilityUserId;
	/** 職員ID */
	private Integer staffId;
	/** システム日時 */
	private Timestamp systemDate;
	/** ローカル日時 */
	private LocalDate localDate;
	/** 取得開始位置 */
	private Integer startIndex;
	/** ソートキー */
	private Integer sortKey;
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getFacilityUserId() {
		return facilityUserId;
	}
	public void setFacilityUserId(Integer facilityUserId) {
		this.facilityUserId = facilityUserId;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public Timestamp getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(Timestamp systemDate) {
		this.systemDate = systemDate;
	}
	public LocalDate getLocalDate() {
		return localDate;
	}
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getSortKey() {
		return sortKey;
	}
	public void setSortKey(Integer sortKey) {
		this.sortKey = sortKey;
	}

}
