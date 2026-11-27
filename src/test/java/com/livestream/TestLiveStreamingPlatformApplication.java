package com.livestream;

import org.springframework.boot.SpringApplication;

public class TestLiveStreamingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.from(LiveStreamingPlatformApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
