package com.ufcg.psoft.tccmatch.services.fieldsOfStudy;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.repositories.fieldsOfStudy.FieldsOfStudyRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldsOfStudyService{
    
    @Autowired
    private FieldsOfStudyRepository fieldsOfStudyRepository;

    public FieldOfStudy createFieldsOfStudy(String name){
        FieldOfStudy fieldsOfStudy = new FieldOfStudy(name);
        fieldsOfStudyRepository.save(fieldsOfStudy);
        return fieldsOfStudy;
    } 
    public Optional<FieldOfStudy> findByName(String name) {
        return fieldsOfStudyRepository.findByName(name);
      }
}