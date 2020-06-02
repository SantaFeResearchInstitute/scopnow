package jp.co.scop.common.dto;

public class CustomContractInstitutionBedDto {
	
	/** ベッド名 */
	private String bedName;
	/** グループID */
	private Integer groupId;
	
	public String getBedName() {
		return bedName;
	}
	public void setBedName(String bedName) {
		this.bedName = bedName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	

}
