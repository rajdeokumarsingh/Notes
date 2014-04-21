package com.springinaction.springidol.config;

import com.springinaction.springidol.PerformanceException;
import com.springinaction.springidol.Performer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
//@Import({SpringIdolConfig.class})
@ImportResource("classpath:com/springinaction/springidol/spring-idol.xml")
public class DataSourceConfig {

     public static void main(String[] args) throws PerformanceException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        Performer duke = (Performer) context.getBean("duke");
        duke.perform();
    }
}

