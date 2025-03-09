package com.circuit_breaker.integration;

import java.time.ZoneOffset;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles({"test"})
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@DirtiesContext
@Slf4j
public class TestContainersConfiguration {

    private static final MongoDBContainer mongoDBContainer;

    static {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
        mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:8.0"))
            .withExposedPorts(27017)
            .withReuse(true)
            .waitingFor(Wait.forListeningPort())
            .withCommand("--replSet", "rs0");
        mongoDBContainer.start();
        try {
            mongoDBContainer.execInContainer("/bin/bash", "-c", "sleep 5 && mongosh --eval 'rs.initiate()'");
        } catch (Exception e) {
            log.error("Error initializing replica set", e);
        }
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "circuit-breaker");
        registry.add("spring.application.name", () -> "circuit-breaker-app");
    }

    @BeforeAll
    public static void setup() {
        log.info("MongoDB Container started at: {}", mongoDBContainer.getReplicaSetUrl());
    }
}

