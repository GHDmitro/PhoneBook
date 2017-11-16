package phonebookpac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


//@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class PhoneBookApplication {


	public static void main(String[] args) {
		SpringApplication.run(PhoneBookApplication.class, args);
	}

}
