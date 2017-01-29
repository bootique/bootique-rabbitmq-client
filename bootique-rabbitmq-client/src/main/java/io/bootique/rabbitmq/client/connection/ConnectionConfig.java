package io.bootique.rabbitmq.client.connection;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rabbitmq.client.Connection;
import io.bootique.config.PolymorphicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class ConnectionConfig implements PolymorphicConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionConfig.class);

    private int requestedChannelMax;
    private int requestedFrameMax;
    private int requestedHeartbeat;
    private int connectionTimeout;
    private int handshakeTimeout;
    private int shutdownTimeout;

    private boolean automaticRecoveryEnabled;
    private boolean topologyRecovery;

    private long networkRecoveryInterval;

    protected abstract com.rabbitmq.client.ConnectionFactory createConnectionFactory();

    public Connection createConnection() {
        com.rabbitmq.client.ConnectionFactory factory = createConnectionFactory();

        factory.setRequestedChannelMax(requestedChannelMax);
        factory.setRequestedFrameMax(requestedFrameMax);
        factory.setRequestedHeartbeat(requestedHeartbeat);
        factory.setConnectionTimeout(connectionTimeout);
        factory.setHandshakeTimeout(handshakeTimeout);
        factory.setShutdownTimeout(shutdownTimeout);
        factory.setAutomaticRecoveryEnabled(automaticRecoveryEnabled);
        factory.setTopologyRecoveryEnabled(topologyRecovery);
        factory.setNetworkRecoveryInterval(networkRecoveryInterval);

        LOGGER.info("Creating RabbitMQ connection.");
        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRequestedChannelMax(int requestedChannelMax) {
        this.requestedChannelMax = requestedChannelMax;
    }

    public void setRequestedFrameMax(int requestedFrameMax) {
        this.requestedFrameMax = requestedFrameMax;
    }

    public void setRequestedHeartbeat(int requestedHeartbeat) {
        this.requestedHeartbeat = requestedHeartbeat;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setHandshakeTimeout(int handshakeTimeout) {
        this.handshakeTimeout = handshakeTimeout;
    }

    public void setShutdownTimeout(int shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public void setAutomaticRecoveryEnabled(boolean automaticRecoveryEnabled) {
        this.automaticRecoveryEnabled = automaticRecoveryEnabled;
    }

    public void setTopologyRecovery(boolean topologyRecovery) {
        this.topologyRecovery = topologyRecovery;
    }

    public void setNetworkRecoveryInterval(long networkRecoveryInterval) {
        this.networkRecoveryInterval = networkRecoveryInterval;
    }
}
