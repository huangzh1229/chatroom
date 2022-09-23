package chatonline.Service;


import chatonline.entity.Users;
import chatonline.mapper.UserMapper;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utils.EncodingUtil;

import javax.jms.Topic;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/10/28 11:06
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserMapper userMapper;
    /**
     * 记录在线人数
     */
    private static CopyOnWriteArraySet<String> userSet;


    static {
        userSet = new CopyOnWriteArraySet<>();
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户上线，将用户放入在线列表
     */
    public void addUser(String  username) throws Exception {
            userSet.add(username);
    }

    /**
     * 用户下线，将用户移除在线列表
     */
    public void removeUser(String username) throws Exception {
               userSet.remove(username);
    }

    /**
     * 用户注册，并加入在线列表
     */
    public void insert(Users user) throws Exception {
        if (!StringUtils.hasText(user.getUsername())) {
            throw new Exception("用户名不能为空");
        }
        userMapper.insert(user);
        addUser(user.getUsername());
    }

    /**
     * 用户是否在线，在线返回true，不在线返回false
     */
    public boolean userOnline(String username) {
        return userSet.contains(username);
    }

    /**
     * 用户是否已经注册 ,已经注册返回true，未注册返回false
     */
    public boolean userRegistryOrNot(Users user) {
        return userMapper.getUserByUsername(user.getUsername()) != null;
    }

    public static CopyOnWriteArraySet<String> getUserSet() {
        return userSet;
    }

    public static int size() {
        return userSet.size();
    }
}
