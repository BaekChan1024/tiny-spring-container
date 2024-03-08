package org.container.context;

import org.container.HelloWorldService;
import org.junit.jupiter.api.Test;

class ApplicationContextTest {

    @Test
    public void testBeforeBeanProcessor() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}