/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_long_ } from "../models/BaseResponse_long_";
import type { BaseResponse_Page_QuestionSubmitVO_ } from "../models/BaseResponse_Page_QuestionSubmitVO_";
import type { QuestionSubmitAddRequest } from "../models/QuestionSubmitAddRequest";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class QuestionSubmitControllerService {
  /**
   * addQuestionSubmit
   * @param questionSubmitAddRequest questionSubmitAddRequest
   * @returns BaseResponse_long_ OK
   * @returns any Created
   * @throws ApiError
   */
  public static addQuestionSubmitUsingPost(
    questionSubmitAddRequest: QuestionSubmitAddRequest
  ): CancelablePromise<BaseResponse_long_ | any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/questionSubmit/add",
      body: questionSubmitAddRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }

  /**
   * listQuestionSubmitVOByPage
   * @param current
   * @param id
   * @param pageSize
   * @param sortField
   * @param sortOrder
   * @param title
   * @param userId
   * @returns BaseResponse_Page_QuestionSubmitVO_ OK
   * @throws ApiError
   */
  public static listQuestionSubmitVoByPageUsingGet(
    current?: number,
    id?: number,
    pageSize?: number,
    sortField?: string,
    sortOrder?: string,
    title?: string,
    userId?: number
  ): CancelablePromise<BaseResponse_Page_QuestionSubmitVO_> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/questionSubmit/list/page/vo",
      query: {
        current: current,
        id: id,
        pageSize: pageSize,
        sortField: sortField,
        sortOrder: sortOrder,
        title: title,
        userId: userId,
      },
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    });
  }
}
