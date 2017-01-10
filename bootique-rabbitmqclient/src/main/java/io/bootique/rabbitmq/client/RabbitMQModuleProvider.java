package io.bootique.rabbitmq.client;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;

public class RabbitMQModuleProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new RabbitMQModule();
    }
}
