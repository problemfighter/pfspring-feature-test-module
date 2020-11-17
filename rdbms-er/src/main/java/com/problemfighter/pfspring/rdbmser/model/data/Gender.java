package com.problemfighter.pfspring.rdbmser.model.data;

public enum Gender {
    MALE(1, "Male"),
    FEMALE(2, "Female"),
    NOT_SPECIFIED(3, "Not Specified"),
    OTHER(4, "Other");

    private Integer index;
    private String type;

    Gender(Integer index, String type) {
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
