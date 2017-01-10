package io.bootique.rabbitmq.client.command;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import io.bootique.application.CommandMetadata;
import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.rabbitmq.client.RabbitMQSettings;
import io.bootique.rabbitmq.client.channel.ChannelFactory;
import io.bootique.rabbitmq.client.connection.ConnectionFactory;

import java.io.IOException;

public class SendCommand extends CommandWithMetadata {

    private Provider<RabbitMQSettings> rabbitMQSettingsProvider;

    @Inject
    public SendCommand(Provider<RabbitMQSettings> rabbitMQSettingsProvider) {
        super(CommandMetadata
                .builder(SendCommand.class)
                .description("Send RabbitMQ message.")
                .build());

        this.rabbitMQSettingsProvider = rabbitMQSettingsProvider;
    }

    @Override
    public CommandOutcome run(Cli cli) {
        String connectionName = cli.optionString("connection");
        String exchangeName = cli.optionString("exchange");
        String queueName = cli.optionString("queue");
        String routingKey = cli.optionString("key");
        String message = cli.optionString("message");

        ConnectionFactory connectionFactory = rabbitMQSettingsProvider.get().getConnectionFactory();
        ChannelFactory channelFactory = rabbitMQSettingsProvider.get().getChannelFactory();

        Connection connection = connectionFactory.forName(connectionName);
        Channel channel = channelFactory.openChannel(connection, exchangeName, queueName, routingKey);

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
