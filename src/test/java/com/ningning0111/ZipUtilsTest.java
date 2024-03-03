package com.ningning0111;

import com.ningning0111.utils.ZipUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project: com.ningning0111
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 21:40
 * @Description:
 */

public class ZipUtilsTest {

    @Test
    public void test1() throws IOException {
        String workPath = System.getProperty("user.dir");
        String testCaseDir = workPath + File.separator + "test_case";
        // 根据题号获取测试用例
        int problemId = 1000;
        String caseZipPath = testCaseDir + File.separator + problemId + ".zip";

        List<String> inputs = new ArrayList<>();
        inputs.add("100 100");
        inputs.add("300 300");
        ZipUtils.saveContents(caseZipPath,"100 100","1.in",true);
        ZipUtils.saveContents(caseZipPath,"300 300","1.out",true);
    }

    @Test
    public void test2() throws IOException {
        String workPath = System.getProperty("user.dir");
        String testCaseDir = workPath + File.separator + "test_case";
        // 根据题号获取测试用例
        int problemId = 1000;
        String caseZipPath = testCaseDir + File.separator + problemId + ".zip";
        List<String> inputList = ZipUtils.getContents(caseZipPath, ".in");
        List<String> outList = ZipUtils.getContents(caseZipPath, ".out");

        System.out.println(inputList);
        System.out.println(outList);
    }

    @Test
    public void test3() throws IOException {
        String workPath = System.getProperty("user.dir");
        String testCaseDir = workPath + File.separator + "test_case";
        int problemId = 1003;
        String caseZipPath = testCaseDir + File.separator + problemId + ".zip";
        List<String> contents = ZipUtils.getContents(caseZipPath, ".in");
        System.out.println(contents);
    }

}
