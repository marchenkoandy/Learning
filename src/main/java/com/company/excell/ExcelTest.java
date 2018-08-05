package com.company.excell;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest {
    public static final String XLSX = ".xlsx";
    public static final String XLS = ".xls";

    private String getFileExtension(String fullFileName) {
        return fullFileName.substring(fullFileName.lastIndexOf("."));
    }

    public Workbook read(String excelFileName) {
        File fileExcel = new File(excelFileName);
        Workbook workbook;
        FileInputStream inputStream = null;
        String extension = getFileExtension(fileExcel.getAbsolutePath());
        try {
            inputStream = new FileInputStream(fileExcel);
            if (extension.equals(XLSX)) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (extension.equals(XLS)) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                throw new RuntimeException("Unknown file format");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    public void write(Workbook workbook, String excelFileName) {
        File fileExcel = new File(excelFileName);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileExcel);
            if (workbook.getNumberOfSheets() == 0) {
                workbook.createSheet("Sheet1");
            }
            workbook.write(outputStream);
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String excelFileName = "src/main/resources/excel/test.xls";
        ExcelTest excelTest = new ExcelTest();
        Workbook workbook = excelTest.read(excelFileName);
        workbook.getSheetAt(0).getRow(0).getCell(0).setCellValue("Name");
        System.out.println(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
        excelTest.write(workbook, excelFileName);

        excelFileName = "src/main/resources/excel/test1.xlsx";
        workbook = new ExcelTest().read(excelFileName);
        System.out.println(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
    }
}
