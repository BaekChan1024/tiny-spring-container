package org.container.bean.factory;

import org.container.bean.BeanDefinition;
import org.container.bean.BeanPostProcessor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory{

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final List<String> beanDefinitionNames = new ArrayList<>();
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();


    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (Objects.isNull(beanDefinition)) {
            throw new IllegalArgumentException("Can't find bean name");
        }
        Object bean = beanDefinition.getBean();
        if (Objects.isNull(bean)) {
            bean = createBean(beanDefinition);
            bean = initializeBean(bean, name);
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    protected Object initializeBean(Object bean, String name) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    protected Object createBean(BeanDefinition beanDefinition) throws InstantiationException, IllegalAccessException {
        Object bean = createBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    protected abstract void applyPropertyValues(Object bean, BeanDefinition beanDefinition);

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws InstantiationException, IllegalAccessException {
        return beanDefinition.getBeanClass().newInstance();
    }

    public List<?> getBeanForType(Class<?> type) throws Exception {
        List<Object> beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public void preInstantiateSingletons() throws Exception {
        for (String beanDefinitionName : beanDefinitionNames) {
            getBean(beanDefinitionName);
        }
    }
}
