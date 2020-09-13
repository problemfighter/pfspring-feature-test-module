package com.problemfighter.pfspring.module.es.model.entity;


import com.problemfighter.pfspring.common.common.AppCommonUtil;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import javax.persistence.*;

@Document(indexName = "department")
public class EsDepartment {

    @Id
    public String id;

    @Field(type = FieldType.Keyword)
    public String name;

    @Field(type = FieldType.Keyword)
    public String code;

    @Field(type = FieldType.Text)
    public String description;


    @Field(type = FieldType.Text)
    public String uuid;


    @PrePersist
    private void onBasePersist() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.uuid = AppCommonUtil.uuid();
        }
    }

}
