package com.cpy.OJ.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpy.OJ.common.ErrorCode;
import com.cpy.OJ.exception.BusinessException;
import com.cpy.OJ.exception.ThrowUtils;
import com.cpy.OJ.mapper.QuestionSubmitMapper;
import com.cpy.OJ.model.entity.Question;
import com.cpy.OJ.model.entity.QuestionSubmit;
import com.cpy.OJ.model.entity.User;
import com.cpy.OJ.model.vo.QuestionSubmitVO;
import com.cpy.OJ.model.vo.QuestionVO;
import com.cpy.OJ.model.vo.UserVO;
import com.cpy.OJ.service.QuestionService;
import com.cpy.OJ.service.QuestionSubmitService;
import com.cpy.OJ.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

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
    @Resource
    UserService userService;

    @Override
    public void validQuestionSubmit(QuestionSubmit questionSubmit) {
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        Long questionId = questionSubmit.getQuestionId();

        // 创建时，参数不能为空
        ThrowUtils.throwIf(StringUtils.isAnyBlank(language, code), ErrorCode.PARAMS_ERROR);
        // 有参数则校验
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目不存在");
        }

    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> page) {
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<QuestionSubmit> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return questionSubmitVOPage;
        }
        //填充数据
        List<QuestionSubmitVO> qsVORecords = new LinkedList<>();

        for (QuestionSubmit record : records) {
            QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
            BeanUtil.copyProperties(record, questionSubmitVO);
            Long questionId = record.getQuestionId();
            Long userId = record.getUserId();
            Question question = questionService.getById(questionId);
            QuestionVO questionVO = QuestionVO.objToVo(question);
            User user = userService.getById(userId);
            UserVO userVO = new UserVO();
            BeanUtil.copyProperties(user, userVO);
            questionSubmitVO.setQuestionVO(questionVO);
            questionSubmitVO.setUserVO(userVO);
            qsVORecords.add(questionSubmitVO);
        }
        questionSubmitVOPage.setRecords(qsVORecords);
        return questionSubmitVOPage;
    }
}




