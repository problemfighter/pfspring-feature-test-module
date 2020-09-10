package com.problemfighter.pfspring.webtestmodule.model.entity;

import com.problemfighter.pfspring.common.model.EntityCommon;

import javax.persistence.Entity;

@Entity
public class Address extends EntityCommon {
    public String details;
    public String postCode;
    public String country;
    public String stateDistrict;
    public String cityUpozila;
}
