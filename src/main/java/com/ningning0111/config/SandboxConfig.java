package com.ningning0111.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Project: com.ningning0111.config
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 19:19
 * @Description:
 */
@Configuration
@ConfigurationProperties(
        prefix = "judge.sandbox"
)
@Data
public class SandboxConfig {
    private String luddSandboxUrl="http://localhost:8329";
}
