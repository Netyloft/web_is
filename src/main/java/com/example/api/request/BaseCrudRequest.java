package com.example.api.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

@Data
public class BaseCrudRequest {

    @Hidden
    private long id;

}
