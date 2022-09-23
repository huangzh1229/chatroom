package chatonline.component;

import chatonline.Service.MessageService;
import chatonline.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/19 10:52
 */
public class ContextListener implements ServletContextListener {
    Logger logger= LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

            logger.info("-----ServletContext容器创建了-----");
            sce.getServletContext().setAttribute("userSet", UserService.getUserSet());
            sce.getServletContext().setAttribute("messageList", MessageService.getMessageList());
            logger.info("-----用户集合放入了ServletContext-----");
            logger.info("-----消息集合放入了ServletContext-----");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("-----ServletContext容器销毁了-----");
    }
}
