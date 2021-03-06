package top.zbeboy.isy.service.util;

/**
 * Created by lenovo on 2016-09-15.
 * sql 查询参数处理工具类
 */
public class SQLQueryUtils {

    /**
     * 组装likeAll全匹配参数
     *
     * @param param 参数
     * @return like '%{param}%'
     */
    public static String likeAllParam(String param) {
        return "%" + param + "%";
    }

    /**
     * 组装likeAll全匹配参数
     *
     * @param param 参数
     * @return like '%{param}%'
     */
    public static String elasticLikeAllParam(String param) {
        return "*" + param + "*";
    }

    /**
     * 右 like
     *
     * @param param 参数
     * @return like '{param}%'
     */
    public static String rightLikeParam(String param) {
        return param + "%";
    }
}
