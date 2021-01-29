package sanensi.qohash;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import sanensi.qohash.domain.FileSystem;

public class Api implements Runnable {
    private static final int PORT = 8080;
    private final AbstractBinder domainBinder;

    public Api(FileSystem root) {
        domainBinder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(root);
            }
        };
    }

    @Override
    public void run() {
        ResourceConfig apiConfig = new ResourceConfig()
            .packages("sanensi.qohash.resources")
            .register(domainBinder);

        ServletContainer apiServletContainer = new ServletContainer(apiConfig);
        ServletHolder apiServletHolder = new ServletHolder(apiServletContainer);

        ServletContextHandler apiContext = new ServletContextHandler();
        apiContext.setContextPath("/files");
        apiContext.addServlet(apiServletHolder, "/*");

        WebAppContext webapp = new WebAppContext();
        webapp.setBaseResource(Resource.newClassPathResource("frontend"));
        webapp.setContextPath("/*");

        ErrorPageErrorHandler notFoundHandler = new ErrorPageErrorHandler();
        notFoundHandler.addErrorPage(404, "/");
        webapp.setErrorHandler(notFoundHandler);

        ContextHandlerCollection handlers = new ContextHandlerCollection(apiContext, webapp);
        Server server = new Server(PORT);
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }
}
