package org.redwind.autotest.beluga.configuration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.redwind.autotest.beluga.utils.Environment;
import org.redwind.autotest.beluga.utils.PropertyReader;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@ExtendWith(MockitoExtension.class)
class DriverFactoryTest {

    @Mock
    private PropertyReader mockPropertyReader;

    @Mock
    private Environment mockEnvironment;

    @InjectMocks
    private DriverFactory driverFactory;

    @BeforeEach
    void setup() {
        driverFactory = new DriverFactory(mockEnvironment);
    }

    @Test
    void testGetCurrentDriver_WhenNotInitialized_ShouldReturnNull() {
        WebDriver driver = driverFactory.getCurrentDriver();
        assertNull(driver);
    }

    @Test
    void testGetCurrentAppiumDriver_WhenNotInitialized_ShouldReturnNull() {
        AppiumDriver driver = driverFactory.getCurrentAppiumDriver();
        assertNull(driver);
    }

    @Test
    void testInitializeBrowser_ShouldSetDriver() {
        when(mockEnvironment.getPlatform()).thenReturn("Chrome");
        driverFactory = spy(new DriverFactory(mockEnvironment));
        doReturn(mock(ChromeDriver.class)).when(driverFactory).getDesktopDriver("Chrome");

        driverFactory.initializeBrowser();
        assertNotNull(driverFactory.getCurrentDriver());
    }

    @Test
    void testGetDesktopDriver_WithChrome_ShouldReturnChromeDriver() {
        DriverFactory driverFactorySpy = spy(new DriverFactory(mockEnvironment));
    
        ChromeDriver mockChromeDriver = mock(ChromeDriver.class);
    
        doReturn(mockChromeDriver).when(driverFactorySpy).getDesktopDriver("Chrome");
    
        WebDriver driver = driverFactorySpy.getDesktopDriver("Chrome");
    
        assertNotNull(driver);
    }


    @Test
    void testGetDesktopDriver_WithInvalidBrowser_ShouldReturnNull() {
        WebDriver driver = driverFactory.getDesktopDriver("InvalidBrowser");
        assertNull(driver);
    }

    @Test
    void testGetAppiumDriver_WithPlatformAndroid_ShouldReturnAndroidDriver() throws Exception {
        lenient().when(mockPropertyReader.getMobileProperty("ANDROID_PLATFORM_NAME")).thenReturn("Android");
        lenient().when(mockPropertyReader.getMobileProperty("ANDROID_VERSION")).thenReturn("11.0");
        lenient().when(mockPropertyReader.getMobileProperty("ANDROID_APP_PACKAGE")).thenReturn("com.example");
        lenient().when(mockPropertyReader.getMobileProperty("ANDROID_TEST_NAME")).thenReturn("UiAutomator2");

        DriverFactory factory = spy(new DriverFactory(mockEnvironment));

        Field field = DriverFactory.class.getDeclaredField("propertyReader");
        field.setAccessible(true);
        field.set(factory, mockPropertyReader);

        AppiumDriver mockAppiumDriver = mock(AndroidDriver.class);
        doReturn(mockAppiumDriver).when(factory).getAppiumDriver("Android");

        AppiumDriver driver = factory.getAppiumDriver("Android");
        assertNotNull(driver);
    }

    @Test
    void testCleanUpCurrentDriver_ShouldRemoveThreadLocal() {
        driverFactory.cleanUpCurrentDriver();
        assertNull(driverFactory.getCurrentDriver());
    }
}
