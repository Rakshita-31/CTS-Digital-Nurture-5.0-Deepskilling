package com.cts.banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Exercise 1: Mocking and Stubbing
 *
 * Scenario: testing a service (MyService) that depends on an external API
 * (ExternalApi), without actually calling that real external API during
 * the test - we don't want our tests to depend on a third-party service
 * being online, fast, or returning consistent data.
 *
 * Mockito lets us:
 *   1. Create a mock object standing in for ExternalApi (mock(...))
 *   2. Stub its methods to return whatever predefined value we choose
 *      (when(...).thenReturn(...))
 *   3. Use that mock inside the real MyService and verify the result comes
 *      through correctly
 *
 * This means the test is fast, deterministic, and isolated entirely to
 * MyService's own logic - it can't fail because of a network issue or a
 * change in the real external API's behavior.
 */
public class Mockito_Exercise1_MockingAndStubbingTest {

    @Test
    public void testExternalApi() {
        // Step 1: create a mock object for the external API
        ExternalApi mockApi = mock(ExternalApi.class);

        // Step 2: stub the method to return a predefined value instead of
        // calling any real implementation
        when(mockApi.getData()).thenReturn("Mock Data");

        // Step 3: write a test case that uses the mock object
        MyService service = new MyService(mockApi);
        String result = service.fetchData();

        assertEquals("Mock Data", result, "MyService should return exactly what the mocked API provides");
    }
}
