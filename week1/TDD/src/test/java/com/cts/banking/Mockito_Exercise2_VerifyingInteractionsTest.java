package com.cts.banking;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

/*
 * Exercise 2: Verifying Interactions
 *
 * Scenario: confirming that a method actually got called on the mock -
 * not just checking the return value, but checking the *behavior*. This
 * matters when a method's side effect (the fact that it was called at all,
 * or called a certain number of times) is what we actually care about,
 * separate from whatever it returns.
 *
 * verify(mock).someMethod() checks that someMethod() was called exactly
 * once on that mock. If it was never called, or called more than once,
 * the test fails.
 */
public class Mockito_Exercise2_VerifyingInteractionsTest {

    @Test
    public void testVerifyInteraction() {
        // Step 1: create a mock object
        ExternalApi mockApi = mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        // Step 2: call the method
        service.fetchData();

        // Step 3: verify the interaction - confirms getData() was actually
        // called on the mock as a result of calling fetchData()
        verify(mockApi).getData();
    }

    @Test
    public void testVerifyInteraction_calledExactlyOnce() {
        // A slightly stronger check than the basic verify() above: this
        // confirms getData() was called exactly one time, not zero and not
        // multiple times - useful when calling it twice by mistake would be
        // a real bug (e.g., making the same costly external call twice)
        ExternalApi mockApi = mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        service.fetchData();

        verify(mockApi, times(1)).getData();
    }

    @Test
    public void testVerifyInteraction_neverCalledIfServiceNotUsed() {
        // Confirms the opposite case: if fetchData() is never invoked, the
        // mock's getData() should never be called either - proof that
        // MyService doesn't call out to the API on its own, only in
        // response to fetchData() being called
        ExternalApi mockApi = mock(ExternalApi.class);
        new MyService(mockApi); // service created, but fetchData() is never called

        verify(mockApi, never()).getData();
    }
}
