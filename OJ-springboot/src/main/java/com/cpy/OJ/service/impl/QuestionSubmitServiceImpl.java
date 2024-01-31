package com.cpy.OJ.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cpy.OJ.common.ErrorCode;
import com.cpy.OJ.exception.BusinessException;
import com.cpy.OJ.exception.ThrowUtils;
import com.cpy.OJ.mapper.QuestionSubmitMapper;
import com.cpy.OJ.model.entity.Question;
import com.cpy.OJ.model.entity.QuestionSubmit;
import com.cpy.OJ.service.QuestionService;
import com.cpy.OJ.service.QuestionSubmitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 成希德
 * @description 针对表【question_submit(题目)】的数据库操作Service实现
 * @createDate 2024-02-02 21:07:25
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {
    @Resource
    QuestionService questionService;

    @Override
    public void validQuestionSubmit(QuestionSubmit questionSubmit) {
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        Long questionId = questionSubmit.getQuestionId();

        // 创建时，参数不能为空
        ThrowUtils.throwIf(StringUtils.isAnyBlank(language, code), ErrorCode.PARAMS_ERROR);
        // 有参数则校验
        Question question = questionService.getById(questionId);
        if (question==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"题目不存在");
        }

    }
}




