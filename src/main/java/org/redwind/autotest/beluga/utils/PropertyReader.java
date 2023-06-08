package org.redwind.autotest.beluga.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader
{
    public static final Logger logger = LogManager.getFormatterLogger();

    public String getApiProperty(String propertyKey) {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/apiConfig.properties";
        String value = getPropertyValue(propertyFileLocation,propertyKey);
        return value;
    }

    public String getGuiProperty(String propertyKey) {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/guiConfig.properties";
        String value = getPropertyValue(propertyFileLocation,propertyKey);
        return value;
    }

    public String getGenericProperty(String propertyKey) {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/genericConfig.properties";
        String value = getPropertyValue(propertyFileLocation,propertyKey);
        return value;
    }
    public String getMobileProperty(String propertyKey) {
        String propertyFileLocation = System.getProperty("user.dir") + "/src/main/resources/mobileConfig.properties";
        String value = getPropertyValue(propertyFileLocation,propertyKey);
        return value;
    }

    public String getPropertyValue(String filePath, String propertyKey) {
        Properties properties = new Properties();
        try(FileInputStream inputStream =new FileInputStream(filePath)){
            properties.load(inputStream);
        }catch (IOException e) {
            logger.info("failed to load property file");
        }
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
