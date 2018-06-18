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
