package example;

import common.EntityManagerHandler;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest")
public class RestApp extends ResourceConfig {
    public RestApp() {
        EntityManagerHandler.initialize();
        packages("api");
    }
}
