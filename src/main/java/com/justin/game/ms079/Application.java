package com.justin.game.ms079;

import com.justin.game.ms079.config.ServerProperties;
import com.justin.game.ms079.dao.AccountDAO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import server.Start;

@EnableConfigurationProperties(ServerProperties.class)
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Resource
    private ServerProperties serverProperties;

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info(String.valueOf(serverProperties.isLoadGui()));

//        Start.instance.startServer();
    }
}
