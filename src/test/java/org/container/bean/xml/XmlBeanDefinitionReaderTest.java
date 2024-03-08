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
        Assertions.assertTrue(registry.size() > 0);

    }
}