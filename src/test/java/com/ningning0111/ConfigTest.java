package com.ningning0111;

import com.ningning0111.config.JudgeServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Project: com.ningning0111
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 21:29
 * @Description:
 */
@SpringBootTest
public class ConfigTest {

    @Autowired
    private JudgeServerConfig judgeServerConfig;

    @Test
    public void test1() {
        System.out.println(judgeServerConfig);
    }

}
