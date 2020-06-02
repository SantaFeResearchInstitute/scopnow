package jp.co.scop.common.util;

public enum SensorType {

    
    // フィールドを定義
    private String name;
    
    // コンストラクタを定義
    private SensorType(String name) {
        this.name = name;
    }
    
    // メソッド
    public String getValue() {
        return this.name;
    }
}
