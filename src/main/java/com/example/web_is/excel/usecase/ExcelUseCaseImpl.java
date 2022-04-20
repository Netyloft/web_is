package com.example.web_is.excel.usecase;

import com.example.web_is.data.User;
import com.example.web_is.excel.data.FileResult;
import com.example.web_is.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExcelUseCaseImpl implements ExcelUseCase {

    private final UserRepository userRepository;

    @Override
    public FileResult get() {

        try (
                XSSFWorkbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            createFile(workbook);

            workbook.write(outputStream);

            String name = "Файл.xlsx";
            String fileName = URLEncoder.encode(name, StandardCharsets.UTF_8);

            FileResult results = new FileResult();
            results.setFilename(fileName);
            results.setInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
            return results;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createFile(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Просто лист");

        List<User> users = userRepository.getAll();

        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Password");
        row.createCell(2).setCellValue("Email");

        for (User dataModel : users) {
            createSheetHeader(sheet, ++rowNum, dataModel);
        }
    }

    private static void createSheetHeader(Sheet sheet, int rowNum, User dataModel) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(dataModel.getNickName());
        row.createCell(1).setCellValue(dataModel.getPassword());
        row.createCell(2).setCellValue(dataModel.getEmail());

    }
}
