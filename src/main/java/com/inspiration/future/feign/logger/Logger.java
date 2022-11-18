package com.inspiration.future.feign.logger;

/**
 * @author zpf
 * @description logger interface
 * @createTime 2022-11-18 09:40
 */
public interface Logger {

    /**
     * @param statement Log statements
     * @param objs      The formatting parameters
     * @description: print info log
     */
    void inPutInfoLog(String statement, Object... objs);

    /**
     * @param statement Log statements
     * @param e      exception
     * @description: print info log
     */
    void inPutInfoLog(String statement, Throwable e);

    /**
     * @param statement Log statements
     * @param objs      The formatting parameters
     * @description: print error log
     */
    void inPutErrorLog(String statement, Object... objs);

    /**
     * @param statement Log statements
     * @param e      exception
     * @description: print error log
     */
    void inPutErrorLog(String statement, Throwable e);

    /**
     * @param statement Log statements
     * @param objs      The formatting parameters
     * @description: print debug log
     */
    void inPutDebugLog(String statement, Object... objs);

    /**
     * @param statement Log statements
     * @param e      exception
     * @description: print debug log
     */
    void inPutDebugLog(String statement, Throwable e);

    /**
     * @param statement Log statements
     * @param objs      The formatting parameters
     * @description: print war log
     */
    void inPutWarLog(String statement, Object... objs);

    /**
     * @param statement Log statements
     * @param e      exception
     * @description: print war log
     */
    void inPutWarLog(String statement, Throwable e);
}
