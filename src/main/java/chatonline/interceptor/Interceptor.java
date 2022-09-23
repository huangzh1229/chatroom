package chatonline.interceptor;

import chatonline.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/16 19:49
 */
@Component
public class Interceptor implements HandlerInterceptor {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().contains("login")) {
            Cookie cookie = Arrays.stream(request.getCookies()).filter(e -> "user".equals(e.getName())).findFirst().orElse(null);
            //有授权登录的cookie值
            if (cookie != null) {
                //已经上线
                if (userService.userOnline(cookie.getName())) {
                    return true;
                }
            }
        }
        response.sendRedirect("/login.html");
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
