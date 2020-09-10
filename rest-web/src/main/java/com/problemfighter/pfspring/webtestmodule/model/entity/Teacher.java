package com.problemfighter.pfspring.webtestmodule.model.entity;

import com.problemfighter.pfspring.common.model.EntityCommon;

import javax.persistence.Entity;


@Entity
public class Teacher extends EntityCommon {

    public String firstName;
    public String lastName;
    public String teacherCode;
    public String mobile;


}
