package chatonline;

import chatonline.mapper.MessageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
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
    @Test
    void test1()throws Exception{
        Enumeration<NetworkInterface> allNetworkInterfaces = NetworkInterface.getNetworkInterfaces();

        while (allNetworkInterfaces.hasMoreElements()){
            NetworkInterface nif = allNetworkInterfaces.nextElement();
                    if (nif.getName().startsWith("wlan")){
                        Enumeration<InetAddress> addresses = nif.getInetAddresses();
                        while (addresses.hasMoreElements())
                        {
                            InetAddress address = addresses.nextElement();
                            if (address.getAddress().length==4){
                                System.out.println(address.getHostAddress());
                                //return address.getHostAddress();
                            }
                        }
                    }
        }
        }


}
