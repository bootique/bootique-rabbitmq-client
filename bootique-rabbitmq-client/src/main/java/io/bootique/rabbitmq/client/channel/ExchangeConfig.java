package io.bootique.rabbitmq.client.channel;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Map;

public class ExchangeConfig {
    private BuiltinExchangeType type;
    private boolean durable = false;
    private boolean autoDelete = false;
    private boolean internal = false;
    private Map<String, Object> arguments;

    public void exchangeDeclare(Channel channel, String exchangeName) throws IOException {
        channel.exchangeDeclare(exchangeName, type, durable, autoDelete, internal, arguments);
    }

    public void setType(BuiltinExchangeType type) {
        this.type = type;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
}
