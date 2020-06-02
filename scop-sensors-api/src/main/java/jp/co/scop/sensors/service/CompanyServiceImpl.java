package jp.co.scop.sensors.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.scop.common.dao.CompanyDao;
import jp.co.scop.common.entity.Company;
import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.sensors.dto.CompanyDto;
import jp.co.scop.sensors.dto.CompanySearchConditionDto;

/**
 * 企業用サービスクラス
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	/** 会社情報DAO */
	@Autowired
	CompanyDao companyDao;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CompanyDto> searchAll() {
		// EntityからDtoへ変換する
		return companyDao.selectAll().stream().map(new Function<Company,CompanyDto>() {
			@Override
			public CompanyDto apply(final Company company) {
				CompanyDto dto = new CompanyDto();
				dto.setCompanyId(company.getCompanyId());
				dto.setCompanyName(company.getCompanyName());
				dto.setJobCategoryLevel1(company.getJobCategoryLevel1());
				dto.setJobCategoryLevel2(company.getJobCategoryLevel2());
				dto.setFoundingDate(company.getFoundingDate());
				dto.setEmployeeNumber(company.getEmployeeNumber());
				return dto;
			}
		}).collect(Collectors.toList());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompanyDto searchById(Integer id) {

		Company company = companyDao.selectById(id);
		CompanyDto dto = new CompanyDto();
		dto.setCompanyId(company.getCompanyId());
		dto.setCompanyName(company.getCompanyName());
		dto.setJobCategoryLevel1(company.getJobCategoryLevel1());
		dto.setJobCategoryLevel2(company.getJobCategoryLevel2());
		dto.setFoundingDate(company.getFoundingDate());
		dto.setEmployeeNumber(company.getEmployeeNumber());
		dto.setUpdateDate(company.getUpdateDate());
		return dto;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CompanyDto> searchFullFillCompanyList(CompanySearchConditionDto conditionDto) {

		/**
		 * 検索条件を元にDBに問い合わせて企業情報を取得する
		 */
		Company condition = new Company();
		condition.setCompanyName(conditionDto.getCompanyName());
		condition.setJobCategoryLevel1(conditionDto.getJobCategoryLevel1());
		condition.setJobCategoryLevel2(conditionDto.getJobCategoryLevel2());
		condition.setFoundingDate(conditionDto.getFoundingDate());
		condition.setEmployeeNumber(conditionDto.getEmployeeNumber());

		// EntityからDtoへ変換する
		return companyDao.selectFullFillCompanyList(condition).stream().map(new Function<Company,CompanyDto>() {
			@Override
			public CompanyDto apply(final Company company) {
				CompanyDto dto = new CompanyDto();
				dto.setCompanyId(company.getCompanyId());
				dto.setCompanyName(company.getCompanyName());
				dto.setJobCategoryLevel1(company.getJobCategoryLevel1());
				dto.setJobCategoryLevel2(company.getJobCategoryLevel2());
				dto.setFoundingDate(company.getFoundingDate());
				dto.setEmployeeNumber(company.getEmployeeNumber());
				return dto;
			}
		}).collect(Collectors.toList());
	}
	
	/**
	 * 企業情報を登録する
	 * @param dto 登録対象のDTO
	 * @return 登録件数
	 */
	@Transactional
	public int insert(CompanyDto dto) {
		/**
		 * @Transactionalアノテーションをつけることで、このメソッド内が1トランザクションを示す
		 * 
		 * このアノテーションを付けることで、以下の動作となる。
		 *  ・このメソッドが正常終了した時、DBに自動コミットされる
		 *  ・このメソッドが例外発生（RuntimeException）した時、DBに自動ロールバックされる
         * 
		 * ＊＊＊＊＊＊以下、注意点＊＊＊＊＊＊
		 * 　@Transactionalのロールバックのデフォルト設定は、
		 * 　非検査例外（RuntimeException）のサブクラスのみ有効となり、
		 * 　検査例外（RuntimeException以外）はコミットされてしまう。
		 *   @Transactional(rollbackFor=Exception.class)と設定するとすべてロールバックされる
		 *   
		 *   このExampleでは、Service層から発生する例外は、RuntimeException(を継承したクラス)で抜けるようにしている。
		 */
		Company company = new Company();
		company.setCompanyName(dto.getCompanyName());
		company.setJobCategoryLevel1(dto.getJobCategoryLevel1());
		company.setJobCategoryLevel2(dto.getJobCategoryLevel2());
		company.setFoundingDate(dto.getFoundingDate());
		company.setEmployeeNumber(dto.getEmployeeNumber());
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		company.setRegistDate(timestamp);
		company.setRegistUser("user001");
		company.setUpdateDate(timestamp);
		company.setUpdateUser("user001");
		
		return companyDao.insert(company);
	}

	/**
	 * 企業情報を更新する
	 * @param dto 更新対象のDTO
	 * @return 更新件数
	 */
	@Transactional
	public int update(CompanyDto dto) {
		
		/**
		 * 排他チェックを行う
		 */
		Company latestCompany =  companyDao.selectById(dto.getCompanyId());
		if (!latestCompany.getUpdateDate().equals(dto.getUpdateDate())) {
			throw new BusinessException("global.optimismExclusiveError");
		}
		
		/**
		 * 更新
		 */
		Company company = new Company();
		company.setCompanyId(dto.getCompanyId());
		company.setCompanyName(dto.getCompanyName());
		company.setJobCategoryLevel1(dto.getJobCategoryLevel1());
		company.setJobCategoryLevel2(dto.getJobCategoryLevel2());
		company.setFoundingDate(dto.getFoundingDate());
		company.setEmployeeNumber(dto.getEmployeeNumber());
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		company.setUpdateDate(timestamp);
		company.setUpdateUser("user001");

		return companyDao.update(company);
	}
	
	/**
	 * 企業情報を削除する
	 * @param dto 削除対象のDTO
	 * @return 削除件数
	 */
	@Transactional
	public int delete(CompanyDto dto) {
		Company company = new Company();
		company.setCompanyId(dto.getCompanyId());
		return companyDao.delete(company);
	}
}
