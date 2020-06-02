package jp.co.scop.sensors.security.filter;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.sensors.dto.SensorGroupsDto;
import jp.co.scop.sensors.service.TokenService;


/**
 * 認証時に付与したJWTを解決するためのフィルター
 */
public class JWTResolveFilter extends GenericFilterBean {

	/** JWT用サービス */
	final TokenService tokenService; 
	
	/** メッセージリソース */
	final MessageSource messageSource;

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(JWTResolveFilter.class);
	
	/**
	 * コンストラクタ
	 * @param jwtService JWT用サービス
	 * @param messageSource メッセージリソース
	 */
	public JWTResolveFilter(TokenService tokenService, MessageSource messageSource){
		this.tokenService = tokenService;
		this.messageSource = messageSource;
	}

	/**
	 * 認可フィルター
	 * @param request リクエスト
	 * @param response レスポンス
	 * @param filterChain フィルターチェイン
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		/**
		 * リクエストに付与されたJWTを取り出す
		 */
		String encodedJWT = extractJWT(request);

		if (encodedJWT == null) {
			// JWTが存在していない場合は、このフィルタの対象外とみなし後続のフィルタを呼び出す
			filterChain.doFilter(request, response);
			return;
		}
		
		/**
		 * 以下、JWTが付与されている場合
		 */
		
		/**
		 *  JWT検証
		 */
		DecodedJWT decodedJwt = null;
		try {
			
			decodedJwt = tokenService.verify(encodedJWT);
			
		} catch (AccessControlException e) {
			logger.error(messageSource.getMessage(e.getMessage(), null, Locale.JAPAN), e);
			if ( !response.isCommitted() ) {
				((HttpServletResponse)response).setStatus(HttpStatus.FORBIDDEN.value());
				return;
			} 
		} catch (BusinessException e) {
			logger.error(messageSource.getMessage(e.getMessage(), null, Locale.JAPAN), e);
			if ( !response.isCommitted() ) {
				((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			} 
		} catch (IllegalArgumentException | JWTVerificationException e) {
			logger.error(e.getMessage());
			if ( !response.isCommitted() ) {
				((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			} 
		}

		/**
		 * 後続の認可処理で利用するためSecurityContextに認証情報を設定する
		 * JWT検証が完了した認証情報は全てADMINとして扱っている
		 */
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(decodedJwt.getSubject(), null, AuthorityUtils.createAuthorityList("ROLE_ADMIN"))
		);

		// 後続のフィルタを呼び出す
		filterChain.doFilter(request, response);
	}

	/**
	 * JWTを取り出す
	 * @param request リクエスト
	 * @return エンコードされているJWT
	 */
	String extractJWT(ServletRequest request) {
		/**
		 * 認証時に設定したリクエストヘッダーのルールが設定されているか判定する
		 * (リクエストヘッダーにAuthorizationがついていて、そのValueがBearerで始まるか確認する)
		 * 
		 * ”Authorization”:"Bearer・・・"というルールはRFC 6750で定義されている
		 * 参考
		 * 　https://qiita.com/uasi/items/cfb60588daa18c2ec6f5
		 */
		String token = ((HttpServletRequest) request).getHeader("authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			return null;
		}

		/**
		 * "Bearer"を外してJWTを返す
		 */
		return token.substring(7);
	}
}
