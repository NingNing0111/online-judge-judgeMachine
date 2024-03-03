package com.ningning0111.model.dto;

import lombok.Data;

/**
 * @Project: com.ningning0111.model.dto
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/3 02:18
 * @Description:
 */
@Data
public class JudgeConfig {
    private String sandboxName = "luddSandbox";
    /**
     * 时间限制大小 单位ms
     */
    private Long maxTime;
    /**
     * 内存限制大小 单位MB -> 1048576B
     */
    private Long maxMemory;

}
