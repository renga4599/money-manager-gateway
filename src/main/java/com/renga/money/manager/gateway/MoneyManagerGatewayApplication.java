package com.renga.money.manager.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackageClasses = {com.renga.money.manager.MoneyManagerBasePackage.class})
public class MoneyManagerGatewayApplication {

	private final static String OS_NAME = System.getProperty("os.name");
	private final static String WINDOWS = "Windows";
	private final static String MAC = "Mac";

	public static void main(String[] args) {
		prepareLocal();
		SpringApplication.run(MoneyManagerGatewayApplication.class, args);
	}

	private static boolean isLocal(){
		return OS_NAME.startsWith(WINDOWS) || OS_NAME.startsWith(MAC);
	}

	private static void prepareLocal(){
		if(isLocal()){
			System.setProperty("spring.profiles.active", "MySqlLocal");
//			System.setProperty("spring.profiles.active", "H2lLocal");
		}
	}

}
