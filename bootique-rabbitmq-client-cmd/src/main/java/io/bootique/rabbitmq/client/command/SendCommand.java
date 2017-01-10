package io.bootique.rabbitmq.client.command;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import io.bootique.application.CommandMetadata;
import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.rabbitmq.client.channel.ChannelFactory;
import io.bootique.rabbitmq.client.connection.ConnectionFactory;

import java.io.IOException;

public class SendCommand extends CommandWithMetadata {

    private Provider<ConnectionFactory> connectionFactoryProvider;
    private Provider<ChannelFactory> channelFactoryProvider;

    @Inject
    public SendCommand(Provider<ConnectionFactory> connectionFactoryProvider,
                       Provider<ChannelFactory> channelFactoryProvider) {
        super(CommandMetadata
                .builder(SendCommand.class)
                .description("Send RabbitMQ message.")
                .build());

        this.connectionFactoryProvider = connectionFactoryProvider;
        this.channelFactoryProvider = channelFactoryProvider;
    }

    @Override
    public CommandOutcome run(Cli cli) {
        String connectionName = cli.optionString("C");
        String exchangeName = cli.optionString("E");
        String queueName = cli.optionString("Q");
        String routingKey = cli.optionString("K");
        String message = cli.optionString("M");

        Connection connection = connectionFactoryProvider.get().forName(connectionName);
        Channel channel = channelFactoryProvider.get().openChannel(connection, exchangeName, queueName, routingKey);

        System.out.println("Chosen: " + connection);
        System.out.println(channel);

        try {
            byte[] messageBytes = message.getBytes();
            channel.basicPublish(exchangeName, routingKey, null, messageBytes);
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }

        return CommandOutcome.succeeded();
    }
}
