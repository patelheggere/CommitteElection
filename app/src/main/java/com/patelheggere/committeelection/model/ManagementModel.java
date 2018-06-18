package com.patelheggere.committeelection.model;

public class ManagementModel  {
    private String name;
    private String phone;

    public ManagementModel() {
    }

    public ManagementModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
