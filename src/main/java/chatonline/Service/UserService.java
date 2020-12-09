package chatonline.Service;


import chatonline.mapper.UserMapper;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
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
    private static final String TOPIC = "chatroom";
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserMapper userMapper;
    /**
     * 记录在线人数
     */
    private static CopyOnWriteArraySet<String> userSet;
    @Autowired
    private JmsMessagingTemplate template;

    private Topic topic = new ActiveMQTopic(TOPIC);

    static {
        userSet = new CopyOnWriteArraySet<>();
    }

    @Autowired
    public void setUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户上线，将用户放入在线列表
     */
    public void addUser(String user) throws Exception {
        template.convertAndSend(topic,"{\"status\":\"information\",\"name\":\""+user+"\",\"action\":\"login\",\"message\":\""+user+"进入了聊天室\"}");
        logger.info("<" + user + ">进入了聊天室。。。");
        userSet.add(user);
    }

    /**
     * 用户下线，将用户移除在线列表
     */
    public void removeUser(String user) throws Exception {
        template.convertAndSend(topic,"{\"status\":\"information\",\"name\":\""+user+"\",\"action\":\"logout\",\"message\":\""+user+"离开了聊天室\"}");
        logger.info("<" + user + ">离开了聊天室。。。");
        userSet.remove(user);
    }

    /**
     * 用户注册，并加入在线列表
     */
    public void insert(String user) throws Exception {
        if (user == "") {
            throw new Exception("用户名不能为空");
        }
        userMapper.insert(user);
        addUser(user);
    }

    /**
     * 用户是否在线，在线返回true，不在线返回false
     */
    public boolean userOnline(String user) {
        if (userSet.contains(user)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用户是否已经注册 ,已经注册返回true，未注册返回false
     */
    public boolean userExist(String user) {
        return userMapper.getUser(user) != null;
    }

    public static CopyOnWriteArraySet<String> getUserSet() {
        return userSet;
    }

    public static int size() {
        return userSet.size();
    }
}
