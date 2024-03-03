package com.ningning0111.service.impl;

import cn.hutool.core.io.FileUtil;
import com.ningning0111.config.JudgeServerConfig;
import com.ningning0111.model.TestCase;
import com.ningning0111.service.TestCaseService;
import com.ningning0111.utils.ZipUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Project: com.ningning0111.service.impl
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 21:25
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private final JudgeServerConfig judgeServerConfig;

    @Override
    public String saveTestCaseZip(
            List<String> inputDataList,
            List<String> outputDataList,
            Long questionId) throws IOException {
        String zipPath = getZipPath(questionId);
        addCaseDataList(zipPath,inputDataList,".in");
        addCaseDataList(zipPath,outputDataList,".out");
        return zipPath;
    }

    @Override
    public TestCase findTestCaseZip(Long questionId) throws IOException {
        String zipPath = getZipPath(questionId);
        List<String> inputDataList = ZipUtils.getContents(zipPath, ".in");
        List<String> outputDataList = ZipUtils.getContents(zipPath, ".out");

        return TestCase.builder()
                .id(questionId)
                .count(inputDataList.size())
                .inputData(inputDataList)
                .outputData(outputDataList)
                .build();
    }

    private String getZipPath(Long questionId){
        String rootPath = judgeServerConfig.getCasePath() + File.separator + judgeServerConfig.getCaseDirName();
        if (!FileUtil.exist(rootPath)) {
            FileUtil.mkdir(rootPath);
        }

        return rootPath + File.separator + questionId + ".zip";
    }

    private void addCaseDataList(String zipPath,List<String> contentList, String endSuffix) throws IOException {
        int size = contentList.size();
        for(int i=0; i < size;i++){
            ZipUtils.saveContents(zipPath,contentList.get(i),(i+1) + endSuffix,true);
        }
    }
}
