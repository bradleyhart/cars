package org.fazz.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class WebApplication {

    public static void main(String[] args) throws ServletException, LifecycleException {
        Tomcat tomcat = new WebApplication().start();
        tomcat.getServer().await();
    }

    private static boolean started = false;
    public static void isRunning() throws ServletException, LifecycleException {
        if(!started){
            new WebApplication().start();
            started = true;
        }
    }

    public Tomcat start() throws ServletException, LifecycleException {
        String webappLocation = new File("src/main/webapp").getAbsolutePath();

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setBaseDir(".");
        tomcat.getHost().setAppBase(webappLocation);
        tomcat.getServer().addLifecycleListener(new AprLifecycleListener());
        tomcat.addWebapp("/", webappLocation);
        tomcat.start();
        return tomcat;
    }
}
