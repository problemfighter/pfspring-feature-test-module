package com.problemfighter.pfspring.rdbmser.model.data;

public enum DegreeType {

    EDUCATION(1, "Education"),
    TRAINING(2, "Training");

    private Integer index;
    private String type;

    DegreeType(Integer index, String type) {
        this.index = index;
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
