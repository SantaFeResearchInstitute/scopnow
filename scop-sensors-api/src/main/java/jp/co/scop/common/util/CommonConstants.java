package jp.co.scop.common.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.BooleanUtils;

/**
 * システム共通定数定義
 */
public class CommonConstants {

	/**
     * 削除フラグ
     */
    public enum DeleteFlg{
    	/** 0 : 未削除(FALSE) */
        DEFAULT(false),
        /** 1 :削除済み(TRUE) */
        DELETED(true);
        private final boolean value;
        private DeleteFlg(boolean value) {
            this.value = value;
        }
        public boolean getValue() {
        	return value;
        }
      // booleanをIntegerに変換
      public Integer getIntValue() {
      	return (DELETED.value)?BooleanUtils.toInteger(DELETED.value):BooleanUtils.toInteger(DEFAULT.value);
      }
    }
    
    /**
     * 使用フラグ
     */
    public enum IsUseFlg{
    	/** 0 : 未使用(FALSE) */
    	DEFAULT(false),
    	/** 1 : 使用中(TRUE) */
        USING(true);
        private final boolean value;
        private IsUseFlg(boolean value) {
            this.value = value;
        }
        public boolean getValue() {
        	return value;
        }
      // booleanをIntegerに変換
      public Integer getIntValue(){
      	return (USING.value)?BooleanUtils.toInteger(USING.value):BooleanUtils.toInteger(DEFAULT.value);
      }
    }
    
    /**
     * 値なし(センサー未使用)
     * ※レスポンスにnullを使用しないために定義する
     */
	public static final Integer INTEGER_EMPTY = (-1);
	public static final String STRING_EMPTY = "0";
	public static final BigDecimal DECIMAL_EMPTY = BigDecimal.valueOf(-1);
	

    /**
     * 値なし(センサー未使用)
     * ※レスポンスにnullを使用しないために定義する
     */
	public enum NullStatus{
		/**
		 * 半角スペース : String型のNULL
		 * -1 : Integer型のNULL
		 */
		EMPTY(" ", -1);
		private final String strValue;
		private final int intValue;
		private NullStatus(final String strValue, final int intValue) {
			this.strValue = strValue;
			this.intValue = intValue;
		}
		/**
		 * 半角スペース : String型のNULL
		 * @return
		 */
		public String getString() {
			return strValue;
		}
		/**
		 * -1 : Integer型のNULL
		 * @return
		 */
		public int getIntValue() {
			return intValue;
		}
	}

	
}
