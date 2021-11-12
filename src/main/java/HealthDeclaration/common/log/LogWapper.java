package HealthDeclaration.common.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogWapper implements Ilog {
    private Logger log;
    private String prefix;

    public LogWapper(Class<?> t, String prefix) {
        this.log = LogManager.getLogger(t);
        this.prefix = prefix;
    }

    public void info(String msg)
    {
        log.info(prefix + "| " + msg);
    }
    public void error(String msg)
    {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        log.error(prefix + "| " + msg  + "|" +methodName+ "():" + lineNumber);
    }
    public void warning(String msg)
    {
        log.warn(prefix + "| " + msg);
    }
}
