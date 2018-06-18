package com.patelheggere.committeelection.model;

public class VotingModel {
    private String name;
    private String phone;
    private String chairman;
    private String secretary;
    private String treasurer;

    public VotingModel() {
    }

    public VotingModel(String name, String phone, String chairman, String secretary, String treasurer) {
        this.name = name;
        this.phone = phone;
        this.chairman = chairman;
        this.secretary = secretary;
        this.treasurer = treasurer;
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

    public String getChairman() {
        return chairman;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    public String getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }
}
