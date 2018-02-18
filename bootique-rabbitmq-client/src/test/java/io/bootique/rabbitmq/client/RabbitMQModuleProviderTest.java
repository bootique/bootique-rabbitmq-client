package io.bootique.rabbitmq.client;

import io.bootique.test.junit.BQModuleProviderChecker;
import org.junit.Test;

public class RabbitMQModuleProviderTest {

    @Test
    public void testAutoLoadable() {
        BQModuleProviderChecker.testAutoLoadable(RabbitMQModuleProvider.class);
    }

    @Test
    public void testMetadata() {
        BQModuleProviderChecker.testMetadata(RabbitMQModuleProvider.class);
    }

}
