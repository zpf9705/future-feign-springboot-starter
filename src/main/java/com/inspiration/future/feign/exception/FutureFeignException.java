package com.inspiration.future.feign.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zpf
 * @description future -feign exception
 * @createTime 2022-10-25 10:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FutureFeignException extends RuntimeException{

    private static final long serialVersionUID = -238091758285157331L;

    private String errCode;

    private String errMsg;

    private String subErrCode;

    private String subErrMsg;

    public FutureFeignException(String message, Throwable cause) {
        super(message, cause);
    }

    public FutureFeignException(String message) {
        super(message);
    }

    public FutureFeignException(Throwable cause) {
        super(cause);
    }

    public FutureFeignException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public FutureFeignException(String errCode, String errMsg, String subErrCode, String subErrMsg) {
        super(errCode + ":" + errMsg + ":" + subErrCode + ":" + subErrMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.subErrCode = subErrCode;
        this.subErrMsg = subErrMsg;
    }
}
