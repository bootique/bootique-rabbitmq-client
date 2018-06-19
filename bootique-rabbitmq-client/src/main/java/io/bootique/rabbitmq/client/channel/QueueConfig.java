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

package io.bootique.rabbitmq.client.channel;

import com.rabbitmq.client.Channel;
import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;

import java.io.IOException;
import java.util.Map;

@BQConfig
public class QueueConfig {
    private boolean durable = true;
    private boolean exclusive = false;
    private boolean autoDelete = false;
    private Map<String, Object> arguments;

    public void queueDeclare(Channel channel, String queueName) throws IOException {
        channel.queueDeclare(queueName, durable, exclusive, autoDelete, arguments);
    }

    @BQConfigProperty
    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    @BQConfigProperty
    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    @BQConfigProperty
    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    @BQConfigProperty
    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
}
