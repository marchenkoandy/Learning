package com.company.excell;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScopeTasks {
    private List<String> columnNames = new ArrayList<>();
    private List<TaskInfo> taskInfoList = new ArrayList<>();
}
