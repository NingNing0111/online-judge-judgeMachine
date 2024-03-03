package com.ningning0111.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Project: com.ningning0111.config
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 19:20
 * @Description:
 */
@Configuration
@ConfigurationProperties(
        prefix = "judge-server"
)
@Data
public class JudgeServerConfig {

    private String caseDirName="test_case";

    private String casePath = System.getProperty("user.dir");

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
