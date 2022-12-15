package com.example.myproj.config.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;

@Data
@Slf4j
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @PostConstruct
    public void postConstruct() {
        log.info(toString());
    }

    @NotEmpty
    private String secret;

    @NotEmpty
    private String issuer;

    @NotEmpty
    private String audience;

}
