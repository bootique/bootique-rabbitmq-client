package io.bootique.rabbitmq.client;

import com.google.inject.Module;
import io.bootique.BQModule;
import io.bootique.BQModuleProvider;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

public class RabbitMQModuleProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new RabbitMQModule();
    }


    @Override
    public Map<String, Type> configs() {
        // TODO: config prefix is hardcoded. Refactor away from ConfigModule, and make provider
        // generate config prefix, reusing it in metadata...
        return Collections.singletonMap("rabbitmq", RabbitMQFactory.class);
    }

    @Override
    public BQModule.Builder moduleBuilder() {
        return BQModuleProvider.super
                .moduleBuilder()
                .description("Provides integration with RabbitMQ client library.");
    }
}
