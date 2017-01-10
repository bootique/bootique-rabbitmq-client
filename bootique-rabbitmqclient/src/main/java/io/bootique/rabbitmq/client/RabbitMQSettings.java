package io.bootique.rabbitmq.client;

import io.bootique.rabbitmq.client.channel.ChannelFactory;
import io.bootique.rabbitmq.client.connection.ConnectionFactory;

public class RabbitMQSettings {
    private ConnectionFactory connectionConfigMap;
    private ChannelFactory channelFactory;

    public RabbitMQSettings(ConnectionFactory connectionConfigMap,
                            ChannelFactory channelFactory) {
        this.connectionConfigMap = connectionConfigMap;
        this.channelFactory = channelFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionConfigMap;
    }

    public ChannelFactory getChannelFactory() {
        return channelFactory;
    }
}
