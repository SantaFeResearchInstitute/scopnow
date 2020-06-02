package jp.co.scop.common.util;

/**
 * スタッフ・利用者共通定義
 */
public class UserConstants {

	/**
	 * 性別
	 */
	public enum Gender{
    	/** 男 */
        MAN(1),
        /** 女 */
        WOMAN(2);
        private final int value;
        private Gender(int value) {
            this.value = value;
        }
        public int getGender() {
        	return value;
        }
    }
	
	public enum BedName{
		DAY_CARE_BED_NAME("daycare", "通所");
		private final String key;
		private final String val;
		private BedName(String key, String val) {
			this.key = key;
			this.val = val;
		}
		public String getKey() {
			return this.key;
		}
		public String getVal() {
			return this.val;
		}
		public String getDayCareBedName(String key) {
			return DAY_CARE_BED_NAME.getKey().equals(key) ? this.val : key;
		}
	}
    
    /**
     * 初回ログインフラグ定義
     */
    public enum InitializationLogin{
    	/** 未ログイン(FALSE) */
    	FIRST_LOGIN_DEFAULT("0"),
    	/** 初回ログイン済み(TRUE) */
    	FIRST_LOGIN_DONE("1");
    	private final String value;
        private InitializationLogin(String value) {
            this.value=value;
        }
        public String getInitializationLogin() {
        	return value;
        }
    }
    
}
