package io.bootique.rabbitmq.client;

import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.bootique.BQCoreModule;
import io.bootique.ConfigModule;
import io.bootique.application.OptionMetadata;
import io.bootique.config.ConfigurationFactory;
import io.bootique.log.BootLogger;
import io.bootique.rabbitmq.client.command.SendCommand;
import io.bootique.rabbitmq.client.connection.ConnectionConfig;
import io.bootique.shutdown.ShutdownManager;

import java.util.logging.Level;

public class RabbitMQModule extends ConfigModule {

    @Override
    public void configure(Binder binder) {
        // turn off chatty logs by default
        BQCoreModule.contributeLogLevels(binder)
                .addBinding(ConnectionConfig.class.getName())
                .toInstance(Level.WARNING);

        // bind commands
        BQCoreModule.contributeCommands(binder)
                .addBinding()
                .to(SendCommand.class);

        // bind options
        OptionMetadata connectionOption = OptionMetadata
                .builder("connection", "Choose connection")
                .valueRequired()
                .build();

        OptionMetadata exchangeOption = OptionMetadata
                .builder("exchange", "Choose exchange point")
                .valueRequired()
                .build();

        OptionMetadata queueOption = OptionMetadata
                .builder("queue", "Choose queue")
                .valueOptional()
                .build();

        OptionMetadata routingKeyOption = OptionMetadata
                .builder("key", "Routing key")
                .valueRequired()
                .build();

        OptionMetadata messageOption = OptionMetadata
                .builder("message", "Message to send")
                .valueOptional()
                .build();

        BQCoreModule.contributeOptions(binder).addBinding().toInstance(connectionOption);
        BQCoreModule.contributeOptions(binder).addBinding().toInstance(exchangeOption);
        BQCoreModule.contributeOptions(binder).addBinding().toInstance(queueOption);
        BQCoreModule.contributeOptions(binder).addBinding().toInstance(routingKeyOption);
        BQCoreModule.contributeOptions(binder).addBinding().toInstance(messageOption);
    }

    @Provides
    @Singleton
    RabbitMQSettings provideSettings(ConfigurationFactory configurationFactory,
                                     BootLogger bootLogger,
                                     ShutdownManager shutdownManager) {
        RabbitMQFactory rabbitMQFactory = configurationFactory.config(RabbitMQFactory.class, configPrefix);

        return new RabbitMQSettings(rabbitMQFactory.createConnectionFactory(bootLogger, shutdownManager),
                rabbitMQFactory.createChannelFactory());
    }
}
