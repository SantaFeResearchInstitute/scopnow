package jp.co.scop.common.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import jp.co.scop.common.exception.BusinessException;

/**
 * プログラム共通システム日時定義
 */
public class TimeUtil {
	
	/** Timestamp型のNULL値 */
	public static final Timestamp NULL_TIMESTAMP = null;
	
	public static final Date SYSTEM_DATE = new Date(System.currentTimeMillis());
	/** SimpleDateFormatのFormatを定義する。 */
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

	/** 
	 * 有効日付判定 
	 */
	public static boolean verifyBetween(Date from, Date to) {
		//from,toをnullチェックし、システム日付と比較する
		if( Objects.isNull(to) ) {
			if( new Date(System.currentTimeMillis()).after(from)) {
				return true;
			}
		} else {
			if( new Date(System.currentTimeMillis()).after(from) 
					&& new Date(System.currentTimeMillis()).before(to) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 営業日を取得する
	 */
	public static Date getAplDate(Timestamp dateTime) {
		// Timestamp型をDate型に変換する
		Date date = new Date(dateTime.getTime());
		return date;
	}
	
//	public static boolean verify(Date from,Date to) {
//		return false;
//	}
	
	public static Timestamp sysdate() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	
	public static java.util.Date of(String strDate) {
		java.util.Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.yyyy_MM_dd_HH_mm_ss);
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
	    	throw new BusinessException(null, e);
		}
		return date;
	}
	
	public static Date gnerateIssuedAt(Timestamp timestamp) {
		
        // 加算されるDate型の日時の作成
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// Date型の日時をCalendar型に変換
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timestamp);
		
		// 日時を加算する
		calendar.add(Calendar.HOUR, 9);
		
		// Calendar型の日時をDate型に戻す
		Date date = (Date) calendar.getTime();
		System.out.println(sdf.format(date));
		
		return date;
	}

	/**
	 * 午前・午後取得
	 * @return
	 */
	public static String cuurentAMorPM() {
		Calendar c = Calendar.getInstance();
		int key = c.get(Calendar.AM_PM);
		switch (key) {
		case Calendar.AM:
			return "1";
		case Calendar.PM:
			return "2";
		default:
			return "0";
		} 
	}

	/**
	 * 現時刻が午前か午後か判別する。
	 *
	 * @return 午前午後区分
	 */
	public static Integer getAmPmTy() {
        Calendar cTime = Calendar.getInstance();
        Integer timeZoneFlg = null;
 
        if(cTime.get(Calendar.AM_PM) == Calendar.AM) {
			timeZoneFlg = AmPmTy.AM.getValue();
		} else {
			timeZoneFlg = AmPmTy.PM.getValue();
		}
        
        return timeZoneFlg;
	}
    
}