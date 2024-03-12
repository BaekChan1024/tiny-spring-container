package org.container.aop;

import org.container.HelloWorldService;
import org.container.bean.io.ResourceLoader;
import org.container.context.ApplicationContext;
import org.container.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

class JdkDynamicAopProxyTest {

    @Test
    public void testInterceptor() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldService.class);
        advisedSupport.setTargetSource(targetSource);

    }
}