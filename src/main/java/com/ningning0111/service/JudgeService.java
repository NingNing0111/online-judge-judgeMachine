package com.ningning0111.service;

import com.ningning0111.common.BaseResponse;
import com.ningning0111.model.dto.JudgeRequest;

/**
 * @Project: com.ningning0111.service
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 18:19
 * @Description: 判题服务
 */
public interface JudgeService {

    BaseResponse judge(JudgeRequest judgeRequest);
}
