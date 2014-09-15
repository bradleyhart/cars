package org.fazz;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class CarsInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(createContextLoaderListener());
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", createAppServlet());
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }

    public static ContextLoaderListener createContextLoaderListener() {
        return new ContextLoaderListener(getContext());
    }

    public static DispatcherServlet createAppServlet() {
        return new DispatcherServlet(getContext());
    }

    private static AnnotationConfigWebApplicationContext contextSingleton;

    private static AnnotationConfigWebApplicationContext getContext() {
        if (contextSingleton == null) {
            contextSingleton = new AnnotationConfigWebApplicationContext();
            contextSingleton.setConfigLocation("org.fazz.spring");
        }
        return contextSingleton;
    }

}

