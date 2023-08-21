package com.code;

import io.brachu.johann.DockerCompose;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Profile({"test", "local"})
public class InitDockerBean {

    private DockerCompose compose = DockerCompose.builder().classpath("/docker-compose.yml").build();

    @PostConstruct
    public void init() {
        compose.up();
    }

    @PreDestroy
    public void shutdown() {
        compose.down();
    }

}
