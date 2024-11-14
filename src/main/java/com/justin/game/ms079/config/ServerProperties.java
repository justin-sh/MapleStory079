package com.justin.game.ms079.config;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "ms.server")
@Configuration
public class ServerProperties {

    @Value("loadGui")
    private String loadGui;

    public boolean isLoadGui(){
        return BooleanUtils.toBoolean(this.loadGui);
    }
}
