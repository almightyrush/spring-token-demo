package com.example.springtokendemo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Request {
    @JsonProperty("authKey")
    private String authKey;
    @JsonProperty("text")
    private String text;
}
