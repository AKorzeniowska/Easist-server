package com.edu.agh.easist.easistserver.dto;

import lombok.Getter;

@Getter
public class FooDto {
    private long id;
    private String name;

    public FooDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
