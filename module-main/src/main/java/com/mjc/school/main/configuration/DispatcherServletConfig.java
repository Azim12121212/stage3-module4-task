package com.mjc.school.main.configuration;

import com.mjc.school.main.Main;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{Main.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}