package jettyproxy;

import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.ProxyConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ProxyServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(3128);
        HttpConfiguration configuration = new HttpConfiguration();
        ServerConnector connector = new ServerConnector(server, new ProxyConnectionFactory(),
                new HTTP2CServerConnectionFactory(configuration));
        server.addConnector(connector);

        HandlerCollection handlers = new HandlerCollection();
        server.setHandler(handlers);
        ServletContextHandler context = new ServletContextHandler(handlers, "/");
        ServletHolder proxyServlet = new ServletHolder("proxyHolder", org.eclipse.jetty.proxy.ProxyServlet.class);
        context.addServlet(proxyServlet, "/*");
        ConnectHandler proxy = new ConnectHandler();
        handlers.addHandler(proxy);

        server.start();
        server.join();
    }

}