package ge.tsu.ByteSized;

import ge.tsu.ByteSized.config.BlogProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties.class)
@EnableScheduling
public class ByteSizedApplication {

	public static void main(String[] args) {
		SpringApplication.run(ByteSizedApplication.class, args);
	}

}
