package ps.demo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(MyProps.class)
public class MyPropsConfig {

    @Bean
    public MyTestBean myTestBean(MyProps myProps, Environment environment) {
        String resolvedConnUrl = environment.resolvePlaceholders(myProps.connUrl());
        MyTestBean myTestBean = new MyTestBean();
        myTestBean.setResolvedConnUrl(resolvedConnUrl);
        return myTestBean;
    }

}
