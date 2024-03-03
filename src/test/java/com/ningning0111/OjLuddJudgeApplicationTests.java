package com.ningning0111;

import com.ningning0111.utils.ZipUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@SpringBootTest
class OjLuddJudgeApplicationTests {

    @Test
    void contextLoads() throws IOException {
        String workPath = System.getProperty("user.dir");
        System.out.println(workPath + File.separator + "test_case");
        String testCaseDir = workPath + File.separator + "test_case";
        // 根据题号获取测试用例
        int problemId = 1000;
        String caseZipPath = testCaseDir + File.separator + problemId + ".zip";
        ZipFile zf = new ZipFile(caseZipPath);

        InputStream in = new BufferedInputStream(new FileInputStream(caseZipPath));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if(ze.getName().endsWith(".in") || ze.getName().endsWith(".out")){
                long size = ze.getSize();
                String fileName = ze.getName();
                System.err.println("file - " + fileName + " : " + size + " bytes");
                if (size > 0) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zf.getInputStream(ze))
                    );
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();
    }

    @Test
    public void zipUtilsTest() throws IOException {
        String workPath = System.getProperty("user.dir");
        String testCaseDir = workPath + File.separator + "test_case";
        // 根据题号获取测试用例
        int problemId = 1000;
        String caseZipPath = testCaseDir + File.separator + problemId + ".zip";
        System.out.println(caseZipPath);
//        // 获取输入用例
//        List<String> inputData = ZipUtils.getContents(caseZipPath, ".in");
//        System.out.println(inputData);
//        // 获取输出用例
//        List<String> outputData = ZipUtils.getContents(caseZipPath, ".out");
//        System.out.println(outputData);
    }


}
