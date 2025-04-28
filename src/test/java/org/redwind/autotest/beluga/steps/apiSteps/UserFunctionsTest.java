package org.redwind.autotest.beluga.steps.apiSteps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.*;

class UserFunctionsTest {

    private UserFunctions userFunctions;

    @BeforeEach
    void setUp() {
        userFunctions = new UserFunctions();
    }

    @Test
    void testSetAndGetEndpoint() throws MalformedURLException {
        URL testUrl = new URL("http://example.com/api");
        userFunctions.setEndpoint(testUrl);
        assertEquals(userFunctions.getEndpoint(), testUrl, "Endpoint should match the one set");
    }

    @Test
    void testSetAndGetCreatedUser() {
        String testUser = "testUser123";
        userFunctions.setCreatedUser(testUser);
        assertEquals(userFunctions.getCreatedUser(), testUser, "Created user should match the one set");
    }

    @Test
    void testUpdateCreateUserJson() {
        try {
            userFunctions.updateCreateUserJson();
            String createdUser = userFunctions.getCreatedUser();
            assertNotNull(createdUser, "Created user should not be null after update");
            assertFalse(createdUser.isEmpty(), "Created user should not be empty");
        } catch (IOException e) {
            fail("IOException thrown during updateCreateUserJson: " + e.getMessage());
        }
    }
}
