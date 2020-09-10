package com.problemfighter.pfspring.webtestmodule.model.entity;

import com.problemfighter.pfspring.common.model.EntityCommon;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Registration extends EntityCommon {

    @ManyToOne
    @JoinColumn(name = "student_id")
    public Student student;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    public Semester semester;

}
