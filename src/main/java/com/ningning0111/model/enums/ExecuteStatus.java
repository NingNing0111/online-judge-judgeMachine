package com.ningning0111.model.enums;

/**
 * @Project: com.ningning0111.model.enums
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/3/3 02:48
 * @Description:
 */
public enum ExecuteStatus {

    /**
     * 执行正常
     */
    EXECUTE_OK(1),
    /**
     * 程序代码错误
     */
    EXECUTE_CODE_ERROR(2),
    /**
     * 系统错误
     */
    EXECUTE_SYSTEM_ERROR(3);

    private Integer status;
    ExecuteStatus(Integer status){
        this.status = status;
    }
    public Integer getStatus() {
        return this.status;
    }
}
