package com.company.excell;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScopeTasksTable {
    private List<String> columnName = new ArrayList<>();
    private List<TaskInfo> taskInfoList = new ArrayList<>();
}
