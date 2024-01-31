package com.cpy.OJ.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cpy.OJ.model.entity.QuestionSubmit;

public interface QuestionSubmitService extends IService<QuestionSubmit> {
    void validQuestionSubmit(QuestionSubmit questionSubmit);
}