package chatonline.Service;

import com.google.gson.Gson;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import utils.EncodingUtil;

import javax.jms.BytesMessage;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/19 19:05
 */
@Service
public class MessageService {

    /**
     * 储存主聊天室聊天消息的集合
     */
    private static ArrayList<String> messageList;

    static {
        messageList = new ArrayList<>();
    }

    /**
     * 储存聊天室的内容
     */
    @JmsListener(destination = "${MainTopic}")
    public void receive(Message message) throws Exception {
        if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) message;
            byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
            char[] chars = new char[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            for (int i = 0; i < bytes.length; i++) {
                chars[i] = (char) bytes[i];
            }
            String jsonData = String.valueOf(chars);
            Gson gson = new Gson();
            Map<String, String> maps = new HashMap<>();
            maps = gson.fromJson(jsonData, maps.getClass());
            if (maps.get("status").equals("message")) {
                String text = EncodingUtil.decodeUnicode(maps.get("message"));
                messageList.add("[" + maps.get("date") + "]" + maps.get("name") + ":" + text);
            }
        }

    }

    public static ArrayList<String> getMessageList() {
        return messageList;
    }
}
