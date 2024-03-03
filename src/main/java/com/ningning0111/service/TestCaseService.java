package com.ningning0111.service;

import com.ningning0111.model.TestCase;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Project: com.ningning0111.service
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 20:49
 * @Description: 测试用例服务 提供测试用例的增删改查 存储在本地 以zip格式存储
 */
public interface TestCaseService {
    /**
     * 添加测试用例
     * @param inputDataList
     * @param outputDataList
     * @param questionId
     * @return
     * @throws IOException
     */
    String saveTestCaseZip(
            List<String> inputDataList,
            List<String> outputDataList,
            Long questionId
    ) throws IOException;

    /**
     * 查找测试用例
     * @param questionId
     * @return
     * @throws IOException
     */
    TestCase findTestCaseZip(Long questionId) throws IOException;
}
