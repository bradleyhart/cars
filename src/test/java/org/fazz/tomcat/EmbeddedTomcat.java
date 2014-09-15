package org.fazz.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class EmbeddedTomcat {

    public static void main(String[] args) throws ServletException, LifecycleException {
        new EmbeddedTomcat().start();
    }


    public void start() throws ServletException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.setBaseDir(".");
        String webapp = new File("src/main/webapp").getAbsolutePath();
        tomcat.getHost().setAppBase(webapp);

        String contextPath = "/";

        StandardServer server = (StandardServer)tomcat.getServer();
        AprLifecycleListener listener = new AprLifecycleListener();
        server.addLifecycleListener(listener);

        tomcat.addWebapp(contextPath, webapp);
        tomcat.start();
    }
}
