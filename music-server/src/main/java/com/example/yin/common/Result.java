package com.example.yin.common;

import com.alibaba.fastjson.JSONObject;
import com.example.yin.handler.BaseErrorInfoInterface;
import com.example.yin.handler.BizException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result<T> {
    private String code;
    private String message;
    private boolean success;
    private T data;
    
    //业务执行成功
    public static <T> Result<T> success(T data){
        Result<T> result=new Result<>();
        result.setCode("200");
        result.setMessage(null);
        result.setSuccess(true);
        result.setData(data);
        
        return result;
    }
    
    public static Result<String> error(String code,String message){
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        result.setData(null);
        return result;
    }
    
    public static Result<String> error(BaseErrorInfoInterface errorInfo) {
        return error(errorInfo.getResultCode(),errorInfo.getResultMsg());
    }
    
    public static Result<String> error(BaseErrorInfoInterface errorInfo,String msg) {
        return error(errorInfo.getResultCode(),msg);
    }
    
    public static Result<String> error(BizException biz) {
        return error(biz.getErrorCode(), biz.getErrorMsg());
    }
    
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
