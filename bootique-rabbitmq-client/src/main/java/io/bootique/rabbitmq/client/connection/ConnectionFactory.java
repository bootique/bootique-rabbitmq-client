package io.bootique.rabbitmq.client.connection;

import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);

    private Map<String, ConnectionConfig> configMap;
    private Map<String, AtomicReference<Connection>> connections;
    private volatile boolean shutdown;

    public ConnectionFactory(Map<String, ConnectionConfig> configMap) {
        this.configMap = configMap;
        this.connections = new HashMap<>();

        // presumably RabbitMQ connections are thread-safe and we can reuse them for parallel calls
        // https://www.rabbitmq.com/api-guide.html

        // so just create a fixed-sisze self-inflating cache of connections by name
        configMap.keySet().forEach(name -> connections.put(name, new AtomicReference<>()));
    }

    public void shutdown() {

        LOGGER.info("Shutting down RabbitMQ connections...");

        // this will prevent new connections creation, and we'll drain the ones already created...
        this.shutdown = true;

        connections.values()
                .stream()
                .map(AtomicReference::get)
                .filter(r -> r != null && r.isOpen())
                .forEach(c -> {
                    try {
                        c.close();
                    } catch (IOException e) {
                        // ignore...
                    }
                });
    }

    public Collection allNames() {
        return configMap.keySet();
    }

    public Connection forName(String connectionName) {

        AtomicReference<Connection> ref = connections.computeIfAbsent(connectionName, name -> {
            throw new IllegalStateException("No configuration present for Connection named '" + name + "'");
        });

        // states:
        // 1. no connection
        // 2. closed connection
        // 3. open connection

        Connection c = ref.get();

        if (c == null || !c.isOpen()) {
            Connection newConnection = createConnection(connectionName);
            if (ref.compareAndSet(c, newConnection)) {
                c = newConnection;
            }
            // another thread just opened a connection.. assuming it is valid in the pool
            else {
                try {
                    newConnection.close();
                } catch (IOException e) {
                    // ignore...
                }

                c = Objects.requireNonNull(ref.get());
            }
        }

        return c;
    }

    private Connection createConnection(String connectionName) {

        if (shutdown) {
            throw new IllegalStateException("ConnectionFactory is being shutdown");
        }

        return configMap.computeIfAbsent(connectionName, name -> {
            throw new IllegalStateException("No configuration present for Connection named '" + name + "'");
        }).createConnection();
    }
}
