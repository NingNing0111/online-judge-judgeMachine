package com.ningning0111;

import cn.hutool.core.util.ArrayUtil;
import com.ningning0111.api.sandbox.LuddSandboxApi;
import com.ningning0111.model.resp.ExecuteCodeResponse;
import com.ningning0111.model.dto.ExecuteCodeRequest;
import com.ningning0111.utils.ZipUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Project: com.ningning0111
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 19:28
 * @Description:
 */
@SpringBootTest
public class SandboxTest {

    @Autowired
    private LuddSandboxApi luddSandboxApi;

    @Test
    public void test1() throws IOException {
        String workPath = System.getProperty("user.dir");
        String testCaseDir = workPath + File.separator + "test_case";
        // 根据题号获取测试用例
        int problemId = 1000;
        String caseZipPath = testCaseDir + File.separator + problemId + ".zip";

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
        List<String> inputData = ZipUtils.getContents(caseZipPath,".in");
        ExecuteCodeResponse response = luddSandboxApi.execute(
                ExecuteCodeRequest.builder()
                        .code(code)
                        .input(inputData)
                        .language("java")
                        .build()
        );

        List<String> executeOutputData = response.getOutput();
        List<String> contents = ZipUtils.getContents(caseZipPath, ".out");
        if(executeOutputData.size() == contents.size()){
            int size = contents.size();
            for (int i=0;i<size;i++){
                String executeData = executeOutputData.get(i).trim();
                String result = contents.get(i).trim();

                if(executeData.equals(result)){
                    System.out.println("答案正确!");
                }else{
                    System.out.println("答案错误");
                }
            }
        }
//        System.out.println(response);
    }

    @Test
    public void test(){
        List<Boolean> passResults = new ArrayList<>(Collections.nCopies(12,false));
        System.out.println(passResults);
    }
}
