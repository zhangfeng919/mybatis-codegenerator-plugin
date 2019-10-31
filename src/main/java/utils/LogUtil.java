package utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

/**
 * @author zhangfeng
 * @create 2019-10-16-13:45
 **/
public class LogUtil {

    private static Logger log = LoggerFactory.getLogger(LogUtil.class);


    public static void info(Object msg) {
        log(msg, Level.INFO);
    }

    public static void trace(Object msg) {
        log(msg, Level.TRACE);
    }

    public static void debug(Object msg) {
        log(msg, Level.DEBUG);
    }

    public static void warn(Object msg) {
        log(msg, Level.WARN);
    }

    public static void error(Object msg) {
        log(msg, Level.ERROR);
    }

    private static void log(Object msg, Level level) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];

        String printStr = String.format("类%s 方法%s 行%s 打印%s",
            stackTraceElement.getClassName(),
            stackTraceElement.getMethodName(),
            stackTraceElement.getLineNumber(),
            JSONObject.toJSONString(msg));
        switch (level) {
            case TRACE:
                log.trace(printStr);
                break;
            case DEBUG:
                log.debug(printStr);
                break;
            case INFO:
                log.info(printStr);
                break;
            case WARN:
                log.warn(printStr);
                break;
            case ERROR:
                log.error(printStr);
                break;
            default:
                log.info(printStr);
        }
    }

}
