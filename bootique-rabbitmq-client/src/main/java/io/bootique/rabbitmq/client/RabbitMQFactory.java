package io.bootique.rabbitmq.client;

import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;
import io.bootique.log.BootLogger;
import io.bootique.rabbitmq.client.channel.ChannelFactory;
import io.bootique.rabbitmq.client.channel.ExchangeConfig;
import io.bootique.rabbitmq.client.channel.QueueConfig;
import io.bootique.rabbitmq.client.connection.ConnectionConfig;
import io.bootique.rabbitmq.client.connection.ConnectionFactory;
import io.bootique.shutdown.ShutdownManager;

import java.util.Map;

@BQConfig
public class RabbitMQFactory {

    private Map<String, ConnectionConfig> connections;
    private Map<String, ExchangeConfig> exchanges;
    private Map<String, QueueConfig> queues;

    public ConnectionFactory createConnectionFactory(BootLogger bootLogger, ShutdownManager shutdownManager) {
        ConnectionFactory factory = new ConnectionFactory(connections);
        shutdownManager.addShutdownHook(() -> {
            bootLogger.trace(() -> "shutting down RabbitMQ ConnectionFactory...");
            factory.shutdown();
        });

        return factory;
    }

    public ChannelFactory createChannelFactory() {
        return new ChannelFactory(exchanges, queues);
    }

    @BQConfigProperty
    public void setConnections(Map<String, ConnectionConfig> connections) {
        this.connections = connections;
    }

    @BQConfigProperty
    public void setExchanges(Map<String, ExchangeConfig> exchanges) {
        this.exchanges = exchanges;
    }

    @BQConfigProperty
    public void setQueues(Map<String, QueueConfig> queues) {
        this.queues = queues;
    }
}
