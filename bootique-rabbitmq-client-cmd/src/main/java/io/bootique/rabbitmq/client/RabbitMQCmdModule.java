package io.bootique.rabbitmq.client;

import com.google.inject.Binder;
import io.bootique.BQCoreModule;
import io.bootique.ConfigModule;
import io.bootique.application.OptionMetadata;
import io.bootique.rabbitmq.client.command.SendCommand;
import io.bootique.rabbitmq.client.option.RabbitMQCmdOption;

public class RabbitMQCmdModule extends ConfigModule {

    @Override
    public void configure(Binder binder) {
        // bind commands
        BQCoreModule.contributeCommands(binder)
                .addBinding()
                .to(SendCommand.class);

        // bind options
        OptionMetadata connectionOption = OptionMetadata
                .builder(RabbitMQCmdOption.CONNECTION.getName(), RabbitMQCmdOption.CONNECTION.getDescription())
                .valueRequired()
                .build();

        OptionMetadata exchangeOption = OptionMetadata
                .builder(RabbitMQCmdOption.EXCHANGE.getName(), RabbitMQCmdOption.EXCHANGE.getDescription())
                .valueRequired()
                .build();

        OptionMetadata queueOption = OptionMetadata
                .builder(RabbitMQCmdOption.QUEUE.getName(), RabbitMQCmdOption.QUEUE.getDescription())
                .valueOptional()
                .build();

        OptionMetadata routingKeyOption = OptionMetadata
                .builder(RabbitMQCmdOption.ROUTING_KEY.getName(), RabbitMQCmdOption.ROUTING_KEY.getDescription())
                .valueRequired()
                .build();

        OptionMetadata messageOption = OptionMetadata
                .builder(RabbitMQCmdOption.MESSAGE.getName(), RabbitMQCmdOption.MESSAGE.getDescription())
                .valueOptional()
                .build();

        BQCoreModule.contributeOptions(binder).addBinding().toInstance(connectionOption);
        BQCoreModule.contributeOptions(binder).addBinding().toInstance(exchangeOption);
        BQCoreModule.contributeOptions(binder).addBinding().toInstance(queueOption);
        BQCoreModule.contributeOptions(binder).addBinding().toInstance(routingKeyOption);
        BQCoreModule.contributeOptions(binder).addBinding().toInstance(messageOption);
    }
}
