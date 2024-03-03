package com.ningning0111.service;

import com.ningning0111.api.sandbox.SandboxApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Project: com.ningning0111.service
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/3 02:12
 * @Description:
 */
@Component
@Slf4j
public class SandboxApiFactory implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public SandboxApi createSandboxApi(String sandboxName){
        SandboxApi sandboxApi = applicationContext.getBean(sandboxName + "Api", SandboxApi.class);
        log.info("create a sandbox api:{}",sandboxName);
        return sandboxApi;
    }
}
