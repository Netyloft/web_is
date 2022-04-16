package com.example.api.response;

import com.example.api.Constants;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"requestId", "version", "data"})
public class ApiResponse<T> {

    @NotNull
    @Schema(example = "ca0d6e76a2d247b7b431df7aeb67f6404")
    private String requestId;

    @NotNull
    @Schema(description = "Версия API", example = Constants.API_V1_VERSION)
    private String version;

    @NotNull
    private T data;

    public ApiResponse(String requestId, String version, T data) {
        this.requestId = requestId;
        this.version = version;
        this.data = data;
    }

    public ApiResponse(String requestId, T data) {
        this.requestId = requestId;
        this.version = Constants.API_V1_VERSION;
        this.data = data;
    }
}
