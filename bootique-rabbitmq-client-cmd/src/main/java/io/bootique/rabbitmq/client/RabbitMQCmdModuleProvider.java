package io.bootique.rabbitmq.client;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;

public class RabbitMQCmdModuleProvider implements BQModuleProvider {
    @Override
    public Module module() {
        return new RabbitMQCmdModule();
    }
}
