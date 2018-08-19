package com.company.excell;

import com.company.excell.exceptions.ExcelException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWorkbook {
    private static final String XLSX = "xlsx";
    private static final String XLS = "xls";

    private String getFileExtension(String fullFileName) {
        return FilenameUtils.getExtension(fullFileName);
//        return fullFileName.substring(fullFileName.lastIndexOf("."));
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
            throw new ExcelException();
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

    public void close(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (Exception e) {
                throw new ExcelException();
            } finally {
                workbook = null;
            }
        }
    }

    public static void main(String[] args) {
//        String excelFileName = "src/main/resources/excel/test.xls";
//        ExcelWorkbook excelWorkbook = new ExcelWorkbook();
//        Workbook workbook = excelWorkbook.read(excelFileName);
//        workbook.getSheetAt(0).getRow(0).getCell(0).setCellValue("Name");
//        System.out.println(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
//        excelWorkbook.write(workbook, excelFileName);
//
//        excelFileName = "src/main/resources/excel/test1.xlsx";
//        workbook = new ExcelWorkbook().read(excelFileName);
//        System.out.println(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());

        String excelFileName = "src/main/resources/excel/QAA.xlsx";
        ScopeReader scopeReader = new ScopeReader();
        scopeReader.performTask(excelFileName);
    }
}
