package com.ningning0111.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.ningning0111.api.sandbox.SandboxApi;
import com.ningning0111.common.BaseResponse;
import com.ningning0111.common.ErrorCode;
import com.ningning0111.common.ResultUtils;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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


    /**
     * 判题流程
     * 1. 获取判题配置信息（时间限制、内存限制）、代码、语言、测试用例
     * 2. 代码 + 测试用例 + 语言 发送给代码沙箱 得到响应结果
     * 3. 根据代码执行结果得到判题结果
     * 4. 返回
     * @param judgeRequest
     * @return
     */
    @Override
    public BaseResponse judge(JudgeRequest judgeRequest) {
        // 1. 获取代码、语言、判题配置、测试用例
        String code = judgeRequest.getCode();
        String language = judgeRequest.getLanguage();
        JudgeConfig config = judgeRequest.getConfig();
        String sandboxName = config.getSandboxName();
        List<String> inputData = judgeRequest.getInputData();
        try {
            // 2. 创建代码沙箱请求对象 得到响应结果
            ExecuteCodeRequest sandboxRequest = ExecuteCodeRequest.builder()
                    .input(inputData)
                    .language(language)
                    .code(code)
                    .build();
            ExecuteCodeResponse response = execute(sandboxName, sandboxRequest);
            // 3 4. 得到判题结果并返回
            return transformVo(response, judgeRequest);

        }catch (Exception e) {
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }

    private ExecuteCodeResponse execute(String sandboxName, ExecuteCodeRequest sandboxRequest){
        SandboxApi sandboxApi = sandboxApiFactory.createSandboxApi(sandboxName);
        return sandboxApi.execute(sandboxRequest);
    }

    private BaseResponse transformVo(ExecuteCodeResponse response,  JudgeRequest judgeRequest){
        // 默认答案错误
        JudgeStatusEnum judgeStatusEnum = JudgeStatusEnum.WRONG_ANSWER;
        JudgeConfig config = judgeRequest.getConfig();

        // 如果执行不成功
        if(!Objects.equals(response.getStatus(), ExecuteStatus.EXECUTE_OK.getStatus())){
            if(response.getMessage().equals(JudgeStatusEnum.COMPILE_ERROR.getText())){
                judgeStatusEnum = JudgeStatusEnum.COMPILE_ERROR;

                return ResultUtils.success(JudgeVo.builder()
                        .count(judgeRequest.getInputData().size())
                        .passInfo(compareResult(judgeRequest.getOutputData(), Arrays.asList("")))
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
        List<Boolean> passResults = compareResult(judgeRequest.getOutputData(), response.getOutput());
        // 如果都为true 则通过
        if(passResults.stream().allMatch(value -> value)){
            judgeStatusEnum = JudgeStatusEnum.ACCEPTED;
        }
        // 输入数据脱敏处理
        List<String> input = desInputData(judgeRequest.getInputData(), passResults);
        JudgeInfo judgeInfo = response.getJudgeInfo();
        judgeInfo.setMessage(judgeStatusEnum.getValue());
        JudgeVo judgeVo = JudgeVo.builder()
                .count(judgeRequest.getInputData().size())
                .judgeInfo(response.getJudgeInfo())
                .passInfo(passResults)
                .status(judgeStatusEnum.getText())
                .userOutput(response.getOutput())
                .inputData(input)
                .build();
        return ResultUtils.success(judgeVo);
    }

    /**
     * 比对结果
     * @param answers
     * @param outputDataList
     * @return
     */
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

    /**
     * 输入数据脱敏处理
     * @param inputData
     * @param passResults
     * @return
     */
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
