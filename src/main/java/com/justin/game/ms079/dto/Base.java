package com.justin.game.ms079.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class Base {

    protected int id;

    protected Date createdAt;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
