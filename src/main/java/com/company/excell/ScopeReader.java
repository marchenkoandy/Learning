package com.company.excell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ScopeReader {

    public enum TaskItem {
        COMPONENT,
        STATUS,
        ASSIGNEE
    }

    private Predicate<TaskInfo> filterByColumnItem(TaskItem taskItem, String item) {
        return taskInfo -> {
            switch (taskItem) {
                case COMPONENT:
                    return item.equalsIgnoreCase(taskInfo.getComponent());
                case STATUS:
                    return item.equalsIgnoreCase(taskInfo.getStatus());
                case ASSIGNEE:
                    return item.equalsIgnoreCase(taskInfo.getAssignee());
                default:
                    throw new EnumConstantNotPresentException(TaskItem.class, taskItem.name());
            }
        };
    }

    private Function<TaskInfo, String> itemsOfColumn(TaskItem taskItem) {
        return taskInfo -> {
            switch (taskItem) {
                case COMPONENT:
                    return taskInfo.getComponent();
                case STATUS:
                    return taskInfo.getStatus();
                case ASSIGNEE:
                    return taskInfo.getAssignee();
                default:
                    throw new EnumConstantNotPresentException(TaskItem.class, taskItem.name());
            }
        };
    }

    private void getColumnHeaders(Sheet sheet, ScopeTasks tasks) {
        Row headerRow = sheet.getRow(0);
        int columnsCount = headerRow.getPhysicalNumberOfCells();
        for (int i = 0; i < columnsCount; i++) {
            tasks.getColumnNames().add(headerRow.getCell(i).getStringCellValue());
        }
    }

    private void setColumnHeaders(Sheet sheet, ScopeTasks tasks) {
        Row headerRow = sheet.createRow(0);
        int columnsCount = tasks.getColumnNames().size();
        for (int column = 0; column < columnsCount; column++) {
            headerRow.createCell(column).setCellValue(tasks.getColumnNames().get(column));
        }
    }

    private void getData(Sheet scope, ScopeTasks tasks) {
        int rowsCount = scope.getPhysicalNumberOfRows();
        for (int row = 1; row < rowsCount; row++) {
            Row bodyRow = scope.getRow(row);
            TaskInfo taskInfo = new TaskInfo(bodyRow.getCell(0).getStringCellValue(),
                    bodyRow.getCell(1).getStringCellValue(),
                    bodyRow.getCell(2).getStringCellValue(),
                    bodyRow.getCell(3).getStringCellValue(),
                    bodyRow.getCell(4).getStringCellValue()
            );
            tasks.getTaskInfoList().add(taskInfo);
        }
    }

    private void setData(Sheet sheet, ScopeTasks tasks) {
        int rowsCount = tasks.getTaskInfoList().size();
        for (int row = 0; row < rowsCount; row++) {
            Row bodyRow = sheet.createRow(row + 1);
            bodyRow.createCell(0).setCellValue(tasks.getTaskInfoList().get(row).getZephyrName());
            bodyRow.createCell(1).setCellValue(tasks.getTaskInfoList().get(row).getScenarioName());
            bodyRow.createCell(2).setCellValue(tasks.getTaskInfoList().get(row).getComponent());
            bodyRow.createCell(3).setCellValue(tasks.getTaskInfoList().get(row).getStatus());
            bodyRow.createCell(4).setCellValue(tasks.getTaskInfoList().get(row).getAssignee());
        }
    }

    private ScopeTasks getAllTasks(String fileName) {
        ExcelWorkbook excelWorkbook = new ExcelWorkbook();
        Workbook workbook = excelWorkbook.read(fileName);
        ScopeTasks scopeTasks = new ScopeTasks();
        getColumnHeaders(workbook.getSheet("Scope"), scopeTasks);
        getData(workbook.getSheet("Scope"), scopeTasks);
        excelWorkbook.close(workbook);
        scopeTasks.setTaskInfoList(scopeTasks.getTaskInfoList().stream()
                .peek(taskInfo -> {
                    taskInfo.setComponent(taskInfo.getComponent().isEmpty() ? "EMPTY COMPONENT" : taskInfo.getComponent());
                    taskInfo.setStatus(taskInfo.getStatus().isEmpty() ? "EMPTY STATUS" : taskInfo.getStatus());
                    taskInfo.setAssignee(taskInfo.getAssignee().isEmpty() ? "EMPTY ASSIGNEE" : taskInfo.getAssignee());
                })
                .collect(Collectors.toList()));
        return scopeTasks;
    }

    private ScopeTasks getTasksForItem(ScopeTasks tasks, TaskItem taskItem, String itemName) {
        ScopeTasks scopeTasksForItem = new ScopeTasks();
        scopeTasksForItem.setColumnNames(tasks.getColumnNames());
        scopeTasksForItem.setTaskInfoList(CollectionHelper.filterList(tasks.getTaskInfoList(),
                filterByColumnItem(taskItem, itemName)));
        return scopeTasksForItem;
    }

    private ScopeTasks getTasksFor2Items(ScopeTasks tasks, TaskItem taskItem1, String itemName1, TaskItem taskItem2, String itemName2) {
        ScopeTasks scopeTasksFor2Items = new ScopeTasks();
        scopeTasksFor2Items.setColumnNames(tasks.getColumnNames());
        scopeTasksFor2Items.setTaskInfoList(CollectionHelper.filterList(tasks.getTaskInfoList(),
                filterByColumnItem(taskItem1, itemName1)));
        scopeTasksFor2Items.setTaskInfoList(CollectionHelper.filterList(scopeTasksFor2Items.getTaskInfoList(),
                filterByColumnItem(taskItem2, itemName2)));
        return scopeTasksFor2Items;
    }

    private Map<String, ScopeTasks> getTasksForItems(ScopeTasks tasks, TaskItem taskItem) {
        List<String> items = getDistinctColumnItems(taskItem, tasks);
        Map<String, ScopeTasks> mOut = new TreeMap<>();
        for (String item : items) {
            mOut.put(item, getTasksForItem(tasks, taskItem, item));
        }
        return mOut;
    }

    private List<String> getDistinctColumnItems(TaskItem taskItem, ScopeTasks tasks) {
        return tasks.getTaskInfoList().stream()
                .map(itemsOfColumn(taskItem))
                .distinct()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    private void createAndPopulateFile(String fileName, Map<String, ScopeTasks> m) {
        ExcelWorkbook excelWorkbook = new ExcelWorkbook();
        Workbook workbook = excelWorkbook.create(fileName);
        for (Map.Entry<String, ScopeTasks> entry : m.entrySet()) {
            populateSheet(workbook, entry);
        }
        excelWorkbook.write(workbook, fileName);
        excelWorkbook.close(workbook);
    }

    private void populateSheet(Workbook workbook, Map.Entry<String, ScopeTasks> entry) {
        Sheet sheet = new ExcelWorkbook().getSheet(workbook, entry.getKey());
        setColumnHeaders(sheet, entry.getValue());
        setData(sheet, entry.getValue());
    }

    public void performTask(String scopeFileExcel, String writeTo, TaskItem groupBy) {
        ScopeTasks allTasks = getAllTasks(scopeFileExcel);
        Map<String, ScopeTasks> m = getTasksForItems(allTasks, groupBy);
        createAndPopulateFile(writeTo, m);
    }

    public void performTotal(String scopeFileExcel, String writeTo) {
        ScopeTasks allTasks = getAllTasks(scopeFileExcel);
        Map<String, ScopeTasks> groupByStatus = getTasksForItems(allTasks, TaskItem.STATUS);

        Map<String, ScopeTasks> groupByComponents = getTasksForItems(allTasks, TaskItem.COMPONENT);
        Map<String, ScopeTasks> groupByAssignee = getTasksForItems(allTasks, TaskItem.ASSIGNEE);

    }
}
