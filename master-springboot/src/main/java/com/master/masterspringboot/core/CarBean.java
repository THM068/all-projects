package com.master.masterspringboot.core;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletConfigAware;

import javax.servlet.ServletConfig;

import static java.lang.String.format;

@Component(value = "carBeanVroom")
public class CarBean implements BeanNameAware, ResourceLoaderAware, ServletConfigAware {
    private String beanName;
    public void drive() {
        System.out.println("Drive me ....");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println(format("The name of the bean is %s", name));
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {

    }
}
