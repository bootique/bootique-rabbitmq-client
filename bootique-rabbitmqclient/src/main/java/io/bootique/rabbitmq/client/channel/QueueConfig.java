package io.bootique.rabbitmq.client.channel;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Map;

public class QueueConfig {
    private boolean durable = true;
    private boolean exclusive = false;
    private boolean autoDelete = false;
    private Map<String, Object> arguments;

    public void queueDeclare(Channel channel, String queueName) throws IOException {
        channel.queueDeclare(queueName, durable, exclusive, autoDelete, arguments);
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
}
