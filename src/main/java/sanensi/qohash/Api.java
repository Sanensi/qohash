package sanensi.qohash;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Api implements Runnable {
    private static final int PORT = 8080;

    @Override
    public void run() {
        ResourceConfig resourceConfig = new ResourceConfig().packages("sanensi.qohash.resources");
        ServletContainer container = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        contextHandler.addServlet(servletHolder, "/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }

    public static void main(String[] args) {
        new Api().run();
    }
}
