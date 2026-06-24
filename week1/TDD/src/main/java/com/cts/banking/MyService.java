package com.cts.banking;

/*
 * A service that depends on ExternalApi to do its job. In a real system,
 * fetchData() might call out to a credit bureau or payment gateway and
 * return that data for use elsewhere in the bank's systems.
 *
 * Notice the dependency is injected through the constructor, rather than
 * MyService creating its own ExternalApi internally. This is what makes the
 * class testable with Mockito: a test can pass in a mock ExternalApi
 * instead of a real one, without MyService needing to know or care.
 */
public class MyService {

    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchData() {
        return externalApi.getData();
    }
}
