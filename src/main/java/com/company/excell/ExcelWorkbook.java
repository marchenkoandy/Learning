package com.company.excell;

import com.company.excell.exceptions.ExcelException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
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
    }

    public Workbook create(String excelFileName) {
        File fileExcel = new File(excelFileName);
        if (fileExcel.exists()) {
            fileExcel.delete();
        }
        String extension = getFileExtension(fileExcel.getAbsolutePath());
        Workbook workbook;
        if (extension.equals(XLSX)) {
            workbook = new XSSFWorkbook();
        } else if (extension.equals(XLS)) {
            workbook = new HSSFWorkbook();
        } else {
            throw new ExcelException("Unknown file format");
        }
        return workbook;
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
                throw new ExcelException("Unknown file format");
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

    public Sheet getSheet(Workbook workbook, String sheetName) {
        if (sheetName.isEmpty()) {
            sheetName = "Sheet1";
        }
        if (workbook.getSheet(sheetName) == null) {
            return workbook.createSheet(sheetName);
        }
        return workbook.getSheet(sheetName);
    }

    public void write(Workbook workbook, String excelFileName) {
        File fileExcel = new File(excelFileName);
        new File(fileExcel.getParent()).mkdirs();
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
        String excelFileNameQAA = "src/main/resources/excel/QAA.xlsx";
        String excelFileNameByStatus = "src/main/resources/excel/QAAByStatus.xlsx";
        String excelFileNameByComponent = "src/main/resources/excel/QAAByComponent.xlsx";
        String excelFileNameByAssignee = "src/main/resources/excel/QAAByAssignee.xlsx";
        ScopeReader scopeReader = new ScopeReader();
        scopeReader.performTask(excelFileNameQAA, excelFileNameByStatus, ScopeReader.TaskItem.STATUS);
        scopeReader.performTask(excelFileNameQAA, excelFileNameByComponent, ScopeReader.TaskItem.COMPONENT);
        scopeReader.performTask(excelFileNameQAA, excelFileNameByAssignee, ScopeReader.TaskItem.ASSIGNEE);
    }
}
