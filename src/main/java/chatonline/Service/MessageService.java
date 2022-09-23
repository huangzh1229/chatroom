package chatonline.Service;


import chatonline.entity.Message;
import chatonline.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/19 19:05
 */
@Service
public class MessageService {
    private MessageMapper messageMapper;
    private LocalDateTime from;
    private LocalDateTime end;
    /**
     * 储存主聊天室聊天消息的集合
     */
    private static ArrayList<Message> messageCache;

    public MessageService() {
        from = null;
        end = null;
    }

    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }
    static {
        messageCache = new ArrayList<>();
    }

    /**
     * 重新将所有的聊天信息加载到内存中
     */
    public boolean refresh() {
        if(!timeValid()) {
            return false;
        }
        try {
            messageCache.clear();
            List<Message> messageList = messageMapper.getMessagebetween(this.from, this.end);
            sort();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 储存聊天室的内容
     */
    public void receive(Message message) throws Exception {
            messageCache.add(message);
            messageMapper.addMessage(message);
    }
    /**
     * 将缓存中消息列表按照时间进行排序
     *  */
    public void sort(){
       messageCache.sort((e1,e2)->e1.getSendTime().isBefore(e2.getSendTime())?1:0);
    }
    /**
     * 缓存中保存着某个时间段之间的消息，该方法用于设置时间段的起始时间和结束时间，from参数为null则表示时间段设置为end之前的所有时间，end参数为null同理。
     **/
    public boolean setMessageSearchTime(LocalDateTime from, LocalDateTime end) {
        if (from == null && end == null) {
            return false;
        }
        this.from = null;
        this.end = null;
        this.from = from;
        this.end = end;
        return true;
    }

    /**
     * 聊天记录的起始和终止时间是否有效,true为时间有效，false为时间无效
     */
    public boolean timeValid() {
        return this.from != null || this.end != null;
    }

    public static ArrayList<Message> getMessageList() {
        return messageCache;
    }
}
