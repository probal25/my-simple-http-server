package ws.probal.httpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.probal.httpserver.config.Configuration;
import ws.probal.httpserver.config.ConfigurationManager;
import ws.probal.httpserver.core.ServerListenerThread;

import java.io.IOException;

public class HttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {
        LOGGER.info("Server starting . . .");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/httpconfig.json");
        Configuration currentConfiguration = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using port: {}", currentConfiguration.getPort());
        LOGGER.info("Using web root: {}", currentConfiguration.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(currentConfiguration.getPort(),
                    currentConfiguration.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }
}