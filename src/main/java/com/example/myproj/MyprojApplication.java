package com.example.myproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * MyprojApplication.
 *
 * @author Evgeny_Ageev
 */
@EnableJpaAuditing
@SpringBootApplication
//@EnableTransactionManagement
@ConfigurationPropertiesScan("com.example.myproj.config.props")
public class MyprojApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyprojApplication.class, args);
    }

}
