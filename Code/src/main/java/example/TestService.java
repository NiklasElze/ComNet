package example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/message")
public class TestService
{
    @GET
    public String getMsg()
    {
        return "Hello World !! - Jersey 2";
    }
}
