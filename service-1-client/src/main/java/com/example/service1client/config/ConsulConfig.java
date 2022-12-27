package com.example.service1client.config;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Collections;

@Startup
@Singleton
public class ConsulConfig {
    String serviceId = "service-1-server-1.0-SNAPSHOT";
    Consul client;
    AgentClient agentClient;

    @PostConstruct
    void init() {
        client = Consul.builder().build();
        agentClient = client.agentClient();
        Registration service = ImmutableRegistration.builder()
                .id(serviceId)
                .name(serviceId)
                .port(4567)
                .address("localhost")
                .check(Registration.RegCheck.http("http://localhost:8080/service-1-server-1.0-SNAPSHOT/api/v1/ping", 10, 1))
                .tags(Collections.singletonList("tag1"))
                .meta(Collections.singletonMap("version", "1.0"))
                .build();

        agentClient.register(service);
    }

    @PreDestroy
    private void deregisterService(){
        agentClient.deregister(serviceId);
    }

}
