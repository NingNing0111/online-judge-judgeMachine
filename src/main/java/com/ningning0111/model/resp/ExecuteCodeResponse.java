package com.ningning0111.model.resp;

import com.ningning0111.model.vo.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Project: com.ningning0111.model
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 17:25
 * @Description:
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeResponse {
    /**
     *输出数据
     */
    private List<String> output;

    /**
     * 接口信息
     */
    private String message;
    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;
}
