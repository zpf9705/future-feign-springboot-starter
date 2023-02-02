package com.inspiration.future.feign.logger;

import org.slf4j.Marker;

/**
 * slf4j abstract logger
 *
 * @author zpf
 * @since 1.1.0
 */
public abstract class AbstractLogger {

    protected void info(org.slf4j.Logger logger, String statement, Object... objs) {
        logger.info(statement, objs);
    }

    protected void info(org.slf4j.Logger logger, String statement, Throwable e) {
        logger.info(statement, e);
    }

    protected void error(org.slf4j.Logger logger, String statement, Object... objs) {
        logger.error(statement, objs);
    }

    protected void error(org.slf4j.Logger logger, String statement, Throwable e) {
        logger.error(statement, e);
    }

    protected void debug(org.slf4j.Logger logger, String statement, Object... objs) {
        logger.debug(statement, objs);
    }

    protected void debug(org.slf4j.Logger logger, String statement, Throwable e) {
        logger.debug(statement, e);
    }

    protected void warn(org.slf4j.Logger logger, String statement, Object... objs) {
        logger.warn(statement, objs);
    }

    protected void warn(org.slf4j.Logger logger, String statement, Throwable e) {
        logger.warn(statement, e);
    }

    protected boolean isInfoEnabled(org.slf4j.Logger logger, Marker var1) {
        return var1 == null ? logger.isInfoEnabled() : logger.isInfoEnabled(var1);
    }

    protected boolean isErrorEnabled(org.slf4j.Logger logger, Marker var1) {
        return var1 == null ? logger.isErrorEnabled() : logger.isErrorEnabled(var1);
    }

    protected boolean isDebugEnabled(org.slf4j.Logger logger, Marker var1) {
        return var1 == null ? logger.isDebugEnabled() : logger.isDebugEnabled(var1);
    }

    protected boolean isWarnEnabled(org.slf4j.Logger logger, Marker var1) {
        return var1 == null ? logger.isWarnEnabled() : logger.isWarnEnabled(var1);
    }
}
