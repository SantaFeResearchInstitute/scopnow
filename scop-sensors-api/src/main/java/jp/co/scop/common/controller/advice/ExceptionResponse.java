package jp.co.scop.common.controller.advice;

import java.util.List;

/**
 * 例外用レスポンス
 */
public class ExceptionResponse {

	/**
	 * グローバルエラー
	 */
	String globalError;
	
	/**
	 * フィールドエラーメッセージリスト
	 */
	List<ExceptionEntry> fieldErrors;

	public String getGlobalError() {
		return globalError;
	}

	public void setGlobalError(String globalError) {
		this.globalError = globalError;
	}

	public List<ExceptionEntry> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<ExceptionEntry> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
