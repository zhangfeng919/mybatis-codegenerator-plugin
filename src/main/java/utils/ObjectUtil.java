package utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zhangfeng
 * @create 2019-10-16-16:46
 **/
public class ObjectUtil {

    public static <T> T parseObject(Object object, Class<T> clazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(object), clazz);
    }
}
