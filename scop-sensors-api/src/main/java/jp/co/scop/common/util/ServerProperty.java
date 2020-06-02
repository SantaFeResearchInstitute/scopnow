package jp.co.scop.common.util;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerProperty {
	
	@Value("${server.env.server-id}")
    private Integer serverId;
	
	@Value("${server.env.server-name}")
    private String serverName;
	
	@Value("${server.env.process-id}")
    private String processId;
	
	public Integer getServerId(){
		return serverId;
	}
	
	public String getServerName(){
		return serverName;
	}

	public String getProcessId(){
		return processId;
	}

	public String generateUUID() {
		return getProcessId() + "-" + UUID.randomUUID().toString();
	}


}
