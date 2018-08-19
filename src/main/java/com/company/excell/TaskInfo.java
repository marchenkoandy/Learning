package com.company.excell;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskInfo {
    private String zephyrName;
    private String scenarioName;
    private String component;
    private String status;
    private String assignee;
}
