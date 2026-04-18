package ge.tsu.ByteSized;

import ge.tsu.ByteSized.config.BlogProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties.class)
public class ByteSizedApplication {

	public static void main(String[] args) {
		SpringApplication.run(ByteSizedApplication.class, args);
	}

}
