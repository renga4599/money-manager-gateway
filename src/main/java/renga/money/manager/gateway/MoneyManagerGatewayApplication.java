package renga.money.manager.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {renga.money.manager.MoneyManagerBasePackage.class})
public class MoneyManagerGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyManagerGatewayApplication.class, args);
	}

}
