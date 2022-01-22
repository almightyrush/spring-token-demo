package com.example.springtokendemo.model.dto;

import lombok.Data;

@Data
public class CommonResponse
{

    private Boolean isSuccess;

    private String message;


    public CommonResponse(Boolean isSuccess, String message)
    {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
