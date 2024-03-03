package com.ningning0111.service.impl;

import com.ningning0111.api.sandbox.SandboxApi;
import com.ningning0111.common.BaseResponse;
import com.ningning0111.common.ErrorCode;
import com.ningning0111.common.ResultUtils;
import com.ningning0111.model.TestCase;
import com.ningning0111.model.dto.ExecuteCodeRequest;
import com.ningning0111.model.dto.JudgeConfig;
import com.ningning0111.model.dto.JudgeRequest;
import com.ningning0111.model.enums.ExecuteStatus;
import com.ningning0111.model.enums.JudgeStatusEnum;
import com.ningning0111.model.resp.ExecuteCodeResponse;
import com.ningning0111.model.vo.JudgeInfo;
import com.ningning0111.model.vo.JudgeVo;
import com.ningning0111.service.JudgeService;
import com.ningning0111.service.SandboxApiFactory;
import com.ningning0111.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Project: com.ningning0111.service.impl
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/3 02:17
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JudgeServiceImpl implements JudgeService {
    private final SandboxApiFactory sandboxApiFactory;
    private final TestCaseService testCaseService;


    /**
     * 判题流程
     * 1. 获取判题配置信息（时间限制、内存限制）、代码、题目ID、判题代码、语言
     * 2. 根据题目ID获取到测试用例列表
     * 3. 代码 + 测试用例 + 语言 发送给代码沙箱 得到响应结果
     * 4. 根据代码执行结果得到判题结果
     * 5. 返回
     * @param judgeRequest
     * @return
     */
    @Override
    public BaseResponse judge(JudgeRequest judgeRequest) {
        // 1. 获取题目ID、代码、语言、判题配置
        Long questionId = judgeRequest.getQuestionId();
        String code = judgeRequest.getCode();
        String language = judgeRequest.getLanguage();
        JudgeConfig config = judgeRequest.getConfig();
        String sandboxName = config.getSandboxName();
        try {
            // 2. 获取测试用例
            TestCase testCaseZip = testCaseService.findTestCaseZip(questionId);
            log.error("{}",testCaseZip);
            // 创建代码沙箱请求对象
            ExecuteCodeRequest sandboxRequest = ExecuteCodeRequest.builder()
                    .input(testCaseZip.getInputData())
                    .language(language)
                    .code(code)
                    .build();
            ExecuteCodeResponse response = execute(sandboxName, sandboxRequest);
            return transformVo(response,config, judgeRequest,testCaseZip);

        }catch (IOException e) {
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }

    private ExecuteCodeResponse execute(String sandboxName, ExecuteCodeRequest sandboxRequest){
        SandboxApi sandboxApi = sandboxApiFactory.createSandboxApi(sandboxName);
        return sandboxApi.execute(sandboxRequest);
    }

    private BaseResponse transformVo(ExecuteCodeResponse response, JudgeConfig config, JudgeRequest judgeRequest, TestCase testCase){
        JudgeStatusEnum judgeStatusEnum = JudgeStatusEnum.ACCEPTED;
        // 如果执行不成功
        if(response.getStatus() != ExecuteStatus.EXECUTE_OK.getStatus()){
            if(response.getMessage().equals(JudgeStatusEnum.COMPILE_ERROR.getText())){
                judgeStatusEnum = JudgeStatusEnum.COMPILE_ERROR;
                return ResultUtils.success(JudgeVo.builder()
                        .id(judgeRequest.getId())
                        .questionId(judgeRequest.getQuestionId())
                        .count(testCase.getCount())
                        .passInfo(compareResult(testCase.getOutputData(), Arrays.asList("")))
                        .status(judgeStatusEnum.getText())
                        .judgeInfo(
                                JudgeInfo.builder()
                                        .message(judgeStatusEnum.getValue())
                                        .build()
                        )
                        .build());
            }else{
                String errorMessage = response.getMessage();
                return ResultUtils.error(ErrorCode.SUCCESS,errorMessage);
            }
        }
        // 时间限制、内存限制
        Long maxMemory = config.getMaxMemory();
        Long maxTime = config.getMaxTime();
        // 程序运行时间、内存消耗
        Long useMemory = response.getJudgeInfo().getMemory();
        Long useTime = response.getJudgeInfo().getTime();
        if(useMemory >= maxMemory){
            judgeStatusEnum = JudgeStatusEnum.MEMORY_LIMIT_EXCEEDED;
        }
        if(useTime >= maxTime){
            judgeStatusEnum = JudgeStatusEnum.TIME_LIMIT_EXCEEDED;
        }
        // 获取比对结果
        List<Boolean> passResults = compareResult(testCase.getOutputData(), response.getOutput());
        // 输入数据脱敏处理
        List<String> input = desInputData(testCase.getInputData(), passResults);
        JudgeInfo judgeInfo = response.getJudgeInfo();
        judgeInfo.setMessage(judgeStatusEnum.getValue());
        JudgeVo judgeVo = JudgeVo.builder()
                .id(judgeRequest.getId())
                .questionId(judgeRequest.getQuestionId())
                .count(testCase.getCount())
                .judgeInfo(response.getJudgeInfo())
                .passInfo(passResults)
                .status(judgeStatusEnum.getText())
                .userOutput(response.getOutput())
                .inputData(input)
                .build();
        return ResultUtils.success(judgeVo);
    }

    private List<Boolean> compareResult(List<String> answers, List<String> outputDataList){
        int size = answers.size();
        List<Boolean> passResults = new ArrayList<>(Collections.nCopies(size,false));
        int outSize = outputDataList.size();
        for (int i = 0; i < outSize; i++) {
            // 答案
            String answer = answers.get(i).trim();
            // 用户程序执行的结果
            String output = outputDataList.get(i).trim();
            if(answer.equals(output)){
                passResults.set(i,true);
            }
        }
        return  passResults;
    }

    private List<String> desInputData(List<String> inputData, List<Boolean> passResults) {
        List<String> input = new ArrayList<>(Collections.nCopies(inputData.size(), "-"));
        for (int i = 0; i < passResults.size(); i++) {
            if(!passResults.get(i)){
                input.set(i,inputData.get(i));
                return input;
            }
        }
        return input;
    }



}
