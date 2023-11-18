package ws.probal.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import ws.probal.httpserver.exception.HttpConfigurationException;
import ws.probal.httpserver.util.JsonUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    public void loadConfigurationFile(String filePath) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException("Configuration file not found", e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while (((i = fileReader.read()) != -1)) {
                sb.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException("Configuration file not valid", e);
        }

        JsonNode configJson;
        try {
            configJson = JsonUtil.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error occurred while parsing Configuration file", e);
        }

        try {
            myCurrentConfiguration = JsonUtil.fromJson(configJson, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error occurred while parsing Configuration file || internal", e);
        }
    }

    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration set.");
        }
        return myCurrentConfiguration;
    }
}
