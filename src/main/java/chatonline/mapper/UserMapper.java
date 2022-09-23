package chatonline.mapper;

import chatonline.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/18 19:13
 */

public interface UserMapper {

    @Select(value = "select id,username,password from user where username=#{username}")
    Users getUserByUsername(String username);

    @Insert(value = "insert into user(username,password) value(#{username},#{password})")
    boolean insert(Users user);



}
