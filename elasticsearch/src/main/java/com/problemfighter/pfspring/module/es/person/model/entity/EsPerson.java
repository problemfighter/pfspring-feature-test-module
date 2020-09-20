package com.problemfighter.pfspring.module.es.person.model.entity;

import com.problemfighter.pfspring.module.es.person.common.EsIndex;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import javax.persistence.Id;
import java.util.Date;

@Document(indexName = EsIndex.person)
public class EsPerson {

    @Id
    public String id;

    @Field(type = FieldType.Keyword)
    public String name;

    public Double age;

    @Field(type = FieldType.Keyword)
    public String sex;

    public Double income;

    public Date dob;

    @Field(type = FieldType.Keyword)
    public String degree;

    @Field(type = FieldType.Keyword)
    public String occupation;


}
