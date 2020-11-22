package com.problemfighter.pfspring.rdbmser.model.dto;

import com.problemfighter.pfspring.rdbmser.model.data.DegreeType;
import java.util.HashSet;
import java.util.Set;

public class DegreeDTO {
    public Long id;
    public String name;
    public DegreeType type;
    public String description;
    public Set<PersonDTO> person = new HashSet<>();
}
