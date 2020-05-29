package hmm.architecturestudio.management;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hmm.architecturestudio.management.util.MyDatabaseSeeder;

@SpringBootApplication
public class ManagementApplication {

	@Autowired
	private MyDatabaseSeeder myDatabaseSeeder;

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}

	@Bean
	InitializingBean seedDatabase() {
		return () -> {
			myDatabaseSeeder.seedDatabase();
		};
	}

}
