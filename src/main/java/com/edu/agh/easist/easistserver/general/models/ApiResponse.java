package com.edu.agh.easist.easistserver.general.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {
    private Integer code;
    private String message;
    private Object content;
}
