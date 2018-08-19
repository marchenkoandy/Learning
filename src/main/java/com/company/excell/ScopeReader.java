package com.company.excell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ScopeReader {
    public enum GroupTasksBy {
        COMPONENT,
        STATUS,
        ASSIGNEE
    }

    private void getColumnNames(Sheet scope, ScopeTasksTable scopeTasksTable) {
        Row headerRow = scope.getRow(0);
        int columnsCount = headerRow.getPhysicalNumberOfCells();
        for (int i = 0; i < columnsCount; i++) {
            scopeTasksTable.getColumnName().add(headerRow.getCell(i).getStringCellValue());
        }
    }

    private void getData(Sheet scope, ScopeTasksTable scopeTasksTable) {
        int rowsCount = scope.getPhysicalNumberOfRows();
        for (int row = 1; row < rowsCount; row++) {
            Row bodyRow = scope.getRow(row);
            TaskInfo taskInfo = new TaskInfo(bodyRow.getCell(0).getStringCellValue(),
                    bodyRow.getCell(1).getStringCellValue(),
                    bodyRow.getCell(2).getStringCellValue(),
                    bodyRow.getCell(3).getStringCellValue(),
                    bodyRow.getCell(4).getStringCellValue()
            );
            scopeTasksTable.getTaskInfoList().add(taskInfo);
        }
    }

    private ScopeTasksTable getAllTasksFromFile(String fileName) {
        ExcelWorkbook excelWorkbook = new ExcelWorkbook();
        Workbook workbook = excelWorkbook.read(fileName);
        ScopeTasksTable scopeTasksTable = new ScopeTasksTable();
        getColumnNames(workbook.getSheet("Scope"), scopeTasksTable);
        getData(workbook.getSheet("Scope"), scopeTasksTable);
        excelWorkbook.close(workbook);
        scopeTasksTable.getTaskInfoList().stream()
                .peek(taskInfo -> {
                    taskInfo.setComponent(taskInfo.getComponent().isEmpty() ? "EMPTY COMPONENT" : taskInfo.getComponent());
                    taskInfo.setStatus(taskInfo.getStatus().isEmpty() ? "EMPTY STATUS" : taskInfo.getStatus());
                    taskInfo.setAssignee(taskInfo.getAssignee().isEmpty() ? "EMPTY ASSIGNEE" : taskInfo.getAssignee());
                })
                .collect(Collectors.toList());
        return scopeTasksTable;
    }

    private ScopeTasksTable getTasksForComponent(ScopeTasksTable allTasks, String componentName) {
        ScopeTasksTable scopeTasksForComponent = new ScopeTasksTable();
        scopeTasksForComponent.setColumnName(allTasks.getColumnName());
        scopeTasksForComponent.setTaskInfoList(
                allTasks.getTaskInfoList().stream()
                        .filter(taskInfo -> taskInfo.getComponent().equalsIgnoreCase(componentName))
                        .collect(Collectors.toList())
        );
        return scopeTasksForComponent;
    }

    private ScopeTasksTable getTasksForStatus(ScopeTasksTable allTasks, String statusName) {
        ScopeTasksTable scopeTasksForComponent = new ScopeTasksTable();
        scopeTasksForComponent.setColumnName(allTasks.getColumnName());
        scopeTasksForComponent.setTaskInfoList(
                allTasks.getTaskInfoList().stream()
                        .filter(taskInfo -> taskInfo.getStatus().equalsIgnoreCase(statusName))
                        .collect(Collectors.toList())
        );
        return scopeTasksForComponent;
    }

    private ScopeTasksTable getTasksForAssignee(ScopeTasksTable allTasks, String assignee) {
        ScopeTasksTable scopeTasksForComponent = new ScopeTasksTable();
        scopeTasksForComponent.setColumnName(allTasks.getColumnName());
        scopeTasksForComponent.setTaskInfoList(
                allTasks.getTaskInfoList().stream()
                        .filter(taskInfo -> taskInfo.getAssignee().equalsIgnoreCase(assignee))
                        .collect(Collectors.toList())
        );
        return scopeTasksForComponent;
    }

    private List<String> getComponents(ScopeTasksTable allTasks) {
        return allTasks.getTaskInfoList().stream().map(TaskInfo::getComponent)
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    private List<String> getStatuses(ScopeTasksTable allTasks) {
        return allTasks.getTaskInfoList().stream().map(TaskInfo::getStatus)
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    private List<String> getAssignee(ScopeTasksTable allTasks) {
        return allTasks.getTaskInfoList().stream().map(TaskInfo::getAssignee)
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    private Map<String, ScopeTasksTable> getTasksByComponent(ScopeTasksTable allTasks) {
        List<String> items = getComponents(allTasks);
        Map<String, ScopeTasksTable> mOut = new TreeMap<>();
        for (String item : items) {
            mOut.put(item, getTasksForComponent(allTasks, item));
        }
        return mOut;
    }

    private Map<String, ScopeTasksTable> getTasksByStatus(ScopeTasksTable allTasks) {
        List<String> items = getStatuses(allTasks);
        Map<String, ScopeTasksTable> mOut = new TreeMap<>();
        for (String item : items) {
            mOut.put(item, getTasksForStatus(allTasks, item));
        }
        return mOut;
    }

    private Map<String, ScopeTasksTable> getTasksByAssignee(ScopeTasksTable allTasks) {
        List<String> items = getAssignee(allTasks);
        Map<String, ScopeTasksTable> mOut = new TreeMap<>();
        for (String item : items) {
            mOut.put(item, getTasksForAssignee(allTasks, item));
        }
        return mOut;
    }

    private void createAndPopulateFile(String fileName, Map<String, ScopeTasksTable> m) {
        ExcelWorkbook excelWorkbook = new ExcelWorkbook();
        Workbook workbook = excelWorkbook.create(fileName);
        for (Map.Entry<String, ScopeTasksTable> entry : m.entrySet()) {
            populateSheet(workbook, entry);
        }
        excelWorkbook.write(workbook, fileName);
        excelWorkbook.close(workbook);
    }

    private void populateSheet(Workbook workbook, Map.Entry<String, ScopeTasksTable> entry) {
        Sheet sheet = new ExcelWorkbook().getSheet(workbook, entry.getKey());
        setColumnNames(sheet, entry.getValue());
        setData(sheet, entry.getValue());
    }

    private void setColumnNames(Sheet sheet, ScopeTasksTable value) {
        Row headerRow = sheet.createRow(0);
        int columnsCount = value.getColumnName().size();
        for (int column = 0; column < columnsCount; column++) {
            headerRow.createCell(column).setCellValue(value.getColumnName().get(column));
        }
    }

    private void setData(Sheet sheet, ScopeTasksTable value) {
        int rowsCount = value.getTaskInfoList().size();
        for (int row = 0; row < rowsCount; row++) {
            Row bodyRow = sheet.createRow(row + 1);
            bodyRow.createCell(0).setCellValue(value.getTaskInfoList().get(row).getZephyrName());
            bodyRow.createCell(1).setCellValue(value.getTaskInfoList().get(row).getScenarioName());
            bodyRow.createCell(2).setCellValue(value.getTaskInfoList().get(row).getComponent());
            bodyRow.createCell(3).setCellValue(value.getTaskInfoList().get(row).getStatus());
            bodyRow.createCell(4).setCellValue(value.getTaskInfoList().get(row).getAssignee());
        }
    }

    public void performTask(String scopeFileExcel, String writeTo, GroupTasksBy groupBy) {
        ScopeTasksTable allTasks = getAllTasksFromFile(scopeFileExcel);
        Map<String, ScopeTasksTable> m;
        switch (groupBy) {
            case COMPONENT:
                m = getTasksByComponent(allTasks);
                break;
            case STATUS:
                m = getTasksByStatus(allTasks);
                break;
            case ASSIGNEE:
                m = getTasksByAssignee(allTasks);
                break;
            default:
                throw new EnumConstantNotPresentException(GroupTasksBy.class, groupBy.name());
        }
        createAndPopulateFile(writeTo, m);
    }
}
