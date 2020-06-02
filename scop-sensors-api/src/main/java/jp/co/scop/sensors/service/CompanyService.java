package jp.co.scop.sensors.service;

import java.util.List;

import jp.co.scop.sensors.dto.CompanyDto;
import jp.co.scop.sensors.dto.CompanySearchConditionDto;

/**
 * 企業用サービスクラス
 */
public interface CompanyService {

	/**
	 * 企業を全件検索する
	 * @return 条件を満たした企業情報
	 */
	public List<CompanyDto> searchAll();

	/**
	 * 企業をIDを元に検索する
	 * @return 企業情報
	 */
	public CompanyDto searchById(Integer id);
	
	/**
	 * 条件を満たす企業を検索する
	 * @param conditionDto 検索条件
	 * @return 条件を満たした企業情報
	 */
	public List<CompanyDto> searchFullFillCompanyList(CompanySearchConditionDto dto);
	
	/**
	 * 企業情報を登録する
	 * @param dto 登録対象のDTO
	 * @return 登録件数
	 * @throws Exception 
	 */
	public int insert(CompanyDto dto);

	/**
	 * 企業情報を更新する
	 * @param dto 更新対象のDTO
	 * @return 更新件数
	 */
	public int update(CompanyDto dto);
	
	/**
	 * 企業情報を削除する
	 * @param dto 削除対象のDTO
	 * @return 削除件数
	 */
	public int delete(CompanyDto dto);

}
