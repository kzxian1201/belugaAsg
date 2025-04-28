package org.redwind.autotest.beluga.steps.apiSteps;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.Markup;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redwind.autotest.beluga.utils.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TakeAwayBackendStepsTest {

    @InjectMocks
    private TakeAwayBackendSteps steps;

    @Mock
    private APIHelper apiHelper;
    @Mock
    private PropertyReader propertyReader;
    @Mock
    private GenericFunctions genericFunctions;
    @Mock
    private JsonHelper jsonHelper;
    @Mock
    private Response response;
    @Mock
    private ExtentTest mockExtentTest;

    private Path tempDir;
    private MockedStatic<ExtentCucumberAdapter> mockedExtent;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        steps = new TakeAwayBackendSteps();

        // Setup temp directory
        tempDir = Files.createTempDirectory("beluga-test");

        // Inject common dependencies
        TestUtils.setField(steps, "apiHelper", apiHelper);
        TestUtils.setField(steps, "propertyReader", propertyReader);
        TestUtils.setField(steps, "genericFunctions", genericFunctions);
        TestUtils.setField(steps, "jsonHelper", jsonHelper);
        TestUtils.setField(steps, "response", response);
    }

    private void setupExtentReportsMock() {
        when(mockExtentTest.log(any(Status.class), any(Markup.class))).thenReturn(mockExtentTest);
        mockedExtent = mockStatic(ExtentCucumberAdapter.class);
        mockedExtent.when(ExtentCucumberAdapter::getCurrentStep).thenReturn(mockExtentTest);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Cleanup static mock
        if (mockedExtent != null) {
            mockedExtent.close();
        }

        // Cleanup temp directory
        if (tempDir != null) {
            Files.walk(tempDir)
                    .sorted(java.util.Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    void testValidateNewBookingIsPresent() throws Exception {
        setupExtentReportsMock(); // Only setup for tests that need it

        when(response.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(response.asString()).thenReturn("[{\"bookingid\":123},{\"bookingid\":456}]");

        TestUtils.setField(steps, "generatedID", 456);

        assertDoesNotThrow(() -> steps.validateNewBookingIsPresent());
        verify(mockExtentTest, atLeastOnce()).log(any(Status.class), any(Markup.class));
    }

    @Test
    void testUpdateBookingDetailsJSON() throws Exception {
        // No ExtentReports setup needed here
        Path testFile = tempDir.resolve("test.json");
        Files.write(testFile, "{\"firstname\":\"\",\"lastname\":\"\"}".getBytes());

        when(genericFunctions.getRandomNumber(299, 5600)).thenReturn("1234");

        steps.updateBookingDetailsJSON(testFile.toString());

        String content = Files.readString(testFile);
        assertAll(
                () -> assertTrue(content.contains("\"firstname\":\"Deepak1234\"")),
                () -> assertTrue(content.contains("\"lastname\":\"Mahalingam1234\""))
        );
    }
}