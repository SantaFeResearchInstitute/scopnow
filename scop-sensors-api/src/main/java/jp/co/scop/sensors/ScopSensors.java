package jp.co.scop.sensors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("jp.co.scop.sensors")
@ComponentScan("jp.co.scop.common")
public class ScopSensors extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ScopSensors.class, args);
	} 
	
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	 	return application.sources(ScopSensors.class);
	 }

}
