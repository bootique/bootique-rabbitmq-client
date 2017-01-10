package io.bootique.rabbitmq.client;

import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.log.BootLogger;
import io.bootique.rabbitmq.client.channel.ChannelFactory;
import io.bootique.rabbitmq.client.connection.ConnectionFactory;
import io.bootique.shutdown.ShutdownManager;

public class RabbitMQModule extends ConfigModule {

    @Override
    public void configure(Binder binder) {
    }

    @Provides
    @Singleton
    RabbitMQFactory provideRabbitMQFactory(ConfigurationFactory configurationFactory) {
        return configurationFactory.config(RabbitMQFactory.class, configPrefix);
    }

    @Provides
    ConnectionFactory provideConnectionFactory(RabbitMQFactory rabbitMQFactory,
                                               BootLogger bootLogger,
                                               ShutdownManager shutdownManager) {
        return rabbitMQFactory.createConnectionFactory(bootLogger, shutdownManager);
    }

    @Provides
    ChannelFactory provideChannelFactory(RabbitMQFactory rabbitMQFactory) {
        return rabbitMQFactory.createChannelFactory();
    }
}
