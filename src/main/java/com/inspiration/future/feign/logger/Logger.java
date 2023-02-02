package com.inspiration.future.feign.logger;

import org.slf4j.LoggerFactory;

/**
 * logger rule way to use
 *
 * @author zpf
 * @since 1.1.0
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
     * @param e         exception
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
     * @param e         exception
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
     * @param e         exception
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
     * @param e         exception
     * @description: print war log
     */
    void inPutWarLog(String statement, Throwable e);

    static Logger defaultLogger() {
        org.slf4j.Logger logger = LoggerFactory.getLogger(FeignLogProvider.class);
        return new Logger() {
            @Override
            public void inPutInfoLog(String statement, Object... objs) {
                logger.info(statement, objs);
            }

            @Override
            public void inPutInfoLog(String statement, Throwable e) {
                logger.info(statement, e);
            }

            @Override
            public void inPutErrorLog(String statement, Object... objs) {
                logger.error(statement, objs);
            }

            @Override
            public void inPutErrorLog(String statement, Throwable e) {
                logger.error(statement, e);
            }

            @Override
            public void inPutDebugLog(String statement, Object... objs) {
                logger.debug(statement, objs);
            }

            @Override
            public void inPutDebugLog(String statement, Throwable e) {
                logger.debug(statement, e);
            }

            @Override
            public void inPutWarLog(String statement, Object... objs) {
                logger.warn(statement, objs);
            }

            @Override
            public void inPutWarLog(String statement, Throwable e) {
                logger.warn(statement, e);
            }
        };
    }
}
