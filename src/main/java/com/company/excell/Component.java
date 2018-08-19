package com.company.excell;

public enum Component {
    ADMIN_TOOLS("Admin Tool"),
    BROWSE_GROUPS("Browse Groups"),
    BROWSE_UNIT_PAGE("Browse Unit page"),
    COMPANY_ACTIVATION("Company Activation"),
    FLEET_24_X7_ROADSIDE_ASSISTANCE("Fleet 24x7 Roadside Assistance"),
    FLEET_MAINTENANCE("Fleet Maintenance"),
    FLEET_OVERVIEW("Fleet Overview"),
    GENERAL("General"),
    GLOBAL_NAVIGATION_AND_SEARCH("Global Navigation and Search"),
    LOGIN("Login"),
    MAINTENANCE_BILLING("Maintenance Billing"),
    MY_PROFILE("My Profile"),
    UNIT_24_X7_ROADSIDE_ASSISTANCE("Unit 24x7 Roadside Assistance"),
    UNIT_MAINTENANCE("Unit Maintenance"),
    UNIT_VIEW("Unit View");

    private String name;

    Component(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
