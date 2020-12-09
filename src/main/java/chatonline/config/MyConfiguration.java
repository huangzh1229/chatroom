package chatonline.config;

import chatonline.component.ContextListener;
import chatonline.interceptor.Interceptor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.jms.Topic;
import java.util.Arrays;

/**
 * @author huangzhuo
 * @version 1.8
 * @date 2020/11/16 16:54
 */
@Configuration
public class MyConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main.html").setViewName("main");
        registry.addViewController("/fail.html").setViewName("fail");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/asserts/**", "/webjars/**","/error/**"));
    }

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        return  new ServletListenerRegistrationBean(new ContextListener());
    }
}
