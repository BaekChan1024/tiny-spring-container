package org.container.bean.factory;

public interface BeanFactory {

    Object getBean(String name) throws Exception;
}