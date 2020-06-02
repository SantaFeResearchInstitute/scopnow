package jp.co.scop.common.controller.advice;

/**
 * 例外レスポンス用エラーメッセージ情報
 */
class ExceptionEntry {
	
	/** エラーが発生したフィールド */
	private String field;
	/** エラーメッセージ */
	private String message;
	
	/**
	 * コンストラクタ
	 * @param field フィールド名
	 * @param message メッセージ
	 */
	ExceptionEntry(String field, String message) {
		this.field = field;
		this.message = message;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
