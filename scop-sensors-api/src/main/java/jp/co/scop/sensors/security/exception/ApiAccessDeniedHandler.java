package jp.co.scop.sensors.security.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * アクセスするリソースの認可に失敗した時の処理
 */
@Component
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(ApiAccessDeniedHandler.class);

	public ApiAccessDeniedHandler() {
	}

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException exception) throws IOException {
		if (response.isCommitted()) {
			// すでに別のフィルタなどでレスポンスが確定している場合は、そちらを優先する
			return;
		}
		logger.info("認証済みユーザが閲覧禁止リソースにアクセスしました", exception);
		response.setStatus(HttpStatus.FORBIDDEN.value());
	}
}