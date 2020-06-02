package jp.co.scop.common.exception;

import org.springframework.validation.BindingResult;

/**
 * 入力チェックが発生したことを示す例外
 */
public class ValidationException extends RuntimeException {

	/**
	 * バインディングリザルト（バリデーション結果）
	 */
	private BindingResult bindingResult;
	
	/**
	 * コンストラクタ
	 */
	public ValidationException() {
		super();
	}
	/**
	 * コンストラクタ
	 * @param bindingResult バインディングリザルト（バリデーション結果）
	 */
	public ValidationException(BindingResult bindingResult) {
		super();
		this.bindingResult = bindingResult;
	}
	
	public BindingResult getBindingResult() {
		return this.bindingResult;
	}	
}
