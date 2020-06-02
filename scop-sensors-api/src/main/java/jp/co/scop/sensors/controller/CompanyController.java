package jp.co.scop.sensors.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.common.exception.ValidationException;
import jp.co.scop.sensors.domain.CompaniesResponse;
import jp.co.scop.sensors.domain.CompanyRequest;
import jp.co.scop.sensors.domain.CompanyResponse;
import jp.co.scop.sensors.domain.CompanySearchConditionRequest;
import jp.co.scop.sensors.domain.CompanySearchConditionResponse;
import jp.co.scop.sensors.domain.validator.CompanySearchValidator;
import jp.co.scop.sensors.domain.validator.CompanyValidator;
import jp.co.scop.sensors.dto.CompanyDto;
import jp.co.scop.sensors.dto.CompanySearchConditionDto;
import jp.co.scop.sensors.dto.SensorGroupsDto;
import jp.co.scop.sensors.service.CompanyService;
import jp.co.scop.sensors.service.TokenService;



/**
 * 企業に対して操作を行うコントローラクラス
 */
@RestController
public class CompanyController {

	/** 企業操作用サービス */
	@Autowired 
	CompanyService jobService;
	
	/** 企業情報 登録/更新/削除用バリデータ */
    @Autowired
    CompanyValidator companyValidator;
    
	/** 企業情報 検索用バリデータ */
    @Autowired
    CompanySearchValidator companySearchValidator;
    
	/**
	 * 全件検索用API
	 * return 全件検索結果
	 */
	@CrossOrigin
	@GetMapping("/companies")
	public CompaniesResponse companies(@RequestHeader("Authorization") String token) {
		
		/**
		 * 全件取得
		 */
		List<CompanyDto> companies = jobService.searchAll();
		
    	/**
    	 * レスポンス作成
    	 */
		CompaniesResponse response = new CompaniesResponse();
		response.setCompanies(companies);
		return response;
	}
	
	/**
	 * 1件検索用API
	 * @param id 企業ID
	 * return １件検索結果
	 */
	@CrossOrigin
	@GetMapping("/company/{id}")
	public CompanyResponse company(@PathVariable("id") Integer id) {
		
		/**
		 * 1件取得
		 */
		CompanyDto dto = jobService.searchById(id);
		
    	/**
    	 * レスポンス作成
    	 */
		CompanyResponse response = new CompanyResponse();
		response.setCompanyId(dto.getCompanyId());
		response.setCompanyName(dto.getCompanyName());
		response.setJobCategoryLevel1(dto.getJobCategoryLevel1());
		response.setJobCategoryLevel2(dto.getJobCategoryLevel2());
		response.setFoundingDate(dto.getFoundingDate());
		response.setEmployeeNumber(dto.getEmployeeNumber());
		response.setUpdateDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(dto.getUpdateDate()));
		return response;
	}
	
	/**
	 * 自由検索用API
	 * @param request 検索条件
	 * @param bindingResult バインディングリザルト(バリデーション結果を詰めるオブジェクト)
	 * return 検索結果
	 */
	@CrossOrigin
	@GetMapping("/companies/search")
	public CompanySearchConditionResponse search(
			CompanySearchConditionRequest request, BindingResult bindingResult) {
		
    	/**
    	 * バリデーションチェックを行う。
    	 */
		ValidationUtils.invokeValidator(companySearchValidator, request, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        
		/**
		 * 条件を満たす企業を探す
		 */
		CompanySearchConditionDto conditionDto = new CompanySearchConditionDto();
		conditionDto.setCompanyName(request.getCompanyName());
		conditionDto.setJobCategoryLevel1(request.getJobCategoryLevel1());
		conditionDto.setJobCategoryLevel2(request.getJobCategoryLevel2());
		conditionDto.setFoundingDate(request.getFoundingDate());
		conditionDto.setEmployeeNumber(request.getEmployeeNumber());
		List<CompanyDto> companies = jobService.searchFullFillCompanyList(conditionDto);
		
    	/**
    	 * レスポンス作成
    	 */
		CompanySearchConditionResponse response = new CompanySearchConditionResponse();
		response.setCompanies(companies);
		return response;
	}
	
	/**
	 * 登録用API
	 * @param request 登録対象
	 * @param bindingResult バインディングリザルト(バリデーション結果を詰めるオブジェクト)
	 * @return 登録結果
	 */
    @CrossOrigin
    @PutMapping("/company")
    public CompanyResponse insert(
    		@RequestBody CompanyRequest request, BindingResult bindingResult) {

    	/**
    	 * バリデーションチェックを行う。
    	 */
		ValidationUtils.invokeValidator(companyValidator, request, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        
    	/** 
    	 * 企業情報を1件登録する
    	 */
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName(request.getCompanyName());
		companyDto.setJobCategoryLevel1(request.getJobCategoryLevel1());
		companyDto.setJobCategoryLevel2(request.getJobCategoryLevel2());
		companyDto.setFoundingDate(request.getFoundingDate());
		companyDto.setEmployeeNumber(request.getEmployeeNumber());
    	int resutlCount = jobService.insert(companyDto);

    	/**
    	 * レスポンス作成
    	 */
    	CompanyResponse response = new CompanyResponse();
		response.setCompanyId(request.getCompanyId());
    	return response;
   }

    /**
     * 更新用API
     * @param id 更新対象ID
     * @param request 更新対象
	 * @param bindingResult バインディングリザルト(バリデーション結果を詰めるオブジェクト)
     * @return 更新結果
     * @throws ParseException 
     */
    @CrossOrigin
    @PostMapping("/company/{id}")
    public CompanyResponse update(
    		@PathVariable("id") Integer id, @RequestBody CompanyRequest request, BindingResult bindingResult) throws ParseException {
        
    	/**
    	 * バリデーションチェックを行う。
    	 */
		ValidationUtils.invokeValidator(companyValidator, request, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        
    	/** 
    	 * 企業情報を1件更新する
    	 */
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyId(id);
		companyDto.setCompanyName(request.getCompanyName());
		companyDto.setJobCategoryLevel1(request.getJobCategoryLevel1());
		companyDto.setJobCategoryLevel2(request.getJobCategoryLevel2());
		companyDto.setFoundingDate(request.getFoundingDate());
		companyDto.setEmployeeNumber(request.getEmployeeNumber());
		companyDto.setUpdateDate(new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(request.getUpdateDate()).getTime()));
		int resutlCount = jobService.update(companyDto);

		/**
    	 * レスポンス作成
    	 */
    	CompanyResponse response = new CompanyResponse();
		response.setCompanyId(id);
    	return response;
    }

    /**
     * 削除用API
     * @param id 削除対象ID
     * @return 削除結果
     */
    @CrossOrigin
    @DeleteMapping("/company/{id}")
    public CompanyResponse delete(@PathVariable("id") Integer id) {
        
    	/**
    	 * このExampleプログラムでは、IDを元に削除を行っており、
    	 * 他の入力項目のバリデーションチェックは行っていない。
    	 * 実際の開発では、適宜バリデーションチェックを行う必要がある。
    	 */
    	
    	/** 
    	 * 企業情報を1件削除する
    	 */
		CompanyDto conditionDto = new CompanyDto();
		conditionDto.setCompanyId(id);
    	int resutlCount = jobService.delete(conditionDto);
    	
    	/**
    	 * レスポンス作成
    	 */
    	CompanyResponse response = new CompanyResponse();
		response.setCompanyId(id);		
    	return response;
   }
}
