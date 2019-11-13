package utils;

import com.alibaba.fastjson.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zhangfeng
 * @create 2019-10-16-13:45
 **/
public class LogUtil {

    private static Logger log = Logger.getLogger(LogUtil.class.getName());


    public static void info(Object msg) {
        log(msg, Level.INFO);
    }

    public static void trace(Object msg) {
        log(msg, Level.INFO);
    }

    public static void debug(Object msg) {
        log(msg, Level.INFO);
    }

    public static void warn(Object msg) {
        log(msg, Level.WARNING);
    }

    public static void error(Object msg) {
        log(msg, Level.INFO);
    }

    private static void log(Object msg, Level level) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];

        String printStr = String.format("类%s 方法%s 行%s 打印%s",
            stackTraceElement.getClassName(),
            stackTraceElement.getMethodName(),
            stackTraceElement.getLineNumber(),
            JSONObject.toJSONString(msg));
        log.info(printStr);
    }

}
