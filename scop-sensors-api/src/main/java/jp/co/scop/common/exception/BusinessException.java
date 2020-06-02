package jp.co.scop.common.exception;

/**
 * 業務処理で発生した例外
 */
public class BusinessException extends RuntimeException {

	/**
	 * コンストラクタ
	 */
	public BusinessException() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param messageKey メッセージキー
	 */
	public BusinessException(String messageKey) {
		super(messageKey);
	}
	/**
	 * コンストラクタ
	 * @param messageKey メッセージキー
	 * @param e 例外
	 */
	public BusinessException(String messageKey, Exception e) {
		super(messageKey, e);
	}
}
