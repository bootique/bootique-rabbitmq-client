package io.bootique.rabbitmq.client;

import io.bootique.test.junit.BQModuleProviderChecker;
import org.junit.Test;

public class RabbitMQCmdModuleProviderTest {

    @Test
    public void testPresentInJar() {
        BQModuleProviderChecker.testPresentInJar(RabbitMQCmdModuleProvider.class);
    }

}
