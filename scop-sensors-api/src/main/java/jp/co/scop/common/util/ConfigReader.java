package jp.co.scop.common.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigReader {
	
	@Autowired
	private Settings settings;
	
	/** サーバー名 */
	public String getIssuer() {
		return settings.getIssuer();
	}

	/** メール送信元 */
    public String getAddress(){
        return settings.getAddress();
    }
	

}
