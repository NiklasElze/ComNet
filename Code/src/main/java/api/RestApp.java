package api;

import common.MyEntityManagerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest")
public class RestApp extends ResourceConfig {
    public RestApp() {
        MyEntityManagerFactory.initialize();
        packages("api");
    }
}