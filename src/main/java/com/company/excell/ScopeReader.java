package com.company.excell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ScopeReader {

    private ScopeTasksTable getAllTasksFromFile(String fileName) {
        ExcelWorkbook excelWorkbook = new ExcelWorkbook();
        Workbook workbook = excelWorkbook.read(fileName);
        ScopeTasksTable scopeTasksTable = new ScopeTasksTable();
        getColumnNames(workbook.getSheet("Scope"), scopeTasksTable);
        getData(workbook.getSheet("Scope"), scopeTasksTable);
        excelWorkbook.close(workbook);
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
        return allTasks.getTaskInfoList().stream()
                .map(taskInfo -> taskInfo.getComponent())
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    private List<String> getStatuses(ScopeTasksTable allTasks) {
        return allTasks.getTaskInfoList().stream()
                .map(taskInfo -> taskInfo.getStatus())
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    private List<String> getAssignee(ScopeTasksTable allTasks) {
        return allTasks.getTaskInfoList().stream()
                .map(taskInfo -> taskInfo.getAssignee())
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
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

    public void performTask(String excelFileName) {
        ScopeTasksTable allTasks = getAllTasksFromFile(excelFileName);
        Map<String, ScopeTasksTable> m1 = getTasksByComponent(allTasks);
        Map<String, ScopeTasksTable> m2 = getTasksByStatus(allTasks);
        Map<String, ScopeTasksTable> m3 = getTasksByAssignee(allTasks);
        System.out.println("");
    }
}
