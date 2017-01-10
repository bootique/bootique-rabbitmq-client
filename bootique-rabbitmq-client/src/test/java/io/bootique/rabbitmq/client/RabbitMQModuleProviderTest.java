package io.bootique.rabbitmq.client;

import io.bootique.test.junit.BQModuleProviderChecker;
import org.junit.Test;

public class RabbitMQModuleProviderTest {

    @Test
    public void testPresentInJar() {
        BQModuleProviderChecker.testPresentInJar(RabbitMQModuleProvider.class);
    }

}
