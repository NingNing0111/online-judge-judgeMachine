package com.ningning0111;

import com.ningning0111.common.BaseResponse;
import com.ningning0111.model.dto.JudgeConfig;
import com.ningning0111.model.dto.JudgeRequest;
import com.ningning0111.service.JudgeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Project: com.ningning0111
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/3 03:30
 * @Description:
 */
@SpringBootTest
public class JudgeServiceTest {
    @Autowired
    private JudgeService judgeService;

    @Test
    public void test1() {
        JudgeRequest judgeRequest = new JudgeRequest();
        String code = "import java.util.Scanner;\n" +
                "\n" +
                "public class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        int a = scanner.nextInt();\n" +
                "        int b = scanner.nextInt();\n" +
                "        System.out.println(a + b);\n" +
                "    }\n" +
                "}\n";
        JudgeConfig judgeConfig = new JudgeConfig();
        judgeConfig.setMaxMemory(100000000L);
        judgeConfig.setMaxTime(2000L);
        judgeConfig.setSandboxName("luddSandbox");
        judgeRequest.setCode(code);
        judgeRequest.setId(1L);
        judgeRequest.setLanguage("java");
        judgeRequest.setQuestionId(1003L);
        judgeRequest.setConfig(
                judgeConfig
        );

        BaseResponse resp = judgeService.judge(judgeRequest);

        System.out.println(resp);
    }
}
