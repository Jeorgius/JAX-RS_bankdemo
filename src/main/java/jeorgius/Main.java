package jeorgius;

import jeorgius.Cors.CorsProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;

/**
 * Main class.
 *
 */
public class Main {

    public static final String BASE_URI = "http://localhost:8080/bankaccount/";

    public static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages("jeorgius")
                                  .register(new CorsProvider())
                                  .register(new JacksonJsonProvider());
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));


        System.in.read();
        server.stop();
    }
}

