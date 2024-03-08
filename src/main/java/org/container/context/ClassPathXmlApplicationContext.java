package org.container.context;

import org.container.bean.BeanDefinition;
import org.container.bean.factory.AbstractBeanFactory;
import org.container.bean.factory.AutowireCapableBeanFactory;
import org.container.bean.io.ResourceLoader;
import org.container.bean.xml.XmlBeanDefinitionReader;

import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private final String configLocation;

    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }
}
