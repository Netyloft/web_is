package com.example.web_is.controller;

import com.example.web_is.excel.data.FileResult;
import com.example.web_is.excel.usecase.ExcelUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.web_is.controller.Constants.FILE_API_BASE_PATH;

@Tag(name = "Файл")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = FILE_API_BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    private final ExcelUseCase useCase;

    @Operation(summary = "Скачать файл")
    @GetMapping(value = "/")
    public ResponseEntity<InputStreamResource> getFile() {
        FileResult results = useCase.get();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + results.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(results.getInputStream()));
    }
}
