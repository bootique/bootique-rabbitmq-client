package io.bootique.rabbitmq.client.connection;

import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class ConnectionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionConfig.class);

    private String host;
    private Integer port;
    private String virtualHost;
    private String username;
    private String password;
    private String uri;

    public Connection createConnection() {
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();

        if (uri != null) {
            try {
                factory.setUri(uri);
            } catch (URISyntaxException |
                    NoSuchAlgorithmException |
                    KeyManagementException e) {
                throw new IllegalArgumentException("Config URI syntax is wrong");
            }
        } else {
            if (host != null)
                factory.setHost(host);

            if (port != null)
                factory.setPort(port);

            if (virtualHost != null)
                factory.setVirtualHost(virtualHost);

            if (username != null)
                factory.setUsername(username);

            if (password != null)
                factory.setPassword(password);
        }

        LOGGER.info("Creating RabbitMQ connection.");

        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
