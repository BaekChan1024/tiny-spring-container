package org.container.bean;

import lombok.Getter;
import org.container.bean.BeanDefinition;
import org.container.bean.BeanDefinitionReader;
import org.container.bean.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final Map<String, BeanDefinition> registry;
    private final ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<>();
        this.resourceLoader = resourceLoader;
    }
}
