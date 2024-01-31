package com.cpy.OJ.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cpy.OJ.annotation.AuthCheck;
import com.cpy.OJ.common.BaseResponse;
import com.cpy.OJ.common.DeleteRequest;
import com.cpy.OJ.common.ErrorCode;
import com.cpy.OJ.common.ResultUtils;
import com.cpy.OJ.constant.UserConstant;
import com.cpy.OJ.exception.BusinessException;
import com.cpy.OJ.exception.ThrowUtils;
import com.cpy.OJ.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.cpy.OJ.model.entity.QuestionSubmit;
import com.cpy.OJ.model.entity.User;
import com.cpy.OJ.model.vo.QuestionSubmitVO;
import com.cpy.OJ.service.QuestionSubmitService;
import com.cpy.OJ.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/licpy">程序员鱼皮</a>
 * @from <a href="https://cpy.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/questionSubmit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        if (questionSubmitAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitAddRequest, questionSubmit);
        String judgeInfo = questionSubmit.getJudgeInfo();
        if (judgeInfo != null) {
            questionSubmit.setJudgeInfo(GSON.toJson(judgeInfo));
        }
        questionSubmitService.validQuestionSubmit(questionSubmit);
        User loginUser = userService.getLoginUser(request);
        questionSubmit.setUserId(loginUser.getId());
        boolean result = questionSubmitService.save(questionSubmit);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionSubmitId = questionSubmit.getId();
        return ResultUtils.success(newQuestionSubmitId);
    }

}