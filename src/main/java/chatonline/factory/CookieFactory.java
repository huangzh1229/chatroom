package chatonline.factory;

import javax.servlet.http.Cookie;
import java.net.URLEncoder;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/10/29 11:40
 */
public class CookieFactory {
    /**
     * 最长生存时间（正数） 秒为单位  0为删除  负数为保存在浏览器内存中，关闭浏览器即删除
     * 开发时设置为0
     * 上线时设置为3600
     */

    private static final int COOKIE_MAX_AGE = -1;
    /**
     * cookie的访问路径，就是当某个请求路径的包含该路径时，才会带上该cookie
     */
    private static final String COOKIE_PATH = "/";

    /**
     * 根据一对键值对得到一个cookie
     */
    public static Cookie getCookie(String name, String value)throws Exception {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setPath(COOKIE_PATH);
        return cookie;
    }
    public static Cookie destroyCookie(Cookie cookie){
            cookie.setMaxAge(0);
            return cookie;
    }
}
