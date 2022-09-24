package com.example.yin.handler;

import com.example.yin.common.R;
import com.example.yin.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * 用于捕获异常，并统一返回
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * 捕获业务错误
     */
    @ExceptionHandler(value = BizException.class)
    public Result<String> bizExceptionHandler(BizException biz){
        log.error("业务异常："+biz); //日志记录，用于复查
        return Result.error(biz);
    }
    
    /**
     * 捕获其它所有异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest req,Exception e){
        log.error("服务内部异常：",e);
        return Result.error(CommonEnum.INTERNAL_SERVER_ERROR,"服务器异常："+e.getMessage());
    }
    
    /**
     * 兼容Validation校验框架：参数忽略异常
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result<String> parameterMissingExceptionHandler(MissingServletRequestParameterException e){
        log.error(e.getMessage(), e);
        return Result.error(CommonEnum.BODY_NOT_MATCH, "请求参数：" + e.getParameterName() + " 不能为空");
    }
    
    /**
     * RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     * 兼容Validation校验框架：参数效验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<String> parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        BindingResult binding = e.getBindingResult();
        //有错误信息，则返回错误信息。无则返回默认错误信息
        if (binding.hasErrors()) {
            StringBuilder builder = new StringBuilder("参数校检失败：");
            //拼接校检错误信息
            for (FieldError error : binding.getFieldErrors()) {
                builder.append(error.getField())
                    .append(":")
                    .append(error.getDefaultMessage())
                    .append("，");
            }
            return Result.error(CommonEnum.BODY_NOT_MATCH,builder.toString());
        }
        return Result.error(CommonEnum.BODY_NOT_MATCH);
    }
    
    /**
     *  RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *  兼容Validation校验框架：参数效验异常
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public Result<String> handleConstraintViolationException(ConstraintViolationException e){
        log.error(e.getMessage(), e);
        return Result.error(CommonEnum.BODY_NOT_MATCH, "参数校检失败："+e.getMessage());
    }
    
    /**
     * 兼容Validation校验框架：缺少请求体异常处理器
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<String> parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return Result.error(CommonEnum.BODY_NOT_MATCH, "参数体不能为空");
    }
    
    /**
     * NoHandlerFoundException：不存在路径
     * HttpRequestMethodNotSupportedException：路径存在，但是请求方法不支持
     */
    @ExceptionHandler({NoHandlerFoundException.class,HttpRequestMethodNotSupportedException.class})
    public Result<String> handleNoHandlerFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(CommonEnum.NOT_FOUND,"找不到对应资源："+e.getMessage());
    }
    

}
