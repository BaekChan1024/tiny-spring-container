package org.container;

@FunctionalInterface
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
