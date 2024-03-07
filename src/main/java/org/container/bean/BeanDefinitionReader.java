package org.container.bean;

@FunctionalInterface
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
