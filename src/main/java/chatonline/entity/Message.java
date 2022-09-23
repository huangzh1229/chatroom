package chatonline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huangzhuo
 * @date 2022/8/29 19:38
 **/
@Data
public class Message {
    private int id;
    private String sender;
    private String receiver;
    @JsonFormat(pattern="yyyy-mm-dd HH:SS",timezone = "GMT+8")
    private LocalDateTime sendTime;
    private String message;
}
