package com.cpy.OJ.model.dto.questionSubmit;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @Author:成希德
 * 判例信息
 */
@Data
public class JudgeInfo {
    /**
     * 信息
     */
    private String message;
    /**
     * 所用时间
     */
    private Integer time;
    /**
     * 所用内存
     */
    private Integer memory;
}
