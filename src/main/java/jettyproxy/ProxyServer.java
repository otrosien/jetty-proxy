package jettyproxy;

import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ProxyServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(3128);
		ConnectHandler handler = new org.eclipse.jetty.proxy.ConnectHandler();
		handler.setServer(server);
        HandlerCollection handlers = new HandlerCollection();
        server.setHandler(handlers);
        ServletContextHandler context = new ServletContextHandler(handlers, "/");
        ServletHolder proxyServlet = new ServletHolder("proxyHolder", org.eclipse.jetty.proxy.ProxyServlet.class);
//        proxyServlet.setInitParameter("whiteList", "google.com, www.eclipse.org, localhost");
//        proxyServlet.setInitParameter("blackList", "google.com/calendar/*, www.eclipse.org/committers/");
        context.addServlet(proxyServlet, "/*");		
        ConnectHandler proxy = new ConnectHandler();
        handlers.addHandler(proxy);

		server.start();
		server.join();
	}

}