package jp.co.scop.common.util;

/**
 * 午前午後
 */
public enum AmPmTy {
    /** 1 : AM */
	AM(1, "1"),
	/** 2 : PM */
	PM(2, "2");
	private final int value;
	private final String str;
    private AmPmTy(int value, String str) {
        this.value = value;
        this.str = str;
    }
    public int getValue() {
    	return value;
    }
    public String getStr() {
    	return str; 
    }

}
