package jp.co.scop.common.util;

import java.math.BigDecimal;

/**
 * センサー状態共通定義
 */
public class SensorsConditionConstants {
	
	/**
	 * 稼働状況
	 */
	public enum OperatingStatus{
    	/** 1 : 正常稼働 */
		NORMAL_OPERATION(1),
        /** 2 : 不良稼働 */
		FAILURE_OPERATION(2),
		/** 3 : 非稼働 */
		NON_WORKING(3);
		private final int value;
        private OperatingStatus(int value) {
            this.value = value;
        }
        public int getOperatingStatus() {
        	return value;
        }
    }
	

}
