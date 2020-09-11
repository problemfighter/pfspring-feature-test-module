package com.problemfighter.pfspring.module.es.example;


import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
@Document Some of the attributes in the annotations, like mysql, are as follows:
    index –> DB
    Document –> row

String indexName();//Name of index library. Name of project is recommended

String type() default "";//Type, which is recommended to be named after the entity

short shards() default 5;//Default partition number

short replicas() default 1;//Default number of backups per partition

String refreshInterval() default "1s";//refresh interval

String indexStoreType() default "fs";//Index file storage type

*/


@Document(indexName = "blog")
public class EsBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id  // Primary key, note that the search is id type string, which is different from what we usually use.
    private String id;  //@ After the Id annotation is added, the primary key corresponds to the column in Elastic search, and can be queried directly with the primary key when querying.
    private Long blogId; // The id of the blog entity, where an id attribute of the blog is added
    private String title;
    private String summary;
    private String content;

}


