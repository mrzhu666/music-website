package com.example.yin.handler;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 业务异常类
 * example:
 *  throw new BizException("500",“业务异常");
 */
@Setter
@Getter
public class BizException extends RuntimeException {
    protected String errorCode;
    protected String errorMsg;
    public BizException() {
        super();
    }
    
    public BizException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface.getResultCode());
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMsg = errorInfoInterface.getResultMsg();
    }
    
    public BizException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getResultCode(), cause);
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMsg = errorInfoInterface.getResultMsg();
    }
    
    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
    
    public BizException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public BizException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
    
    @Override
    public String toString() {
        return "BizException{" +
            "errorCode='" + errorCode + '\'' +
            ", errorMsg='" + errorMsg + '\'' +
            '}';
    }
}
