package com.problemfighter.pfspring.jdt.model.entity;

import com.problemfighter.pfspring.restapi.inter.model.RestEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
public class JdtPerson implements RestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String firstName;
    public String lastName;

    @Column(unique = true)
    public String email;

    public String mobile;

    public String occupation;

    @Column(nullable = false)
    public String gender;

    @Column(nullable = false)
    public Double age;

    public Boolean isDeleted = false;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    public Set<JdtAddress> addresses;

}
