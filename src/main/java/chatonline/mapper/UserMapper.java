package chatonline.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/18 19:13
 */

public interface UserMapper {

    @Select(value = "select user from user where user=#{user}")
    String getUser(String user);

    @Insert(value = "insert into user(user) value(#{user})")
    boolean insert(String user);


}
