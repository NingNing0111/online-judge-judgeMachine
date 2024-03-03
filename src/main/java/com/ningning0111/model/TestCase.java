package com.ningning0111.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Project: com.ningning0111.model
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/2 17:58
 * @Description: 题目的测试用例 可以理解为标准答案
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    /**
     * 题目ID
     */
    private Long id;

    /**
     * 测试用例的输入数据
     */
    private List<String> inputData;
    /**
     * 测试用例的输出数据
     */
    private List<String> outputData;
    /**
     * 用例是否通过
     */
    private List<Boolean> passInfo;
    /**
     * 测试用例的数量
     */
    private Integer count;
}
