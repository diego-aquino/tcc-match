package com.ufcg.psoft.tccmatch.models.fieldsOfStudy;

public class fieldsOfStudyResponse {
    private Long id;
    private String name;
    
    public fieldsOfStudyResponse(FieldOfStudy field){
        this.id = field.getId();
        this.name = field.getName();
    }

    public Long getId() {
        return id;
    }
    
    public String getName(){
        return name;
    }

}
