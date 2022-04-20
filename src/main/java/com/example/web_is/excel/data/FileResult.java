package com.example.web_is.excel.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResult {
    private String filename;
    private InputStream inputStream;
}



