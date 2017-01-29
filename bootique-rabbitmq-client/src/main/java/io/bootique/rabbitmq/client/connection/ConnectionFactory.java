package io.bootique.rabbitmq.client.connection;

import com.rabbitmq.client.Connection;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);

    private Map<String, ConnectionConfig> configMap;
    private final ConcurrentMap<String, Connection> connectionList = new ConcurrentHashMap<>();
    private volatile Boolean shutdown;
    private final Object lock = new Object();

    public ConnectionFactory(Map<String, ConnectionConfig> configMap) {
        this.configMap = configMap;
    }

    public void shutdown() {
        shutdown = true;
        LOGGER.info("Shutting down RabbitMQ service...");
        synchronized (lock) {
            LOGGER.info("Closing Rabbit MQ broker connections");
            connectionList.values()
                    .stream()
                    .filter(Connection::isOpen)
                    .forEach(IOUtils::closeQuietly);
            connectionList.clear();
        }
    }

    public Collection allNames() {
        return configMap.keySet();
    }

    public Connection forName(String connectionName) {
        if (shutdown) {
            throw new IllegalStateException("Can't get connection -- Rabbit MQ service is shut down");
        }
        synchronized (lock) {
            return isConnectionValid(connectionName) ? connectionList.get(connectionName) : createConnection(connectionName);
        }
    }

    private Connection createConnection(String connectionName) {
        Connection connection = configMap.computeIfAbsent(connectionName, name -> {
            throw new IllegalStateException("No configuration present for Connection named '" + name + "'");
        }).createConnection();

        connectionList.put(connectionName, connection);
        return connection;
    }

    private boolean isConnectionValid(String connectionName) {
        return connectionList.containsKey(connectionName) && connectionList.get(connectionName).isOpen();
    }
}
