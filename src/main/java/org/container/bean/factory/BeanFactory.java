package org.container.bean.factory;

public interface BeanFactory {

    Object getBean(String name) throws Exception;

    <T> T getBean(String name, Class<T> type) throws Exception;
}
