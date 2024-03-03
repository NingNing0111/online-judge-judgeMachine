package com.ningning0111.api.sandbox;

import com.ningning0111.config.SandboxConfig;
import com.ningning0111.exception.BusinessException;
import com.ningning0111.exception.ThrowUtils;
import com.ningning0111.model.enums.ErrorCode;
import com.ningning0111.model.resp.ExecuteCodeResponse;
import com.ningning0111.model.dto.ExecuteCodeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Project: com.ningning0111.api.sandbox
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 18:26
 * @Description:
 */
@Component("luddSandboxApi")
@Slf4j
public class LuddSandboxApi extends SandboxApi{
    private String baseUrl;
    private final RestTemplate restTemplate;
    private final SandboxConfig sandboxConfig;

    public LuddSandboxApi(RestTemplate restTemplate, SandboxConfig sandboxConfig) {
        this.restTemplate = restTemplate;
        this.sandboxConfig = sandboxConfig;
        this.baseUrl = sandboxConfig.getLuddSandboxUrl();
    }

    public String checkHealth(){
        String result = restTemplate.getForObject(baseUrl + "/check/health", String.class);
        return result;
    }

    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest){

        // 设置请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.set("auth", sandboxConfig.getLuddSecretKey());
        headers.set("content-type","application/json;charset-utf8");
        HttpEntity<ExecuteCodeRequest> httpEntity = new HttpEntity<>(executeCodeRequest, headers);

        ResponseEntity<ExecuteCodeResponse> response = restTemplate.exchange(baseUrl + "/execute", HttpMethod.POST, httpEntity, ExecuteCodeResponse.class);

        ExecuteCodeResponse executeCodeResponse = response.getBody();

        return executeCodeResponse;
    }
}
