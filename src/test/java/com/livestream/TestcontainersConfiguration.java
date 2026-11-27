package com.livestream;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    MySQLContainer mysqlContainer() {
        // 로컬 MySQL(docker-compose.yml)과 같은 버전을 쓴다. 바꿀 때는 두 곳을 같이 바꾼다.
        return new MySQLContainer(DockerImageName.parse("mysql:8.4"));
    }

}
