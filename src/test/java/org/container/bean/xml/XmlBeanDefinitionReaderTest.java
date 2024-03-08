package org.container.bean.xml;

import org.container.bean.BeanDefinition;
import org.container.bean.io.ResourceLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class XmlBeanDefinitionReaderTest {

    @Test
    public void test() throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
        Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : registry.entrySet()) {
            System.out.println(beanDefinitionEntry.getKey());
            System.out.println(beanDefinitionEntry.getValue().getBeanClassName());
        }
        Assertions.assertTrue(registry.size() > 0);

    }
}