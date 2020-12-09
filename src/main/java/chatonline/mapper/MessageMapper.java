package chatonline.mapper;

import org.apache.ibatis.annotations.Select;

import java.sql.Blob;
import java.util.List;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/19 15:19
 */
public interface MessageMapper {
    @Select(value = "select MSG FROM activemq_msgs;")
    List<Object> getMsg();
}
