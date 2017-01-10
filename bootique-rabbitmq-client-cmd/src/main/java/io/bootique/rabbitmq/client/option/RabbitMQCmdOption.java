package io.bootique.rabbitmq.client.option;

public enum RabbitMQCmdOption {
    CONNECTION("C", "Choose connection"),
    EXCHANGE("E", "Choose exchange point"),
    QUEUE("Q", "Choose queue"),
    ROUTING_KEY("K", "Routing key"),
    MESSAGE("M", "Message to send");

    private String name;
    private String description;

    RabbitMQCmdOption(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
