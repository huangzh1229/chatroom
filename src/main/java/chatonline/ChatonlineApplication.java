package chatonline;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/** 多人聊天采用websocket技术，将信息放入主题中，单人聊天采用activeMQ的PointToPoint模式  */
@SpringBootApplication

@MapperScan(value = "chatonline.mapper")
public class ChatonlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatonlineApplication.class, args);
    }

}
