package com.inspiration.future.feign.logger;

import com.inspiration.future.feign.annotaition.LogTarget;
import com.inspiration.future.feign.other.Constant;
import org.slf4j.LoggerFactory;

/**
 * future feign rpc log main record call produce
 *
 * @author zpf
 * @since 1.1.0
 */
@LogTarget(value = Constant.RPC_LOG)
public class RpcRecordLog extends AbstractLogger implements Logger {

    private static final org.slf4j.Logger SYSTEM_RECORD_LOG = LoggerFactory.getLogger(RpcRecordLog.class);

    public void inPutInfoLog(String statement, Object... objs) {
        info(SYSTEM_RECORD_LOG, statement, objs);
    }

    @Override
    public void inPutInfoLog(String statement, Throwable e) {
        info(SYSTEM_RECORD_LOG, statement, e);
    }

    @Override
    public void inPutErrorLog(String statement, Object... objs) {
        error(SYSTEM_RECORD_LOG, statement, objs);
    }

    @Override
    public void inPutErrorLog(String statement, Throwable e) {
        error(SYSTEM_RECORD_LOG, statement, e);

    }

    @Override
    public void inPutDebugLog(String statement, Object... objs) {
        debug(SYSTEM_RECORD_LOG, statement, objs);
    }

    @Override
    public void inPutDebugLog(String statement, Throwable e) {
        debug(SYSTEM_RECORD_LOG, statement, e);
    }

    @Override
    public void inPutWarLog(String statement, Object... objs) {
        warn(SYSTEM_RECORD_LOG, statement, objs);
    }

    @Override
    public void inPutWarLog(String statement, Throwable e) {
        warn(SYSTEM_RECORD_LOG, statement, e);
    }
}
