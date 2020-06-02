package jp.co.scop.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * application.ymlに記述したSecurity情報を読み込む
 * @author kanto_mac003
 *
 */
@Component
public class SecurityProperty {

	@Value("${security.jwt.issuser}")
	private String issuer;
	
	@Value("${security.jwt.expire-milli-sec}")
	private Long expireMilliSec;

	public String getIssuer() {
		return issuer;
	}

	public Long getExpireMilliSec() {
		return expireMilliSec;
	}

}
