package HealthDeclaration.common.log;

public class LogFactory {
    public static LogWapper getLog(Class<?> t, String prefix) {
        return new LogWapper(t, prefix);
    }
}