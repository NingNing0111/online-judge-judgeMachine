package com.ningning0111;

import com.ningning0111.model.TestCase;
import com.ningning0111.service.TestCaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Project: com.ningning0111
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 22:43
 * @Description:
 */
@SpringBootTest
public class TestCaseServiceTest {

    @Autowired
    private TestCaseService testCaseService;

    /**
     * 测试查询
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        TestCase testCaseZip = testCaseService.findTestCaseZip(1000L);
        System.out.println(testCaseZip);
    }

    /**
     * 测试添加
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        List<String> inputDataList = Arrays.asList(
                "1 3",
                "2 4",
                "3 4",
                "100 100",
                "14 14");
        List<String> outputDataList = Arrays.asList(
                "4",
                "6",
                "7",
                "200",
                "28");
        String path = testCaseService.saveTestCaseZip(inputDataList, outputDataList, 1003L);
        System.out.println(path);
    }




}
