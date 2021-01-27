package sanensi.qohash.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class FileSystemResource {
    @GET()
    public String get() {
        return "Hello World!";
    }
}
