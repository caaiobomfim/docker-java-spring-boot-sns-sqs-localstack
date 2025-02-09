package caaiobomfim.application;

import caaiobomfim.core.LocalStackProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "caaiobomfim")
@EnableConfigurationProperties(LocalStackProperties.class)
@EnableScheduling
public class DockerJavaSpringBootSnsSqsLocalstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerJavaSpringBootSnsSqsLocalstackApplication.class, args);
	}

}