package org.redwind.testAuto.beluga.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader
{


public String getApiProperty(String propertyKey) throws IOException {
    String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/apiConfig.properties";
    Properties apiProperties = new Properties();
    apiProperties.load(new FileInputStream(propertyFileLocation));
    String value = apiProperties.getProperty(propertyKey);
    return value;
}

    public String getGuiProperty(String propertyKey) throws IOException {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/guiConfig.properties";
        Properties apiProperties = new Properties();
        apiProperties.load(new FileInputStream(propertyFileLocation));
        String value = apiProperties.getProperty(propertyKey);
        return value;
    }

    public String getGenericProperty(String propertyKey) throws IOException {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/genericConfig.properties";
        Properties apiProperties = new Properties();
        apiProperties.load(new FileInputStream(propertyFileLocation));
        String value = apiProperties.getProperty(propertyKey);
        return value;
    }
}
