package com.problemfighter.pfspring.webtestmodule.model.entity;

import com.problemfighter.pfspring.webtestmodule.model.common.EntityCommon;

import javax.persistence.Entity;


@Entity
public class Teacher extends EntityCommon {

    public String firstName;
    public String lastName;
    public String teacherCode;
    public String mobile;


}
