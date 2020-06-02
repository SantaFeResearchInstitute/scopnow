package jp.co.scop.sensors.domain;

import java.util.List;

import jp.co.scop.sensors.dto.CompanyDto;

/**
 * 企業検索用条件リクエスト
 */
public class CompaniesResponse {
	/** 条件を満たす企業リスト */
	private List<CompanyDto> companies;

	public List<CompanyDto> getCompanies() {
		return companies;
	}
	public void setCompanies(List<CompanyDto> companies) {
		this.companies = companies;
	}
}
