package org.container.bean.xml;

import org.container.bean.AbstractBeanDefinitionReader;
import org.container.bean.BeanDefinition;
import org.container.bean.BeanReference;
import org.container.bean.PropertyValue;
import org.container.bean.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
        applyBeanDefinitions(inputStream);
    }

    protected void applyBeanDefinitions(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(inputStream);

        registerBeanDefinitions(document);
        inputStream.close();
    }

    public void registerBeanDefinitions(Document document) {
        Element root = document.getDocumentElement();
        parseBeanDefinitions(root);
    }

    protected void parseBeanDefinitions(Element root) {
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                processBeanDefinition(element);
            }
        }
    }

    protected void processBeanDefinition(Element element) {
        String name = element.getAttribute("id");
        String className = element.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        processProperty(element, beanDefinition);
        beanDefinition.setBeanClassName(className);
        getRegistry().put(name, beanDefinition);
    }

    private void processProperty(Element element, BeanDefinition beanDefinition) {
        NodeList propertyNode = element.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyElement = (Element) node;
                String name = propertyElement.getAttribute("name");
                String value = propertyElement.getAttribute("value");
                if (Objects.nonNull(value) && !value.isEmpty()) {
                    beanDefinition.getPropertyValues().add(new PropertyValue(name, value));
                } else {
                    String ref = propertyElement.getAttribute("ref");
                    if (Objects.isNull(ref) || ref.isEmpty()) {
                        throw new IllegalArgumentException("property is invalid");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().add(new PropertyValue(name, beanReference));
                }
            }
        }
    }
}
