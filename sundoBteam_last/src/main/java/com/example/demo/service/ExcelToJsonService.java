package com.example.demo.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelToJsonService {
    public List<Map<String, String>> readExcelFile(String filePath, String riverCode) {
        List<Map<String, String>> data = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            Workbook workbook;
            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            } else {
                throw new IllegalArgumentException("오류");
            }

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(8);
                if (cell != null) {
                    String cellValue = cell.toString();
                    if (cellValue.equals(riverCode)) {

                        Iterator<Cell> cellIterator = row.cellIterator();
                        Map<String, String> rowData = new LinkedHashMap<>();
                        while (cellIterator.hasNext()) {
                            Cell currentCell = cellIterator.next();
                            String cellData = currentCell.toString();
                            rowData.put(String.valueOf(currentCell.getColumnIndex()), cellData);
                        }
                        data.add(rowData);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

}
