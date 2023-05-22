package org.redwind.autotest.beluga.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader
{


    public String getApiProperty(String propertyKey) throws IOException {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/apiConfig.properties";
        String value = getPropertyValue(propertyFileLocation,propertyKey);
        return value;
    }

    public String getGuiProperty(String propertyKey) throws IOException {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/guiConfig.properties";
        String value = getPropertyValue(propertyFileLocation,propertyKey);
        return value;
    }

    public String getGenericProperty(String propertyKey) throws IOException {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/genericConfig.properties";
        String value = getPropertyValue(propertyFileLocation,propertyKey);
        return value;
    }

    public String getPropertyValue(String filePath, String propertyKey) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(filePath));
        String value = properties.getProperty(propertyKey);
        return value;
    }

    public Environment getEnvironment() {
        Environment platform;
        String env = System.getProperty("PLATFORM");
        platform = Environment.setPlatform(env);
        return platform;
    }
}
