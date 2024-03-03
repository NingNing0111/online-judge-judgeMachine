package com.ningning0111.model.dto;

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
public class ExecuteCodeRequest {
    /**
     * 输入数据
     */
    private List<String> input;
    /**
     * 代码
     */
    private String code;
    /**
     * 语言
     */
    private String language;
}
