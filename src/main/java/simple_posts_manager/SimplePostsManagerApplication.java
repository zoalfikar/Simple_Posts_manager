package simple_posts_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class SimplePostsManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SimplePostsManagerApplication.class, args);
	}

}
