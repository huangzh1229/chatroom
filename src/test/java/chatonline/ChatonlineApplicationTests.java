package chatonline;

import chatonline.mapper.MessageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.EncodingUtil;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ChatonlineApplicationTests {

    @Autowired
    private MessageMapper mapper;

    @Test
    void contextLoads() throws Exception {
        List<Object> msg = mapper.getMsg();

        byte []bytes=(byte[])msg.get(0);
        for (int i = 0; i < bytes.length; i++) {
            System.out.print((char)bytes[i]);
        }
    }


}
