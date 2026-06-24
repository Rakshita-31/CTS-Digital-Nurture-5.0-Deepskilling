package com.cts.banking;

/*
 * Represents an external API that our service depends on - for example, a
 * third-party credit bureau or exchange-rate provider the bank calls out to.
 * In real life this might wrap an HTTP call to another company's service.
 *
 * It's deliberately an interface (not a concrete class) because Mockito
 * mocks interfaces and classes alike, but interfaces keep the example clean
 * and match how external dependencies are typically abstracted in real code -
 * the rest of the app depends on this interface, not on any specific
 * implementation, so a mock can stand in for it perfectly during testing.
 */
public interface ExternalApi {
    String getData();
}