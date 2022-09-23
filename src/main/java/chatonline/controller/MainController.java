package chatonline.controller;


import chatonline.Service.UserService;
import chatonline.entity.Users;
import chatonline.factory.CookieFactory;
import http.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/10/28 11:06
 */
@Controller
public class MainController {
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String toLoginPage() {
        return "redirect:/login.html";
    }

    /**
     * 登录业务
     */
    @PostMapping(value = "/login")
    public String login(@RequestBody Users user, HttpServletResponse response, HttpSession session) throws Exception {
        /** 是否已经注册过 */
        if (userService.userRegistryOrNot(user)) {
            /** 是否在线 */
            if (!userService.userOnline(user.getUsername())) {
                userService.addUser(user.getUsername());
            } else {
                throw new Exception("用户已上线");
            }
        } else {
            userService.insert(user);
        }
        logger.info("用户上线");
        Cookie cookie = CookieFactory.getCookie("user", user.getUsername());
        response.addCookie(cookie);
        return "redirect:/main.html";

    }

    /**
     * 登出
     */
    @GetMapping(value = "/logout")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie userCookie = Arrays.stream(request.getCookies()).filter(e -> "user".equals(e.getName())).findFirst().orElse(null);
        if (userCookie == null) {
            return "redirect:/login.html";
        }
        if ("user".equals(userCookie.getName())) {
            userService.removeUser(userCookie.getValue());
        }
        CookieFactory.destroyCookie(userCookie);
        response.addCookie(userCookie);
        return "redirect:/login.html";
    }
    public Result receive(){
        return Result.success();
    }
    /**
     * 处理单人聊天的请求
     */
    @GetMapping(value = "/chat")
    public String toChatPage(@RequestParam String user1, @RequestParam String user2, Model model) throws Exception {
        return "chat";
    }



}
