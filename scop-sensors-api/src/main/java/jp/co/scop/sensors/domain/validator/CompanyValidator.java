package jp.co.scop.sensors.domain.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.common.base.Strings;

import jp.co.scop.sensors.domain.CompanyRequest;

/**
 * 企業情報関連のリクエストに対するバリデータ
 */
@Component
public class CompanyValidator implements Validator {

	/**
	 * バリデート対象クラスを指定
	 */
    @Override
    public boolean supports(Class<?> clazz) {
        return CompanyRequest.class.isAssignableFrom(clazz);
    }

    /**
     * バリデーション
     */
    @Override
    public void validate(Object request, Errors errors) {
    	CompanyRequest company = (CompanyRequest)request;
    	
    	/**
    	 * errors.rejectValueの第４引数はデフォルトメッセージを指定する。
    	 * 今回のExampleでは利用していないため空文字を指定している。
    	 * 
    	 * 今回は、第２引数にメッセージコードを元にして、例外ハンドラ(RestControllerExceptionHandler)でメッセージ化している。
    	 */
    	
    	// 企業コードバリデーション
    	if ( Strings.isNullOrEmpty(company.getCompanyName())) {
    		// 必須チェック
    		errors.rejectValue("companyName", "validation.companyName.require");
    	} else {
        	if ( company.getCompanyName().length() > 30) {
        		// 文字数制限チェック ３０文字を超えるとエラー
        		errors.rejectValue("companyName", "validation.companyName.size", new Object[]{30}, "");
        	}
    	}
    	
    	// ジョブカテゴリ１（大分類）バリデーション
    	if ( Strings.isNullOrEmpty(company.getJobCategoryLevel1())) {
    		// 必須チェック
    		errors.rejectValue("jobCategoryLevel1", "validation.jobCategoryLevel1.require");
    	} else {
    		if (!company.getJobCategoryLevel1().matches("[0-9]{2}")) {
        		// フォーマットチェック 2桁数値でないとエラー
        		errors.rejectValue("jobCategoryLevel1", "validation.jobCategoryLevel1.format", new Object[]{2}, "");
    		}
    	}
    	// ジョブカテゴリ２（大分類）バリデーション
    	if ( Strings.isNullOrEmpty(company.getJobCategoryLevel2())) {
    		// 必須チェック
    		errors.rejectValue("jobCategoryLevel2", "validation.jobCategoryLevel2.require");
    	} else {
    		if (!company.getJobCategoryLevel2().matches("[0-9]{2}")) {
        		// フォーマットチェック 2桁数値でないとエラー
        		errors.rejectValue("jobCategoryLevel2", "validation.jobCategoryLevel2.format", new Object[]{2}, "");
    		}
    	}

    	// 創業日バリデーション
    	if ( !Strings.isNullOrEmpty(company.getFoundingDate())) {
    		if (!company.getFoundingDate().matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
        		// フォーマットチェック yyyy/mm/dd形式でないとエラー
        		errors.rejectValue("foundingDate", "validation.foundingDate.format", null, "");
    		}
    	}
    	
    	// 従業員数バリデーション
    	if ( !Strings.isNullOrEmpty(company.getEmployeeNumber())) {
    		
    		if (!company.getEmployeeNumber().matches("[0-9].*")) {
        		// 数値チェック 数値形式でないとエラー
        		errors.rejectValue("employeeNumber", "validation.employeeNumber.number", null, "");
    		} else {
        		// 範囲チェック 1~10000の範囲内でないとエラー
    			Integer employeeNumber  = Integer.valueOf(company.getEmployeeNumber());
        		if (employeeNumber < 1 || 10000 < employeeNumber) {
            		// フォーマットチェック yyyy/mm/dd形式でないとエラー
            		errors.rejectValue("employeeNumber", "validation.employeeNumber.range", new Object[]{1,10000}, "");
        		}
    		}
    	}
    }

}
