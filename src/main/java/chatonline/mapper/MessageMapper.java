package chatonline.mapper;

import chatonline.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/19 15:19
 */
public interface MessageMapper {
    @Select("select * from message;")
    List<Message> getAllMessage();
    //查询某个时间段之后的所有聊天信息

    @Select("select * from message where send_time > #{time};")
    List<Message> getMessageAfter(LocalDateTime time);
    //查询某个时间段之前的所有聊天信息
    @Select("select * from message where send_time < #{time};")
    List<Message> getMessagebefore(LocalDateTime time);

    //查询某个时间段之间的所有聊天信息
    @Select("select * from message where send_time BETWEEN  #{from} AND #{end};")
    List<Message> getMessagebetween(LocalDateTime from,LocalDateTime end);
    @Insert("insert into message(sender, receiver, send_time, message) values (#{sender},#{receiver},#{sendTime},#{message})")
    void addMessage(Message message);
}
