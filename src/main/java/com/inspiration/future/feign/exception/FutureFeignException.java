package com.inspiration.future.feign.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * future -feign exception
 *
 * @author zpf
 * @since 1.1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FutureFeignException extends RuntimeException{

    private static final long serialVersionUID = -238091758285157331L;

    /**
     * error code
     */
    private String errCode;

    /**
     * error message
     */
    private String errMsg;

    /**
     * back - end see code
     */
    private String subErrCode;

    /**
     * back - end see msg
     */
    private String subErrMsg;

    /**
     * throw msg and cause(ex)
     * @param message error msg
     * @param cause   {@link Throwable#getCause()}
     */
    public FutureFeignException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * throw msg
     * @param message error msg
     */
    public FutureFeignException(String message) {
        super(message);
    }

    /**
     * throw cause
     * @param cause   {@link Throwable#getCause()}
     */
    public FutureFeignException(Throwable cause) {
        super(cause);
    }

    /**
     * throw errCode and errMsg
     * @param errCode
     * @param errMsg
     * @rule simple
     */
    public FutureFeignException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * throw errCode and errMsg and show back-end programmer ex-msg of code
     * @param errCode
     * @param errMsg
     * @rule simple
     */
    public FutureFeignException(String errCode, String errMsg, String subErrCode, String subErrMsg) {
        super(errCode + ":" + errMsg + ":" + subErrCode + ":" + subErrMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.subErrCode = subErrCode;
        this.subErrMsg = subErrMsg;
    }
}
