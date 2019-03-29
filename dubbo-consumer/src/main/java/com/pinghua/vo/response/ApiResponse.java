package com.pinghua.vo.response;

import com.pinghua.enums.ApiServerCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Waylon
 * @date 2018/8/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private Integer code;

    private String msg;

    private T result;


    public ApiResponse(Integer errorCode, String msg) {
        this.code = errorCode;
        this.msg = msg;
    }

    public static <T> ApiResponse getSuccessResponse(T result) {
        return new ApiResponse(ApiServerCode.SUCCESS.getCode(), ApiServerCode.SUCCESS.getMsg(), result);
    }

    public static <T> ApiResponse getSuccessResponse(T result, String message) {
        return new ApiResponse(ApiServerCode.SUCCESS.getCode(), message, result);
    }

    public static ApiResponse getSuccessResponse() {
        return new ApiResponse(ApiServerCode.SUCCESS.getCode(), "处理成功", null);
    }

    public static <T> ApiResponse getSuccessResponse(String message) {
        return new ApiResponse(ApiServerCode.SUCCESS.getCode(), message, null);
    }

    public static ApiResponse getFailResponse() {
        return new ApiResponse(ApiServerCode.FAIL.getCode(), ApiServerCode.FAIL.getMsg());
    }

    public static ApiResponse getFailResponse(Integer code, String errorMsg) {
        return new ApiResponse(code, errorMsg);
    }

    public static ApiResponse getFailResponse(String errorMsg) {
        return new ApiResponse(ApiServerCode.FAIL.getCode(), errorMsg);
    }

    public static<T> ApiResponse getFailResponse(Integer code, String errorMsg, T result) {
        return new ApiResponse(code, errorMsg, result);
    }

    public static ApiResponse getFailResponse(ApiServerCode apiServerCode) {
        return new ApiResponse(apiServerCode.getCode(), apiServerCode.getMsg());
    }
}
