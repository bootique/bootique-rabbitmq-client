package io.bootique.rabbitmq.client.connection;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.rabbitmq.client.ConnectionFactory;
import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;

@BQConfig
@JsonTypeName("amqp")
public class AMQPConnectionConfig extends ConnectionConfig {

    private String username;
    private String password;
    private String virtualHost;
    private String host;
    private int port = -1;

    @Override
    protected ConnectionFactory createConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();

        if (username != null)
            factory.setUsername(username);
        if (password != null)
            factory.setPassword(password);
        if (virtualHost != null)
            factory.setVirtualHost(virtualHost);
        if (host != null)
            factory.setHost(host);

        factory.setPort(port);

        return factory;
    }

    @BQConfigProperty
    public void setUsername(String username) {
        this.username = username;
    }

    @BQConfigProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @BQConfigProperty
    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    @BQConfigProperty
    public void setHost(String host) {
        this.host = host;
    }

    @BQConfigProperty
    public void setPort(int port) {
        this.port = port;
    }
}
