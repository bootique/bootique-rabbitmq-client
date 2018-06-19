/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bootique.rabbitmq.client.integration;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.GetResponse;
import io.bootique.BQCoreModule;
import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.rabbitmq.client.channel.ChannelFactory;
import io.bootique.rabbitmq.client.connection.ConnectionFactory;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.LogMessageWaitStrategy;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

/**
 * Runs RabbitMQ and tests against real RMQ instance.
 *
 * @author Ibragimov Ruslan
 * @since 0.24
 */
public class RabbitMQNamingIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQNamingIT.class);

    @ClassRule
    public static GenericContainer rabbit = new GenericContainer("rabbitmq:3.6-alpine")
            .withExposedPorts(5672)
            .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Server startup complete.*\\s"));

    @Test
    public void test() throws IOException, TimeoutException {
        LOGGER.info("Rabbit url: {}", rabbit.getContainerIpAddress() + ":" + rabbit.getMappedPort(5672));

        BQRuntime runtime = Bootique
                .app()
                .args("--config=classpath:naming.yml")
                .module((binder) -> {
                    BQCoreModule.extend(binder)
                            .setProperty(
                                    "bq.rabbitmq.connections.bqConnection.port",
                                    String.valueOf(rabbit.getMappedPort(5672))
                            );
                    binder.bind(RabbitMqUI.class);
                })
                .autoLoadModules()
                .createRuntime();

        runtime.getInstance(RabbitMqUI.class).init();
    }
}


class RabbitMqUI {

    private static final String CONNECTION_NAME = "bqConnection";
    private static final String EXCHANGE_NAME = "bqExchange";
    private static final String QUEUE_NAME = "bqQueue";

    private ConnectionFactory connectionFactory;
    private ChannelFactory channelFactory;

    @Inject
    public RabbitMqUI(ConnectionFactory connectionFactory, ChannelFactory channelFactory) {
        this.connectionFactory = connectionFactory;
        this.channelFactory = channelFactory;
    }

    /*
    Queue and Exchange names must be the same or an IOException is thrown:
            -- channel error; protocol method: #method<channel.close>
            (reply-code=404, reply-text=NOT_FOUND - no exchange 'bqQueue' in vhost '/', class-id=40, method-id=30)
     */
    public void init() throws IOException, TimeoutException {
        try (Connection connection = connectionFactory.forName(CONNECTION_NAME);
             Channel channel = channelFactory.openChannel(connection, EXCHANGE_NAME, QUEUE_NAME, "")
        ) {
            // RabbitMQ Exchange with "bqQueue" must exist or IOException is thrown
            byte[] message = "Hello World!".getBytes("UTF-8");
            channel.basicPublish("", QUEUE_NAME, null, message);
            GetResponse getResponse = channel.basicGet(QUEUE_NAME, false);

            assertEquals(new String(message), new String(getResponse.getBody()));
        }
    }
}

