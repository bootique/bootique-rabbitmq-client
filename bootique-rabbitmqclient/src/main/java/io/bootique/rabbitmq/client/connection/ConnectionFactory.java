package io.bootique.rabbitmq.client.connection;

import com.rabbitmq.client.Connection;
import org.apache.commons.io.IOUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionFactory {
    private Map<String, ConnectionConfig> configMap;
    private final ConcurrentMap<String, Connection> connectionList = new ConcurrentHashMap<>();

    public ConnectionFactory(Map<String, ConnectionConfig> configMap) {
        this.configMap = configMap;
    }

    public void shutdown() {
        connectionList.values().forEach(IOUtils::closeQuietly);
    }

    public Collection allNames() {
        return configMap.keySet();
    }

    public Connection forName(String connectionName) {
        return connectionList.computeIfAbsent(connectionName, this::createConnection);
    }

    protected Connection createConnection(String connectionName) {
        return configMap.computeIfAbsent(connectionName, name -> {
            throw new IllegalStateException("No configuration present for Connection named '" + name + "'");
        }).createConnection();
    }
}
