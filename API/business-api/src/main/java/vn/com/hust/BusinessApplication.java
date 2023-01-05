package vn.com.hust;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import vn.com.core.common.config.CommonConfig;

@EnableAsync
@SpringBootApplication
@ComponentScan({"vn.com.*"})
@EnableJpaRepositories("vn.com.*")
@EntityScan("vn.com.*")
@Import(CommonConfig.class)
public class BusinessApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
