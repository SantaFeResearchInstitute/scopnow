package jp.co.scop.sensors.security.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 認証が必要なリソースに未認証でアクセスしたときの処理
 */
@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(ApiAuthenticationEntryPoint.class);

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (response.isCommitted()) {
			// すでに別のフィルタなどでレスポンスが確定している場合は、そちらを優先する
			return;
		}
		logger.info("未認証のユーザが認証前提のリソースにアクセスしました", exception);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
}