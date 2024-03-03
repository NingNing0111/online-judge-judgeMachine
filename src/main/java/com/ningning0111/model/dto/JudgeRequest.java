package com.ningning0111.model.dto;

import lombok.Data;

/**
 * @Project: com.ningning0111.model
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 18:16
 * @Description: 判题请求
 */
@Data
public class JudgeRequest {
    /**
     * 提交ID
     */
    private Long id;

    /**
     * 题目ID
     */
    private Long questionId;

    /**
     * 提交的代码
     */
    private String code;

    /**
     * 提交的语言
     */
    private String language;

    /**
     * 判题配置
     */
    private JudgeConfig config;
}
