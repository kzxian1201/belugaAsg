package org.redwind.autotest.beluga.utils;

import com.aventstack.extentreports.ExtentTest;
import org.mockito.Mockito;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestUtils {
    public static void setField(Object target, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    public static void disableExtentReports() {
        System.setProperty("extent.reporter.spark.start", "false");
        System.setProperty("extent.reporter.cucumber.start", "false");
        System.setProperty("extent.reporter.html.start", "false");
    }

    public static ExtentTest createMockExtentTest() {
        return Mockito.mock(ExtentTest.class);
    }
}