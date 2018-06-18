/**
 *    Licensed to ObjectStyle LLC under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ObjectStyle LLC licenses
 *  this file to you under the Apache License, Version 2.0 (the
 *  “License”); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package io.bootique.rabbitmq.client.channel;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;

import java.io.IOException;
import java.util.Map;

@BQConfig
public class ExchangeConfig {
    private BuiltinExchangeType type;
    private boolean durable = false;
    private boolean autoDelete = false;
    private boolean internal = false;
    private Map<String, Object> arguments;

    public void exchangeDeclare(Channel channel, String exchangeName) throws IOException {
        channel.exchangeDeclare(exchangeName, type, durable, autoDelete, internal, arguments);
    }

    @BQConfigProperty
    public void setType(BuiltinExchangeType type) {
        this.type = type;
    }

    @BQConfigProperty
    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    @BQConfigProperty
    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    @BQConfigProperty
    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    @BQConfigProperty
    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
}
