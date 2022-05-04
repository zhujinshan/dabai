package com.dabai.proxy.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/05/03 11:42
 */
@RestControllerAdvice
@Slf4j
public class BizExceptionHandler {

    public static final ThreadLocal<String> ERROR_MESSAGE = new ThreadLocal<>();
    /**
     * 用于处理通用异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorList = Lists.newArrayList();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorList.add(fieldError.getDefaultMessage());
        }
        String errorMsg = ERROR_MESSAGE.get();
        if (StringUtils.isEmpty(errorMsg)) {
            errorMsg = String.join(",", errorList);
        }

        ERROR_MESSAGE.remove();
        return Result.genResult(ResultCode.PARAMETER_EXCEPTION.getValue(), errorMsg, null);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, NullPointerException.class, IllegalArgumentException.class})
    public Result<String> paramException(final Throwable e) {
        log.error("dabai-web param exception", e);
        return Result.genResult(ResultCode.FAILURE.getValue(), getErrorMessage(e), null);
    }

    @ExceptionHandler({SQLException.class})
    public Result<String> databaseException(final Throwable e) {
        log.error("dabai-web database exception", e);
        return Result.genResult(ResultCode.DATABASE_EXCEPTION.getValue(),
                ResultCode.DATABASE_EXCEPTION.getReason(), null);
    }

    @ExceptionHandler(Exception.class)
    public Result<String> globalException(final HttpServletRequest request, final Throwable e) {
        log.error("dabai-web exception", e);
        return Result.genResult(ResultCode.FAILURE.getValue(),
                String.format("%s => %s", request.getRequestURI() + " " + ResultCode.FAILURE.getReason(), getErrorMessage(e)), null);
    }

    private String getErrorMessage(Throwable throwable) {
        String errorMessage = ERROR_MESSAGE.get();
        if (StringUtils.isEmpty(errorMessage)) {
            errorMessage = throwable.getMessage();
        }
        ERROR_MESSAGE.remove();
        return errorMessage;
    }
}
