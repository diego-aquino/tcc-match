package com.ufcg.psoft.tccmatch.models.fieldsOfStudy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FieldOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    public FieldOfStudy(){}
    
    public FieldOfStudy(String name){
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }
    public String getName(){
        return name;
    }

}
