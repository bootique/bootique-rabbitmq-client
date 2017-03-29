package io.bootique.rabbitmq.client.connection;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.rabbitmq.client.ConnectionFactory;
import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;

@BQConfig
@JsonTypeName("uri")
public class URIConnectionConfig extends ConnectionConfig {

    private String uri;

    @Override
    protected ConnectionFactory createConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri(uri);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RabbitMQ URI connection factory", e);
        }
        return factory;
    }

    @BQConfigProperty
    public void setUri(String uri) {
        this.uri = uri;
    }
}
