package chatonline.controller;


import chatonline.Service.UserService;
import chatonline.factory.CookieFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.NetworkInterface;
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
    public String toLoginPage(){
        return "redirect:/login.html";
    }
    /**
     * 登录业务
     */
    @PostMapping(value = "/login")
    public String login(@RequestParam String user, HttpServletResponse response, HttpSession session) throws Exception {
        String localUser = user.trim();
        /** 是否已经注册过 */
        if (userService.userExist(localUser)) {
            /** 是否在线 */
            if (!userService.userOnline(localUser)) {
                userService.addUser(localUser);
            } else {
                throw new Exception("用户已上线");
            }
        } else {
            userService.insert(localUser);
        }
        logger.info("用户上线");
        Cookie cookie = CookieFactory.getCookie("user", localUser);
        response.addCookie(cookie);
        /** 本机ip地址 */
       session.setAttribute("ip",getLocalIp());
        return "redirect:/main.html";

    }

    /**
     * 登出
     */
    @GetMapping(value = "/loginOut")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "redirect:/login.html";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) {
                userService.removeUser(cookie.getValue());
            }
            CookieFactory.destroyCookie(cookie);
            response.addCookie(cookie);
        }
        return "redirect:/login.html";
    }

    /**
     * 处理单人聊天的请求
     */
    @GetMapping(value = "/chat")
    public String toChatPage(@RequestParam String user1, @RequestParam String user2, Model model) throws Exception{
        model.addAttribute("user1", user1);
        model.addAttribute("user2", user2);
        if (user1.hashCode() > user2.hashCode()) {
            model.addAttribute("queueName", user2 + "-" + user1);
        } else {
            model.addAttribute("queueName", user1 + "-" + user2);
        }
        return "chat";
    }

  private static String getLocalIp() throws Exception {
        Enumeration<NetworkInterface> allNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetworkInterfaces.hasMoreElements()) {
            NetworkInterface nif = allNetworkInterfaces.nextElement();
            if (nif.getName().startsWith("wlan")) {
                Enumeration<InetAddress> addresses = nif.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.getAddress().length == 4) {
                        return address.getHostAddress();
                    }
                }
            }
        }
        return null;
    }

}
